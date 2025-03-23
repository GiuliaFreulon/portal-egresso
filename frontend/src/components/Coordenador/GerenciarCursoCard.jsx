import React from 'react';
import './GerenciarCursoCard.css'
import {faTrashCan, faRotate} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useNavigate} from "react-router-dom";
import api from "../../services/api.jsx";

const GerenciarCursoCard = ({ nome, nivel, id }) => {
    const navigate = useNavigate()

    const handleDelete = async () => {
        // Confirmação antes de deletar
        const confirmacao = window.confirm(
            `Tem certeza que deseja excluir o curso "${nome}"?\nEsta ação não pode ser desfeita!`
        );

        if (!confirmacao) return;

        try {
            const response = await api.delete(`/api/curso/${id}`);
            console.log(response);
            alert('Curso excluído com sucesso!');
            window.location.reload()
        } catch (error) {
            alert("Falha ao excluir curso: " + error.message);
            console.log('Falha na exclusão', error.response?.data || error.message);
        }
    }

    return (
        <div className="gerenciamento-curso-card-container">
            <div className="gerenciamento-curso-card-texto">
                {nome} - {nivel}
            </div>
            <button className="refresh-btn" onClick={() => navigate(`/coordenador/curso/atualizacao/${id}`)}>
                <FontAwesomeIcon icon={faRotate}/>
            </button>
            <button className="delete-btn" onClick={handleDelete}>
                <FontAwesomeIcon icon={faTrashCan}/>
            </button>
        </div>
    );
};

export default GerenciarCursoCard;