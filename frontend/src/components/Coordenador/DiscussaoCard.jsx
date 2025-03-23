import React from 'react';
import './DiscussaoCard.css'
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import api from "../../services/api.jsx";

const DiscussaoCard = ({ titulo, id }) => {

    const handleDelete = async () => {
        // Confirmação antes de deletar
        const confirmacao = window.confirm(
            `Tem certeza que deseja excluir o grupo de discussão: "${titulo}"?\nEsta ação não pode ser desfeita!`
        );

        if (!confirmacao) return;

        try {
            const response = await api.delete(`/api/discussao/${id}`);
            console.log(response);
            alert('Grupo excluído com sucesso!');
            window.location.reload()
        } catch (error) {
            alert("Falha ao excluir grupo: " + error.message);
            console.log('Falha na exclusão', error.response?.data || error.message);
        }
    }

    return (
        <div className="discussao-card-container">
            <div className="discussao-card-texto">
                {titulo}
            </div>
            <button className="delete-btn" onClick={handleDelete}><FontAwesomeIcon icon={faTrashCan}/></button>
        </div>
    );
};

export default DiscussaoCard;