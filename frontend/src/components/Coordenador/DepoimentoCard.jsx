import React, {useState} from 'react';
import './DepoimentoCard.css'
import {faCircleCheck, faCircleXmark} from "@fortawesome/free-regular-svg-icons";
import {faUpRightAndDownLeftFromCenter, faDownLeftAndUpRightToCenter} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import api from "../../services/api.jsx";

const DepoimentoCard = ({ depoimento }) => {

    const [expanded, setExpanded] = useState(false);
    const [expandIcon, setExpandIcon] = useState(faUpRightAndDownLeftFromCenter);

    const handleExpand = () => {
        setExpanded(!expanded);
        if (expandIcon === faUpRightAndDownLeftFromCenter) {
            setExpandIcon(faDownLeftAndUpRightToCenter)
        } else {
            setExpandIcon(faUpRightAndDownLeftFromCenter)
        }
    }

    const handleApprove = async () => {
        const formData = {
            status: "APROVADO"
        }

        try {
            const response = await api.put(`/api/depoimento/${depoimento?.id_depoimento}`, formData);
            console.log(response);
            alert('Depoimento aprovado!');
            window.location.reload()
        } catch (error) {
            alert("Falha ao aprovar depoimento: " + error.message);
            console.log('Falha ao aprovar', error.response?.data || error.message);
        }
    }

    const handleDeny = async () => {
        const formData = {
            status: "REJEITADO"
        }

        try {
            const response = await api.put(`/api/depoimento/${depoimento?.id_depoimento}`, formData);
            console.log(response);
            alert('Depoimento rejeitado!');
            window.location.reload()
        } catch (error) {
            alert("Falha ao rejeitar depoimento: " + error.message);
            console.log('Falha ao rejeitar', error.response?.data || error.message);
        }
    }

    return (
        <div className="coordenador-depoimento-card-container">
            {expanded ? (
                <div className="coordenador-depoimento-card-nome">
                    {depoimento?.egresso?.nome} - {depoimento.texto}
                </div>
            ) : (
                <div className="coordenador-depoimento-card-nome">
                    {depoimento?.egresso?.nome}
                </div>
            )}
            <button className="expand-btn" onClick={handleExpand}><FontAwesomeIcon icon={expandIcon}/></button>
            <button className="approve-btn" onClick={handleApprove}><FontAwesomeIcon icon={faCircleCheck}/></button>
            <button className="deny-btn" onClick={handleDeny}><FontAwesomeIcon icon={faCircleXmark}/></button>
        </div>
    );
};

export default DepoimentoCard;