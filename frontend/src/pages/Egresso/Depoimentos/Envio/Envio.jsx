import React, {useState} from 'react';
import './Envio.css'

const Envio = () => {

    const [texto, setTexto] = useState('');

    const handleSubmit = () => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            texto,
        };

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
                                onChange={(e) => setTexto(e.target.value)}
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