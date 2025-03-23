import React from 'react';
import './GerenciarEgressoCard.css'
import {faTrashCan, faRotate} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useNavigate} from "react-router-dom";

const GerenciarEgressoCard = ({ nome, id }) => {
    const navigate = useNavigate()

    return (
        <div className="gerenciamento-egresso-card-container">
            <div className="gerenciamento-egresso-card-nome">
                {nome}
            </div>
            <button className="refresh-btn" onClick={() => navigate(`/coordenador/egresso/atualizacao/${id}`)}>
                <FontAwesomeIcon icon={faRotate}/>
            </button>
            <button className="delete-btn">
                <FontAwesomeIcon icon={faTrashCan}/>
            </button>
        </div>
    );
};

export default GerenciarEgressoCard;