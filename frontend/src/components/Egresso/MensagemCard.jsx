import './MensagemCard.css'
import {useAuth} from "../../contexts/AuthContext.jsx";
import {useEffect, useState} from "react";

const MensagemCard = ({ mensagem }) => {
    const { user } = useAuth()
    const [mensagemDoUser, setMensagemDoUser] = useState('')

    useEffect(() => {
        if (mensagem?.egresso?.id_egresso === user.id) {
            setMensagemDoUser('-user')
        }
    })

    return (
        <div className={`mensagem-card${mensagemDoUser}`}>
            <img src={mensagem?.egresso?.foto} alt={`Foto de ${mensagem?.egresso?.nome}`} className={`mensagem-card-foto${mensagemDoUser}`} />
            <div className={`mensagem-card-info${mensagemDoUser}`}>
                <div className="mensagem-card-text-container">
                    <div className="mensagem-card-nome-data">
                        <span className="mensagem-card-nome">{mensagem?.egresso?.nome}</span>
                        <span className="mensagem-card-data">
                            {mensagem?.dataEnvio?.[2]}/{mensagem?.dataEnvio?.[1]}/{mensagem?.dataEnvio?.[0]} -
                            {mensagem?.dataEnvio?.[3]}:{mensagem?.dataEnvio?.[4]}
                        </span>
                    </div>
                    <span className="mensagem-card-texto">{mensagem?.texto}</span>
                </div>
            </div>
        </div>

    );
};

export default MensagemCard;