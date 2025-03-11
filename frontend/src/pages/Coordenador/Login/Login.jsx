import React, {useState} from 'react';
import './Login.css'
import {Link, useNavigate} from "react-router-dom";
import {login as loginAPI} from "../../../services/authService.jsx";
import {jwtDecode} from "jwt-decode";

const Login = () => {
    const navigate = useNavigate()

    const [login, setLogin] = useState('');
    const [senha, setSenha] = useState('');

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
                login: decoded.sub,
                role: decoded.role
            }));
            console.log(response);
            navigate("/coordenador/dashboard");
        }catch(error) {
            console.log('Falha no login', error);
        }

    };

    // useEffect(() => {
    //     const fetchProfile = async () => {
    //         try {
    //             const profile = await getProfile();
    //             setLoginCoordenador(profile[0]);
    //             setSenha(profile[1]);
    //         } catch (error) {
    //             console.error('Erro ao buscar perfil:', error);
    //         }
    //     };
    //
    //     fetchProfile();
    // }, []);

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