import React, {useState} from 'react';
import './Envio.css'

const Envio = () => {

    const [titulo, setTitulo] = useState('');
    const [descricao, setDescricao] = useState('');

    const handleSubmit = () => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            descricao,
        };

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
                                onChange={(e) => setDescricao(e.target.value)}
                                required
                                className="texto-input"
                            />
                        </div>

                        <div>
                            <button
                                className="formulario-button"
                                type="button"
                                onClick={() => handleSubmit()}>
                                Confirmar
                            </button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    );
};

export default Envio;