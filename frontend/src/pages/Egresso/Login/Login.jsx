import React, {useState} from 'react';
import './Login.css'

const Login = () => {

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    const handleSubmit = (userType) => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            email,
            senha,
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
        window.location.href = "/egresso_dashboard";
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

                <a href="#" className="egresso-login-entrar-egresso">Entrar como Coordenador</a>

            </section>
        </div>
    );
};

export default Login;