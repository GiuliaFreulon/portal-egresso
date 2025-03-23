import React, {useState} from 'react';
import './Cadastro.css'
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Cadastro = () => {
    const { user } = useAuth()

    const [nome, setNome] = useState('');
    const [nivel, setNivel] = useState('');
    const [loading, setLoading] = useState(false);

    // Lista de cursos exemplo
    const niveis = [
        { id: 1, nivel: 'Graduação'},
        { id: 2, nivel: 'Pós-Graduação'},
        { id: 3, nivel: 'Mestrado'},
        { id: 4, nivel: 'Doutorado'}
    ];

    const handleSubmit = async (event) => {
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
            const response = await api.post(`/api/coordenador/cadastrarCurso/${user.id}`, formData);

            //reseta campos após cadastro
            setNome('');
            setNivel('');
            alert("Curso cadastrado com sucesso");

        }catch(error) {
            alert("falha no cadastro" + error.message);
            console.log('Falha no cadastro', error.response?.data || error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="main__container">
            <section className="coordenador-cadastro-curso">
                <h1 className="line-text">Cadastrar Curso</h1>
                <div className="coordenador-cadastro-curso-frame">
                    <form onSubmit={handleSubmit} className="coordenador-cadastro-curso-form">

                        <div className="">
                            <label htmlFor="nome" className="coordenador-cadastro-curso-label">Nome do Curso*</label>
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
                            <label htmlFor="nivel" className="coordenador-cadastro-curso-label">Nível*</label>

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
                                    type="submit">
                                    Cadastrar
                                </button>
                            )}
                        </div>

                    </form>
                </div>
            </section>
        </div>
    );
};

export default Cadastro;