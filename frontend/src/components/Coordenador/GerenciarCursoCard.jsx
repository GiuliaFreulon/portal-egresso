import React from 'react';
import './GerenciarCursoCard.css'
import {faTrashCan, faRotate} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useNavigate} from "react-router-dom";

const GerenciarCursoCard = ({ nome, nivel }) => {
    const navigate = useNavigate()

    return (
        <div className="gerenciamento-curso-card-container">
            <div className="gerenciamento-curso-card-texto">
                {nome} - {nivel}
            </div>
            <button className="refresh-btn" onClick={() => navigate("/coordenador/curso/atualizacao")}>
                <FontAwesomeIcon icon={faRotate}/>
            </button>
            <button className="delete-btn">
                <FontAwesomeIcon icon={faTrashCan}/>
            </button>
        </div>
    );
};

export default GerenciarCursoCard;