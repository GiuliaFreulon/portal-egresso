import React, {useEffect, useState} from 'react';
import './GerenciarDepoimentoCard.css'
import {faUpRightAndDownLeftFromCenter, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const GerenciarDepoimentoCard = ({ texto, status }) => {

    const [statusCor, setStatusCor] = useState('black');
    const [statusTexto, setStatusTexto] = useState('');

    useEffect(() => {
        if (status === "AGUARDANDO") {
            setStatusTexto('Enviado')
        }
        if(status === "REJEITADO"){
            setStatusCor('red');
            setStatusTexto('Rejeitado')
        } else if(status === "APROVADO"){
            setStatusCor('green');
            setStatusTexto('Aprovado')
        }
    })

    return (
        <div className="egresso-gerenciar-depoimento-card-container">
            <div className="egresso-gerenciar-depoimento-card-texto">
                {texto}
            </div>
            <button className="expand-btn"><FontAwesomeIcon icon={faUpRightAndDownLeftFromCenter}/></button>
            <p className="status-text" style={{color: statusCor}}>{statusTexto}</p>
            <button className="deny-btn"><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default GerenciarDepoimentoCard;