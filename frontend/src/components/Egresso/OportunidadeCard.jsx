import React from 'react';
import './OportunidadeCard.css'

const OportunidadeCard = ({ titulo, autor, descricao }) => {
    return (
        <div className="oportunidade-card">
            <div className="oportunidade-card-egresso-info">
                <div className="oportunidade-container">
                    <p className="oportunidade-titulo">{titulo}</p>
                    <p className="oportunidade-autor">Publicado por: {autor}</p>
                    <span className="oportunidade-descricao">{descricao}</span>
                </div>
            </div>
            <div className="oportunidade-card-separator">
                <div className="separator-line"></div>
            </div>
        </div>

    );
};

export default OportunidadeCard;