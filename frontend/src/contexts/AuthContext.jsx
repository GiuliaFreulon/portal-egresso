
import {createContext, useContext, useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';
import { login as loginAPI} from '../services/authService.jsx'

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

            const response = await loginAPI(credentials);
            const { token, user } = response;

            localStorage.setItem('token', token);
            const decoded = jwtDecode(token);
            setUser({
                email: decoded.sub,
                role: decoded.role
            });
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
