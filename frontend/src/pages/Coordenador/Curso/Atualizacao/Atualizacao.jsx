import React, {useEffect, useState} from 'react';
import './Atualizacao.css'
import {useParams} from "react-router-dom";
import {useAuth} from "../../../../contexts/AuthContext.jsx";
import api from "../../../../services/api.jsx";

const Atualizacao = () => {
    const { id } = useParams()
    const { user } = useAuth()

    const [nome, setNome] = useState('');
    const [nivel, setNivel] = useState('');
    const [loading, setLoading] = useState(false);

    // Lista de cursos
    const niveis = [
        { id: 1, nivel: 'Graduação'},
        { id: 2, nivel: 'Pós-Graduação'},
        { id: 3, nivel: 'Mestrado'},
        { id: 4, nivel: 'Doutorado'}
    ];

    useEffect(() => {
        const fetchEgresso = async () => {
            try {
                const response = await api.get(`/api/curso/buscarPorId/${id}`);

                setNome(response.data.nome);
                setNivel(response.data.nivel);

            } catch (error) {
                console.log("Erro ao buscar egresso:", error);
            }
        }

        fetchEgresso();
    }, [id]);

    const handleSubmit = async () => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            nome,
            nivel
        };

        try {
            //cadastra curso
            setLoading(true);
            const response = await api.put(`/api/curso/${id}`, formData);

            //reseta campos após cadastro
            setNome('');
            setNivel('');
            alert("Curso atualizado com sucesso");

        }catch(error) {
            alert("falha na atualização" + error.message);
            console.log('Falha na atualização', error.response?.data || error.message);
        } finally {
            setLoading(false);
        }

    };

    return (
        <div className="main__container">
            <section className="coordenador-atualizacao-curso">
                <h1 className="line-text">Atualizar Curso</h1>
                <div className="coordenador-atualizacao-curso-frame">
                    <form onSubmit={handleSubmit} className="coordenador-atualizacao-curso-form">

                        <div className="">
                            <label htmlFor="nome" className="coordenador-atualizacao-curso-label">Nome do Curso*</label>
                            <input
                                type="text"
                                id="nome"
                                name="nome"
                                value={nome}
                                onChange={(e) => setNome(e.target.value)}
                                required
                            />
                        </div>

                        <div className="">
                            <label htmlFor="nivel" className="coordenador-atualizacao-curso-label">Nível*</label>

                            <select
                                id="nivel"
                                value={nivel}
                                onChange={(e) => setNivel(e.target.value)}
                                required
                                className="drop-down-input"
                            >
                                <option value="">Selecione um nivel</option>
                                {niveis.map((curso) => (
                                    <option key={curso.id} value={curso.nivel}>{curso.nivel}</option>
                                ))
                                }
                            </select>
                        </div>

                        <div>
                            {loading ? (
                                <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                                    <div className="skeleton-loader"></div>
                                </div>
                            ) : (
                                <button
                                    className="formulario-button"
                                    type="button"
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

export default Atualizacao;