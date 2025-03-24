import React, {useEffect, useState} from 'react';
import './GerenciarDepoimentoCard.css'
import {faUpRightAndDownLeftFromCenter, faTrashCan, faDownLeftAndUpRightToCenter} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import api from "../../services/api.jsx";

const GerenciarDepoimentoCard = ({ depoimento }) => {

    const [statusCor, setStatusCor] = useState('black');
    const [statusTexto, setStatusTexto] = useState('');
    const [expanded, setExpanded] = useState(false);
    const [expandIcon, setExpandIcon] = useState(faUpRightAndDownLeftFromCenter);


    useEffect(() => {
        if (depoimento.status === "AGUARDANDO") {
            setStatusTexto('Enviado')
        }
        if(depoimento.status === "REJEITADO"){
            setStatusCor('red');
            setStatusTexto('Rejeitado')
        } else if(depoimento.status === "APROVADO"){
            setStatusCor('green');
            setStatusTexto('Aprovado')
        }
    })

    const handleExpand = () => {
        setExpanded(!expanded);
        if (expandIcon === faUpRightAndDownLeftFromCenter) {
            setExpandIcon(faDownLeftAndUpRightToCenter)
        } else {
            setExpandIcon(faUpRightAndDownLeftFromCenter)
        }
    }

    const handleDelete = async () => {
        // Confirmação antes de deletar
        const confirmacao = window.confirm(
            `Tem certeza que deseja excluir o depoimento?\nEsta ação não pode ser desfeita!`
        );

        if (!confirmacao) return;

        try {
            const response = await api.delete(`/api/depoimento/${depoimento.id_depoimento}`);
            console.log(response);
            alert('Depoimento excluído com sucesso!');
            window.location.reload()
        } catch (error) {
            alert("Falha ao excluir depoimento: " + error.message);
            console.log('Falha na exclusão', error.response?.data || error.message);
        }
    }

    return (
        <div className="egresso-gerenciar-depoimento-card-container">
            {expanded ? (
                <div className="egresso-gerenciar-depoimento-card-texto" style={{overflow: "unset", whiteSpace: "wrap", textOverflow: "unset"}}>
                    {depoimento.texto}
                </div>
            ) : (
                <div className="egresso-gerenciar-depoimento-card-texto">
                    {depoimento.texto}
                </div>
            )}
            <button className="expand-btn" onClick={handleExpand}><FontAwesomeIcon icon={expandIcon}/></button>
            <p className="status-text" style={{color: statusCor}}>{statusTexto}</p>
            <button className="deny-btn" onClick={handleDelete}><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default GerenciarDepoimentoCard;