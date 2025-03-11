import React, {useState} from 'react';
import './Login.css'
import {Link} from "react-router-dom";
import {login} from "../../../services/authService.jsx";

const Login = () => {

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    const handleSubmit = async () => {
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
            const {token} = await login(formData);
            localStorage.setItem('token', token);
        }catch(error) {
            console.log('Falha no login', error);
        }

        // window.location.href = "/egresso_dashboard";
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
                                type="button"
                                onClick={() => handleSubmit('egresso')}>
                                Entrar
                            </button>
                        </div>
                    </form>
                </div>

                <Link to="/coordenador_login" className="egresso-login-entrar-coordenador">Entrar como Coordenador</Link>

            </section>
        </div>
    );
};

export default Login;