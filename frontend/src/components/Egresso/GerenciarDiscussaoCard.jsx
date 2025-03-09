import React from 'react';
import './GerenciarDiscussaoCard.css'
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const GerenciarDiscussaoCard = ({ titulo }) => {
    return (
        <div className="egresso-gerenciamento-discussao-card-container">
            <div className="egresso-gerenciamento-discussao-card-titulo">
                {titulo}
            </div>
            <button className="delete-btn"><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default GerenciarDiscussaoCard;