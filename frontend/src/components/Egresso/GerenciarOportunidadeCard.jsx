import React, {useEffect, useState} from 'react';
import './GerenciarOportunidadeCard.css'
import {
    faUpRightAndDownLeftFromCenter,
    faTrashCan,
    faDownLeftAndUpRightToCenter
} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import api from "../../services/api.jsx";

const GerenciarOportunidadeCard = ({ oportunidade }) => {

    const [statusCor, setStatusCor] = useState('black');
    const [statusTexto, setStatusTexto] = useState('');
    const [expanded, setExpanded] = useState(false);
    const [expandIcon, setExpandIcon] = useState(faUpRightAndDownLeftFromCenter);

    useEffect(() => {
        if (oportunidade.status === "AGUARDANDO") {
            setStatusTexto('Enviado')
        }
        if(oportunidade.status === "REJEITADO"){
            setStatusCor('red');
            setStatusTexto('Rejeitado')
        } else if(oportunidade.status === "APROVADO"){
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
            `Tem certeza que deseja excluir a oportunidade "${oportunidade.titulo}"?\nEsta ação não pode ser desfeita!`
        );

        if (!confirmacao) return;

        try {
            const response = await api.delete(`/api/oportunidade/${oportunidade.id_oportunidade}`);
            console.log(response);
            alert('Oportunidade excluída com sucesso!');
            window.location.reload()
        } catch (error) {
            alert("Falha ao excluir oportunidade: " + error.message);
            console.log('Falha na exclusão', error.response?.data || error.message);
        }
    }

    return (
        <div className="egresso-gerenciar-oportunidade-card-container">
            {expanded ? (
                <div className="egresso-gerenciar-oportunidade-card-titulo" style={{overflow: "unset", whiteSpace: "wrap", textOverflow: "unset"}}>
                    {oportunidade.titulo} <br/><br/> {oportunidade.descricao}
                </div>
            ) : (
                <div className="egresso-gerenciar-oportunidade-card-titulo">
                    {oportunidade.titulo}
                </div>
            )}
            <button className="expand-btn" onClick={handleExpand}><FontAwesomeIcon icon={expandIcon}/></button>
            <p className="status-text" style={{color: statusCor}}>{statusTexto}</p>
            <button className="deny-btn" onClick={handleDelete}><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default GerenciarOportunidadeCard;