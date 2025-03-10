import React from 'react';
import './MensagemCard.css'

const MensagemCard = ({ foto, nome, dataHora, mensagem }) => {
    return (
        <div className="mensagem-card">
            <img src={foto} alt={`Foto de ${nome}`} className="mensagem-card-foto" />
            <div className="mensagem-card-info">
                <div className="mensagem-card-text-container">
                    <div className="mensagem-card-nome-data">
                        <span className="mensagem-card-nome">{nome}</span>
                        <span className="mensagem-card-data">{dataHora}</span>
                    </div>
                    <span className="mensagem-card-texto">{mensagem}</span>
                </div>
            </div>
        </div>

    );
};

export default MensagemCard;