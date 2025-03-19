import React, {useState} from 'react';
import './Criacao.css'

const Criacao = () => {

    const [nome, setNome] = useState('');

    const handleSubmit = () => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            nome,
        };

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

export default Criacao;