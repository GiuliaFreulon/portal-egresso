import React from 'react';
import './Header.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGraduationCap } from "@fortawesome/free-solid-svg-icons";

const Header = ({usuario}) => {

    let cor = '#691F31';

    if (usuario === 'COORDENADOR') {
        cor = '#22415A';
    }

    return (
        <header className="header" style={{ backgroundColor: cor }}>
            <nav className="header__menu">
                <div className="logo">
                    <a href="#" className="header__logo">
                        <h1 className="header__icon"><FontAwesomeIcon icon={faGraduationCap} /> Portal Egresso </h1>
                    </a>
                </div>
                <div className="header__links">
                    <a href="#" className="header__link">Egressos</a>
                    <a href="#" className="header__link">Depoimentos</a>
                    <a href="#" className="header__link">Relatórios</a>
                    <a href="#" className="header__link">Oportunidades</a>
                </div>
                <button className="header__login" onClick={() => window.location.href = "/login"}>Fazer login</button>
            </nav>
        </header>
    );
};

export default Header;