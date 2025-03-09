import React, {useState} from 'react';
import './Cadastro.css'

const Cadastro = () => {

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
                                id="curso"
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
                                Cadastrar
                            </button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    );
};

export default Cadastro;