import React from 'react';
import './GerenciarEgressoCard.css'
import {faRotate, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useNavigate} from "react-router-dom";
import api from "../../services/api.jsx";

const GerenciarEgressoCard = ({ nome, id }) => {
    const navigate = useNavigate()

    const handleDelete = async () => {
        // Confirmação antes de deletar
        const confirmacao = window.confirm(
            `Tem certeza que deseja excluir o egresso "${nome}"?\nEsta ação não pode ser desfeita!`
        );

        if (!confirmacao) return;

        try {
            const response = await api.delete(`/api/egresso/${id}`);
            console.log(response);
            alert('Egresso excluído com sucesso!');
            window.location.reload()
        } catch (error) {
            alert("Falha ao excluir egresso: " + error.message);
            console.log('Falha na exclusão', error.response?.data || error.message);
        }
    }

    return (
        <div className="gerenciamento-egresso-card-container">
            <div className="gerenciamento-egresso-card-nome">
                {nome}
            </div>
            <button className="refresh-btn" onClick={() => navigate(`/coordenador/egresso/atualizacao/${id}`)}>
                <FontAwesomeIcon icon={faRotate}/>
            </button>
            <button className="delete-btn" onClick={handleDelete}>
                <FontAwesomeIcon icon={faTrashCan} />
            </button>
        </div>
    );
};

export default GerenciarEgressoCard;