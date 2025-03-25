import React, {useEffect, useRef, useState} from 'react';
import './Discussao.css'
import {faPaperPlane} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import MensagemCard from "../../../../components/Egresso/MensagemCard.jsx";
import {useParams} from "react-router-dom";
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Discussao = () => {
    const { user } = useAuth();
    const { id } = useParams()

    const [discussao, setDiscussao] = useState(null)
    const [listaMensagens, setListaMensagens] = useState(null)
    const [mensagem, setMensagem] = useState('');
    const textareaRef = useRef(null);
    const mensagensRef = useRef(null);

    useEffect(() => {

        const fetchDiscussao = async () => {
            try {
                const reponse = await api.get(`/api/discussao/buscarPorId/${id}`);
                setDiscussao(reponse.data);

                const responseMensagens = await api.get(`/api/mensagem/${reponse.data.id_discussao}`);
                setListaMensagens(responseMensagens.data);
            } catch (error) {
                console.error("Erro ao buscar discussao: ", error);
            }
        }

        fetchDiscussao();
        ajustarAltura();
    }, [id]);

    useEffect(() => {
        // Rola para o fundo quando as mensagens são atualizadas
        if (mensagensRef.current) {
            mensagensRef.current.scrollTop = mensagensRef.current.scrollHeight;
        }
    }, [listaMensagens]);

    //useEffect para polling
    useEffect(() => {
        const interval = setInterval(async () => {
            if (discussao?.id_discussao) {
                try {
                    const response = await api.get(`/api/mensagem/${discussao.id_discussao}`);
                    setListaMensagens(prev => {
                        // Atualiza apenas se houver novas mensagens
                        return JSON.stringify(prev) === JSON.stringify(response.data)
                            ? prev
                            : response.data;
                    });
                } catch (error) {
                    console.error("Erro ao atualizar mensagens:", error);
                }
            }
        }, 1000); // Consulta a cada 1 segundo

        return () => clearInterval(interval); // Limpa o intervalo ao desmontar
    }, [discussao?.id_discussao]); // Executa quando o ID da discussão muda

    // Função de autoajuste
    const ajustarAltura = () => {
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
        }
    };


    const handleSubmit = async () => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            texto: mensagem
        };

        try {
            const response = await api.post(`/api/mensagem/${discussao.id_discussao}/${user.id}`, formData);
            setMensagem('');
        } catch (error) {
            console.error("Erro ao enviar mensagens:", error);
        }
    };

    return (
        <div className="main__container main-container-discussao">
            <section className="egresso-discussao">
                <h1 className="line-text titulo-discussao-h1">{discussao?.titulo}</h1>
                <div className="egresso-discussao-frame">
                    <div className="egresso-discussao-mensagens" ref={mensagensRef}>
                        {listaMensagens?.map((mensagem) => (
                            <MensagemCard
                                mensagem={mensagem}
                                dataEnvio={mensagem?.dataEnvio}
                            />
                        ))}
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