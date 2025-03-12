import React, {useEffect, useState} from 'react';
import './Login.css'
import {Link, useNavigate} from "react-router-dom";
import {login as loginAPI} from "../../../services/authService.jsx";
import {jwtDecode} from "jwt-decode";
import {useAuth} from "../../../contexts/AuthContext.jsx";

const Login = () => {
    const { isAuthenticated, user } = useAuth();
    const navigate = useNavigate()
    const [login, setLogin] = useState('');
    const [senha, setSenha] = useState('');

    // verificar autenticação ao carregar a página
    useEffect(() => {
        // Verifica se o usuário já está autenticado
        if (isAuthenticated) {
            // Redireciona com base no papel do usuário
            if (user.role === "ROLE_EGRESSO") {
                navigate("/egresso/dashboard")
            } else if (user.role === "ROLE_COORDENADOR") {
                navigate("/coordenador/dashboard")
            }
        }
    }, [isAuthenticated, user, navigate])

    const handleSubmit = async (event) => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            login,
            senha,
        };

        // teste
        console.log('Dados enviados:', JSON.stringify(formData, null, 2));

        try {
            const response = await loginAPI(formData);
            const token = response.token;
            const decoded = jwtDecode(token);
            localStorage.setItem('token', token);
            localStorage.setItem('user', JSON.stringify({
                id: decoded.sub,
                name: decoded.email,
                role: decoded.role
            }));
            console.log(response);
            navigate("/coordenador/dashboard");
        }catch(error) {
            console.log('Falha no login', error);
        }

    };

    return (
        <div className="main__container">
            <section className="coordenador-login">
                <div className="coordenador-login-frame">
                    <h1 className="coordenador-login-titulo">Fazer Login - Coordenador</h1>
                    <form onSubmit={(e) => {e.preventDefault(); handleSubmit('coordenador')}} className="coordenador-login-form">
                        <div className="coordenador-login-container">
                            <label htmlFor="login" className="coordenador-login-label">Login</label>
                            <input
                                type="text"
                                id="login"
                                name="login"
                                value={login}
                                onChange={(e) => setLogin(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="senha" className="coordenador-login-label">Senha</label>
                            <input
                                type="password"
                                id="senha"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <button
                                className="formulario-button"
                                type="submit"
                                onClick={handleSubmit}>
                                Entrar
                            </button>
                        </div>
                    </form>
                </div>

                <Link to="/egresso/login" className="coordenador-login-entrar-egresso">Entrar como Egresso</Link>

            </section>
        </div>
    );
};

export default Login;