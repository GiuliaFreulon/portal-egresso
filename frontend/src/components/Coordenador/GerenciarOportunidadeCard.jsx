import React, {useState} from 'react';
import './GerenciarOportunidadeCard.css'
import {faCircleCheck, faCircleXmark} from "@fortawesome/free-regular-svg-icons";
import {faUpRightAndDownLeftFromCenter, faDownLeftAndUpRightToCenter} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import api from "../../services/api.jsx";

const GerenciarEgressoCard = ({ oportunidade }) => {

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

        console.log(formData)

        try {
            const response = await api.put(`/api/oportunidade/${oportunidade?.id_oportunidade}`, formData);
            console.log(response);
            window.location.reload()
            alert('Oportunidade aprovada!');
        } catch (error) {
            alert("Falha ao aprovar oportunidade: " + error.message);
            console.log('Falha ao aprovar', error.response?.data || error.message);
        }
    }

    const handleDeny = async () => {
        const formData = {
            status: "REJEITADO"
        }

        try {
            const response = await api.put(`/api/oportunidade/${oportunidade?.id_oportunidade}`, formData);
            console.log(response);
            window.location.reload()
            alert('Oportunidade rejeitada!');
        } catch (error) {
            alert("Falha ao rejeitar oportunidade: " + error.message);
            console.log('Falha ao rejeitar', error.response?.data || error.message);
        }
    }


    return (
        <div className="coordenador-oportunidade-card-container">
            {expanded ? (
                <div className="coordenador-oportunidade-card-nome">
                    {oportunidade?.egresso?.nome} - {oportunidade.titulo} <br/><br/> {oportunidade.descricao}
                </div>
            ) : (
                <div className="coordenador-oportunidade-card-nome">
                    {oportunidade?.egresso?.nome} - {oportunidade.titulo}
                </div>
            )}
            <button className="expand-btn" onClick={handleExpand}><FontAwesomeIcon icon={expandIcon}/></button>
            <button className="approve-btn" onClick={handleApprove}><FontAwesomeIcon icon={faCircleCheck}/></button>
            <button className="deny-btn" onClick={handleDeny}><FontAwesomeIcon icon={faCircleXmark}/></button>
        </div>
    );
};

export default GerenciarEgressoCard;