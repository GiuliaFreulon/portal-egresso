import React from 'react';
import './EgressoCard.css'
import {redirect, useNavigate} from "react-router-dom";

const EgressoCard = ({ foto, nome, curso, descricao }) => {
    const navigate = useNavigate()

    return (
        <div className="egresso-card">
            <div className="egresso-info">
                <img src={foto} alt={`Foto de ${nome}`} className="egresso-foto" />
                <div className="text-container">
                    <h2 className="egresso-nome">{nome}</h2>
                    <h2 className="egresso-curso">Curso: {curso}</h2>
                    <span className="egresso-descricao">{descricao}</span>
                </div>
            </div>
            <div>
                <button className="botaoVerMais" onClick={() => navigate("/egressos/perfil/id:")}>Ver mais
                </button>
                <div className="separator-line"></div>
            </div>
        </div>

    );
};

export default EgressoCard;