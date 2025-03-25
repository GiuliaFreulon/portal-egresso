import React from 'react';
import './DepoimentoCard.css'

const DepoimentoCard = ({ foto, nome, curso, descricao, data }) => {
    return (
        <div className="depoimento-card">
            <div className="depoimento-card-egresso-info">
                <img src={`data:image/jpeg;base64, ${foto}`} alt={`Foto de ${nome}`} className="depoimento-card-foto" />
                <div className="depoimento-card-text-container">
                    <div className="depoimento-card-nome-data">
                        <span className="depoimento-card-nome">{nome}</span>
                        <span className="depoimento-card-data">{data[2]}/{data[1]}/{data[0]}</span>
                    </div>
                    <span className="depoimento-card-curso">Curso(s): {curso}</span>
                    <span className="depoimento-card-descricao">{descricao}</span>
                </div>
            </div>
            <div className="depoimento-card-separator">
                <div className="separator-line"></div>
            </div>
        </div>

    );
};

export default DepoimentoCard;