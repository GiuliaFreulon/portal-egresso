import React from 'react';
import './GerenciarOportunidadeCard.css'
import {faUpRightAndDownLeftFromCenter, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const GerenciarOportunidadeCard = ({ titulo, status }) => {

    let statusCor = 'black';

    if(status === 'Rejeitado'){
        statusCor = 'red';
    } else if(status === 'Aprovado'){
        statusCor = 'green';
    }

    return (
        <div className="egresso-gerenciar-oportunidade-card-container">
            <div className="egresso-gerenciar-oportunidade-card-titulo">
                {titulo}
            </div>
            <button className="expand-btn"><FontAwesomeIcon icon={faUpRightAndDownLeftFromCenter}/></button>
            <p className="status-text" style={{color: statusCor}}>{status}</p>
            <button className="deny-btn"><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default GerenciarOportunidadeCard;