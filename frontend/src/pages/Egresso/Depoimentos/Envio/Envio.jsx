import React, {useState} from 'react';
import './Envio.css'
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Envio = () => {
    const { user } = useAuth()

    const [texto, setTexto] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            texto,
        };

        try {
            setLoading(true);
            const response = await api.post(`/api/depoimento/${user.id}`, formData);

            //reseta campos após cadastro
            setTexto('');
            alert("Depoimento enviado com sucesso");

        }catch(error) {
            console.log(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="main__container">
            <section className="egresso-depoimento-envio">
                <h1 className="line-text">Enviar Depoimento</h1>
                <div className="egresso-depoimento-envio-frame">
                    <form onSubmit={handleSubmit} className="egresso-depoimento-envio-form">

                        <div className="">
                            <label htmlFor="texto" className="egresso-depoimento-envio-label">Depoimento*</label>
                            <textarea
                                id="texto"
                                name="texto"
                                value={texto}
                                maxLength={1000}
                                onChange={(e) => setTexto(e.target.value)}
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