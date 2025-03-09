import React from 'react';
import './GerenciarCursoCard.css'
import {faTrashCan, faRotate} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const GerenciarCursoCard = ({ nome, nivel }) => {
    return (
        <div className="gerenciamento-curso-card-container">
            <div className="gerenciamento-curso-card-texto">
                {nome} - {nivel}
            </div>
            <button className="refresh-btn"><FontAwesomeIcon icon={faRotate}/></button>
            <button className="delete-btn"><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default GerenciarCursoCard;