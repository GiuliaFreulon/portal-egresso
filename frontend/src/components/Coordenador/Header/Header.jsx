import React from 'react';
import './Header.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGear, faGraduationCap, faHouse, faRightFromBracket} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";
import {useAuth} from "../../../contexts/AuthContext.jsx";

const Header = () => {
    const {logout} = useAuth();

    const handleLogout = () => {
        logout();
    };


    return (
        <header className="coordenador-header">
            <nav className="coordenador-header__menu">
                <div className="logo">
                    <Link to="/" className="coordenador-header__logo">
                        <h1 className="coordenador-header__icon"><FontAwesomeIcon icon={faGraduationCap} /> Portal Egresso </h1>
                    </Link>
                </div>
                <div className="coordenador-header__links">
                    <Link to="/coordenador/egressos" className="coordenador-header__link">Egressos</Link>
                    <Link to="/coordenador/depoimentos" className="coordenador-header__link">Depoimentos</Link>
                    <Link to="/coordenador/relatorios" className="coordenador-header__link">Relatórios</Link>
                    <Link to="/coordenador/oportunidades" className="coordenador-header__link">Oportunidades</Link>
                </div>
                <div className="coordenador-header__menu--buttons">
                    <Link to="/coordenador/dashboard" className="coordenador-header-icon"><FontAwesomeIcon icon={faHouse} /></Link>
                    <Link to="/coordenador/edicao" className="coordenador-header-icon"><FontAwesomeIcon icon={faGear} /></Link>
                    <button onClick={() => handleLogout() } className="coordenador-header-icon"><FontAwesomeIcon icon={faRightFromBracket} /></button>
                </div>
            </nav>
        </header>
    );
};

export default Header;