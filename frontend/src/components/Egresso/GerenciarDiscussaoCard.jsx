import React from 'react';
import './GerenciarDiscussaoCard.css'
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useNavigate} from "react-router-dom";
import api from "../../services/api.jsx";

const GerenciarDiscussaoCard = ({ titulo, id }) => {
    const navigate = useNavigate()

    const handleDelete = async () => {
        // Confirmação antes de deletar
        const confirmacao = window.confirm(
            `Tem certeza que deseja excluir a discussão "${titulo}"?\nEsta ação não pode ser desfeita!`
        );

        if (!confirmacao) return;

        try {
            const response = await api.delete(`/api/discussao/${id}`);
            alert('Discussão excluída com sucesso!');
            window.location.reload()
        } catch (error) {
            alert("Falha ao excluir discussão: " + error.message);
            console.log('Falha na exclusão', error.response?.data || error.message);
        }
    }

    return (
        <div className="egresso-gerenciamento-discussao-card-container">
            <div className="egresso-gerenciamento-discussao-card-titulo">
                {titulo}
            </div>
            <button className="delete-btn" onClick={handleDelete}><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default GerenciarDiscussaoCard;