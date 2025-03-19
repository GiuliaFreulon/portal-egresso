import React, {useState} from 'react';
import './Edicao.css'

const Edicao = () => {

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

    };

    return (
        <div className="main__container">
            <section className="coordenador-edicao">
                <h1 className="line-text">Atualizar Dados</h1>
                <div className="coordenador-edicao-frame">
                    <form onSubmit={handleSubmit} className="coordenador-edicao-form">
                        <div className="coordenador-edicao-container">
                            <label htmlFor="login" className="coordenador-edicao-label">Login</label>
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
                            <label htmlFor="senha" className="coordenador-edicao-label">Senha</label>
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
                                Confirmar
                            </button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    );
};

export default Edicao;