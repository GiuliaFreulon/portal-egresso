import React, {useState} from 'react';
import './Login.css'

const Login = () => {

    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (userType) => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            login,
            password,
            userType // 'coordenador' ou 'egresso'
        };

        // Simula o envio do JSON (substitua por fetch/axios)
        console.log('Dados enviados:', JSON.stringify(formData, null, 2));

        // Aqui você faria a chamada à API:
        // fetch('/api/login', {
        //   method: 'POST',
        //   headers: { 'Content-Type': 'application/json' },
        //   body: JSON.stringify(formData)
        // })
    };

    return (
        <div className="main__container">
            <section className="coordenador-login">
                <div className="coordenador-login-frame">
                    <h1 className="coordenador-login-titulo">Fazer Login - Coordenador</h1>
                    <form onSubmit={handleSubmit} className="coordenador-login-form">
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
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <button
                                className="formulario-button"
                                type="button"
                                onClick={() => handleSubmit('coordenador')}>
                                Entrar
                            </button>
                        </div>
                    </form>
                </div>

                <a href="#" className="coordenador-login-entrar-egresso">Entrar como Egresso</a>

            </section>
        </div>
    );
};

export default Login;