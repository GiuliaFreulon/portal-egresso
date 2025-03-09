import React from 'react';
import './DepoimentoCard.css'
import {faCircleCheck, faCircleXmark} from "@fortawesome/free-regular-svg-icons";
import {faUpRightAndDownLeftFromCenter} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const GerenciarEgressoCard = ({ nome }) => {
    return (
        <div className="coordenador-depoimento-card-container">
            <div className="coordenador-depoimento-card-nome">
                {nome}
            </div>
            <button className="expand-btn"><FontAwesomeIcon icon={faUpRightAndDownLeftFromCenter}/></button>
            <button className="approve-btn"><FontAwesomeIcon icon={faCircleCheck}/></button>
            <button className="deny-btn"><FontAwesomeIcon icon={faCircleXmark}/></button>
        </div>
    );
};

export default GerenciarEgressoCard;