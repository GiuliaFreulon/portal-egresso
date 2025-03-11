import React from 'react';
import './Header.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGraduationCap } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import { useAuth } from "../../../contexts/AuthContext.jsx"; // Importa o contexto

const Header = ({usuario}) => {

    // const { user, logout } = useAuth(); // Pega o usuário logado

    let cor = '#691F31';

    if (usuario === 'COORDENADOR') {
        cor = '#22415A';
    }

    return (
        <header className="header" style={{ backgroundColor: cor }}>
            <nav className="header__menu">
                <div className="logo">
                    <Link to="/" className="header__logo">
                        <h1 className="header__icon"><FontAwesomeIcon icon={faGraduationCap} /> Portal Egresso </h1>
                    </Link>
                </div>
                <div className="header__links">
                    <Link to="/egressos" className="header__link">Egressos</Link>
                    <Link to="/depoimentos" className="header__link">Depoimentos</Link>
                    <Link to="/relatorios" className="header__link">Relatórios</Link>
                    <Link to="/oportunidades" className="header__link">Oportunidades</Link>
                </div>
                <button className="header__login" onClick={() => window.location.href = "/egresso_login"}>Fazer login</button>
            </nav>
        </header>
    );
};

export default Header;