import React, {useEffect, useState} from 'react';
import './Edicao.css'
import api from "../../../services/api.jsx";
import {useAuth} from "../../../contexts/AuthContext.jsx";

const Edicao = () => {
    const { user } = useAuth()

    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLogin(user.name)
    }, [user.name]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            login,
            senha: password
        };

        try {
            //atualiza coordenador
            setLoading(true);
            const response = await api.put(`/api/coordenador/${user.id}`, formData);

            //reseta campos após cadastro
            setLogin(login);
            setPassword('');

            alert("Coordenador atualizado com sucesso");

        }catch(error) {
            console.log("Erro ao atualizar coordenador:", error);
        } finally {
            setLoading(false);
        }

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
                            />
                        </div>

                        <div>
                            <label htmlFor="senha" className="coordenador-edicao-label">Senha</label>
                            <input
                                type="password"
                                id="senha"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>

                        <div>
                            {loading ? (
                                <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                                    <div className="skeleton-loader"></div>
                                </div>
                            ) : (
                                <button
                                    className="formulario-button"
                                    type="submit"
                                    onClick={() => handleSubmit()}>
                                    Confirmar
                                </button>
                            )}
                        </div>

                    </form>
                </div>
            </section>
        </div>
    );
};

export default Edicao;