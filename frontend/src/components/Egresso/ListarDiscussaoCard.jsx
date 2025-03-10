import React from 'react';
import './ListarDiscussaoCard.css'
import {faArrowAltCircleRight} from "@fortawesome/free-regular-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const ListarDiscussaoCard = ({ titulo }) => {
    return (
        <div className="egresso-listagem-discussao-card-container">
            <div className="egresso-listagem-discussao-card-titulo">
                {titulo}
            </div>
            <button className="selection-btn"><FontAwesomeIcon icon={faArrowAltCircleRight}/></button>
        </div>
    );
};

export default ListarDiscussaoCard;