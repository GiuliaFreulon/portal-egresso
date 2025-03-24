import React from 'react';
import './ListarDiscussaoCard.css'
import {faArrowAltCircleRight} from "@fortawesome/free-regular-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useNavigate} from "react-router-dom";

const ListarDiscussaoCard = ({ titulo, id }) => {
    const navigate = useNavigate();
    return (
        <div className="egresso-listagem-discussao-card-container">
            <div className="egresso-listagem-discussao-card-titulo">
                {titulo}
            </div>
            <button className="selection-btn"  onClick={() => navigate(`/egresso/discussao/${id}`)}>
                <FontAwesomeIcon icon={faArrowAltCircleRight}/>
            </button>
        </div>
    );
};

export default ListarDiscussaoCard;