// src/contexts/AuthContext.jsx
import { createContext, useContext, useState, useEffect } from 'react';
import {Navigate, useLocation, useNavigate} from 'react-router-dom';
import { jwtDecode } from 'jwt-decode'; // Instale com: npm install jwt-decode

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    // Verificar token ao inicializar
    useEffect(() => {
        checkAuth();
    }, []);

    const checkAuth = async () => {
        try {
            const token = localStorage.getItem('token');

            if (token && !isTokenExpired(token)) {
                const decoded = jwtDecode(token);
                decoded.name = undefined;
                decoded.email = undefined;
                decoded.role = undefined;
                setUser({
                    id: decoded.sub,
                    role: decoded.role,
                    email: decoded.email,
                    name: decoded.name
                });
            }
        } catch (error) {
            logout(error);
        } finally {
            setLoading(false);
        }
    };

    const isTokenExpired = (token) => {
        try {
            const { exp } = jwtDecode(token);
            return Date.now() >= exp * 1000;
        } catch {
            return true;
        }
    };

    const login = async (credentials) => {
        try {
            // Simular chamada à API
            const response = await fetch('/api/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(credentials)
            });

            if (!response.ok) throw new Error('Login falhou');

            const { token, user } = await response.json();

            localStorage.setItem('token', token);
            setUser(user);
            navigate('/dashboard');

        } catch (error) {
            console.error('Erro no login:', error);
            throw error;
        }
    };

    const logout = () => {
        localStorage.removeItem('token');
        setUser(null);
        navigate('/login');
    };

    const value = {
        user,
        loading,
        isAuthenticated: !!user,
        login,
        logout
    };

    return (
        <AuthContext.Provider value={value}>
            {!loading && children}
        </AuthContext.Provider>
    );
};

// Hook personalizado
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth deve ser usado dentro de um AuthProvider');
    }
    return context;
};

// Componente para proteger rotas
export const PrivateRoute = ({ children, roles }) => {
    const { user, isAuthenticated, loading } = useAuth();
    const location = useLocation();

    if (loading) return <div>Carregando...</div>;

    if (!isAuthenticated) {
        return <Navigate to="/login" state={{ from: location }} replace />;
    }

    if (roles && !roles.includes(user?.role)) {
        return <Navigate to="/unauthorized" replace />;
    }

    return children;
};