import React, {useState} from 'react';
import './Login.css'
import {Link} from "react-router-dom";
import {login} from "../../../services/authService.jsx";

const Login = () => {

    const [loginCoordenador, setLoginCoordenador] = useState('');
    const [senha, setSenha] = useState('');

    const handleSubmit = async () => {
        // Cria o objeto com os dados
        const formData = {
            "login": loginCoordenador,
            senha,
        };

        // teste
        console.log('Dados enviados:', JSON.stringify(formData, null, 2));

        try {
            const {token} = await login(formData);
            localStorage.setItem('token', token);
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
                                value={loginCoordenador}
                                onChange={(e) => setLoginCoordenador(e.target.value)}
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
                                onSubmit={() => handleSubmit('coordenador')}>
                                Entrar
                            </button>
                        </div>
                    </form>
                </div>

                <Link to="/egresso_login" className="coordenador-login-entrar-egresso">Entrar como Egresso</Link>

            </section>
        </div>
    );
};

export default Login;