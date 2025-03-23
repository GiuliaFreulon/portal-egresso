import React, {useState} from 'react';
import './Criacao.css'
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Criacao = () => {
    const { user } = useAuth()

    const [nome, setNome] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            titulo: nome,
        };

        try {
            setLoading(true);
            const response = await api.post(`/api/discussao/${user.id}`, formData);

            //reseta campos após cadastro
            setNome('');
            alert("Discussão criada com sucesso");

        }catch(error) {
            console.log(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="main__container">
            <section className="egresso-criacao-discussao">
                <h1 className="line-text">Criar Grupo de Discussão</h1>
                <div className="egresso-criacao-discussao-frame">
                    <form onSubmit={handleSubmit} className="egresso-criacao-discussao-form">

                        <div className="">
                            <label htmlFor="nome" className="egresso-criacao-discussao-label">Nome do Grupo*</label>
                            <input
                                type="text"
                                id="nome"
                                name="nome"
                                value={nome}
                                onChange={(e) => setNome(e.target.value)}
                                required
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

export default Criacao;