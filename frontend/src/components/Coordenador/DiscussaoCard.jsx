import React from 'react';
import './DiscussaoCard.css'
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const DiscussaoCard = ({ titulo }) => {
    return (
        <div className="discussao-card-container">
            <div className="discussao-card-texto">
                {titulo}
            </div>
            <button className="delete-btn"><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default DiscussaoCard;