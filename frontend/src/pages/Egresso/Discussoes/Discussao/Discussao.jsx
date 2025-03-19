import React, {useState, useRef, useEffect} from 'react';
import './Discussao.css'
import {faPaperPlane} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import MensagemCard from "../../../../components/Egresso/MensagemCard.jsx";

const Discussao = () => {

    const [mensagem, setMensagem] = useState('');
    const textareaRef = useRef(null);

    // Função de autoajuste
    const ajustarAltura = () => {
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
        }
    };

    // Ajusta a altura sempre que a mensagem mudar
    useEffect(() => {
        ajustarAltura();
    }, [mensagem]);

    const handleSubmit = () => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            mensagem,
        };


        setMensagem('');
    };

    return (
        <div className="main__container">
            <section className="egresso-discussao">
                <h1 className="line-text">Título do Grupo</h1>
                <div className="egresso-discussao-frame">
                    <div className="egresso-discussao-mensagens">
                        <MensagemCard
                            nome='Nome do Egresso'
                            dataHora='dd/mm/aa - 01/01/25'
                            mensagem={'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eu efficitur massa. Fusce erat purus, ornare quis lacinia sit amet, auctor sodales velit. Sed sed dictum ipsum, vitae rutrum ipsum. Sed id placerat velit. Morbi euismod quis odio in varius. In suscipit quam eros, vitae aliquam risus rhoncus sodales. In bibendum gravida erat, eget dignissim mi dictum at.'}
                        />
                    </div>
                    <form onSubmit={handleSubmit} className="egresso-discussao-mensagem-form">

                        <div className="egresso-discussao-envio-mensagem">
                            <textarea
                                ref={textareaRef}
                                id="mensagem"
                                name="mensagem"
                                value={mensagem}
                                placeholder="Escreva sua mensagem aqui"
                                onChange={(e) => setMensagem(e.target.value)}
                                onKeyDown={(e) => {
                                    if (e.key === 'Enter' && !e.shiftKey) {
                                        e.preventDefault(); // Evita nova linha ao pressionar Enter
                                        handleSubmit();
                                    }
                                }}
                                required
                                className='texto-input egresso-discussao-envio-mensagem-texto'
                            />
                            <div>
                                <button
                                    className="egresso-discussao-envio-mensagem-btn"
                                    type="button"
                                    onClick={() => handleSubmit()}>
                                    <FontAwesomeIcon icon={faPaperPlane} />
                                </button>
                            </div>
                        </div>
                    </form>

                </div>
            </section>
        </div>
    );
};

export default Discussao;