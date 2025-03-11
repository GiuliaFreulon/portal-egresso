import React, {useState} from 'react';
import './Login.css'
import {Link, useNavigate} from "react-router-dom";
import {login} from "../../../services/authService.jsx";
import {jwtDecode} from "jwt-decode";

const Login = () => {
    const navigate = useNavigate()

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    const handleSubmit = async (event) => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            email,
            senha,
        };

        // teste
        console.log('Dados enviados:', JSON.stringify(formData, null, 2));

        try {
            const response = await login(formData);
            const token = response.token;
            const decoded = jwtDecode(token);
            localStorage.setItem('token', token);
            localStorage.setItem('user', JSON.stringify({
                email: decoded.sub,
                role: decoded.role
            }));
            console.log(response);
            navigate("/egresso/dashboard");
        }catch(error) {
            console.log('Falha no login', error);
        }

        // useEffect(() => {
        //     const fetchProfile = async () => {
        //         try {
        //             const profile = await getProfile();
        //             setEmail(profile[0]);
        //             setSenha(profile[1]);
        //         } catch (error) {
        //             console.error('Erro ao buscar perfil:', error);
        //         }
        //     };
        //
        //     fetchProfile();
        // }, []);
    };

    return (
        <div className="main__container">
            <section className="egresso-login">
                <div className="egresso-login-frame">
                    <h1 className="egresso-login-titulo">Fazer Login - Egresso</h1>
                    <form onSubmit={handleSubmit} className="egresso-login-form">
                        <div className="egresso-login-container">
                            <label htmlFor="email" className="egresso-login-label">E-mail</label>
                            <input
                                type="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="senha" className="egresso-login-label">Senha</label>
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

                <Link to="/coordenador/login" className="egresso-login-entrar-coordenador">Entrar como Coordenador</Link>

            </section>
        </div>
    );
};

export default Login;