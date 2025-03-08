import React from 'react';
import './GerenciarEgressoCard.css'
import {faTrashCan, faRotate} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const GerenciarEgressoCard = ({ nome }) => {
    return (
        <div className="gerenciamento-egresso-card-container">
            <div className="gerenciamento-egresso-card-nome">
                {nome}
            </div>
            <button className="refresh-btn"><FontAwesomeIcon icon={faRotate}/></button>
            <button className="delete-btn"><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default GerenciarEgressoCard;