import React, {useState} from 'react';
import './Atualizacao.css'

const Atualizacao = () => {

    const [nome, setNome] = useState('');
    const [nivel, setNivel] = useState('');

    // Lista de cursos exemplo
    const niveis = [
        { id: 1, nivel: 'Graduação'},
        { id: 2, nivel: 'Pós-Graduação'},
        { id: 3, nivel: 'Mestrado'}
    ];

    const handleSubmit = () => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            nome,
            nivel
        };

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

export default Atualizacao;