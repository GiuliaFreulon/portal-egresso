import React, {useState} from 'react';
import './Envio.css'
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Envio = () => {
    const { user } = useAuth()

    const [titulo, setTitulo] = useState('');
    const [descricao, setDescricao] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            titulo,
            descricao,
        };

        try {
            setLoading(true);
            const response = await api.post(`/api/oportunidade/${user.id}`, formData);

            //reseta campos após cadastro
            setTitulo('');
            setDescricao('');
            alert("Oportunidade enviada com sucesso");

        }catch(error) {
            console.log(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="main__container">
            <section className="egresso-oportunidade-envio">
                <h1 className="line-text">Enviar Oportunidade</h1>
                <div className="egresso-oportunidade-envio-frame">
                    <form onSubmit={handleSubmit} className="egresso-oportunidade-envio-form">

                        <div className="">
                            <label htmlFor="titulo" className="egresso-criacao-discussao-label">Título*</label>
                            <input
                                type="text"
                                id="titulo"
                                name="titulo"
                                value={titulo}
                                onChange={(e) => setTitulo(e.target.value)}
                                required
                            />
                        </div>

                        <div className="">
                            <label htmlFor="descricao" className="egresso-oportunidade-envio-label">Descrição*</label>
                            <textarea
                                id="descricao"
                                name="descricao"
                                value={descricao}
                                maxLength={1000}
                                onChange={(e) => setDescricao(e.target.value)}
                                required
                                className="texto-input"
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
                                    type="submit">
                                    Enviar
                                </button>
                            )}
                        </div>
                    </form>
                </div>
            </section>
        </div>
    );
};

export default Envio;