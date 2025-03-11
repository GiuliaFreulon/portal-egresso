import React from 'react';
import './LoginHeader.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGraduationCap} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";

const Header = ({usuario}) => {

    // const { user, logout } = useAuth(); // Pega o usuário logado

    let cor = '#691F31';

    if (usuario === 'COORDENADOR') {
        cor = '#22415A';
    }

    return (
        <header className="login-header" style={{ backgroundColor: cor }}>
            <nav className="login-header__menu">
                <div className="logo">
                    <Link to="/" className="login-header__logo">
                        <h1 className="login-header__icon"><FontAwesomeIcon icon={faGraduationCap} /> Portal Egresso </h1>
                    </Link>
                </div>
                <div className="login-header__links">
                    <Link to="/egressos" className="login-header__link">Egressos</Link>
                    <Link to="/depoimentos" className="login-header__link">Depoimentos</Link>
                    <Link to="/relatorios" className="login-header__link">Relatórios</Link>
                    <Link to="/oportunidades" className="login-header__link">Oportunidades</Link>
                </div>
                <div></div>
            </nav>
        </header>
    );
};

export default Header;