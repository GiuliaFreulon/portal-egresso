import React from 'react';
import './EgressoCard.css'
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../contexts/AuthContext.jsx";

const EgressoCard = ({ id, foto, nome, curso, descricao }) => {
    const navigate = useNavigate();
    const { user } = useAuth();

    const handleNavigation = () => {
        window.scrollTo(0, 0);

        if (!user) {
            navigate(`/egressos/perfil/${id}`);
        } else if (user.role === "ROLE_COORDENADOR") {
            navigate(`/coordenador/egressos/perfil/${id}`);
        } else if (user.role === "ROLE_EGRESSO") {
            navigate(`/egresso/egressos/perfil/${id}`);
        } else {
            navigate(`/egressos/perfil/${id}`);
        }
    };

    return (
        <div className="egresso-card">
            <div className="egresso-info">
                <img src={foto} alt={`Foto de ${nome}`} className="egresso-foto" />
                <div className="text-container">
                    <h2 className="egresso-nome">{nome}</h2>
                    <h2 className="egresso-curso">Curso(s): {curso}</h2>
                    <span className="egresso-descricao">{descricao}</span>
                </div>
            </div>
            <div>
                <button className="botaoVerMais" onClick={handleNavigation}>Ver mais
                </button>
                <div className="separator-line"></div>
            </div>
        </div>

    );
};

export default EgressoCard;