import React from 'react';
import './Header.css';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGear, faGraduationCap, faHouse, faRightFromBracket, faUserGroup} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";
import {useAuth} from "../../../contexts/AuthContext.jsx";

const Header = () => {
    const {logout} = useAuth();

    const handleLogout = () => {
        logout();
    };


    return (
        <header className="egresso-header">
            <nav className="egresso-header__menu">
                <div className="logo">
                    <Link to="/egresso/home" className="egresso-header__logo">
                        <h1 className="egresso-header__icon"><FontAwesomeIcon icon={faGraduationCap} /> Portal Egresso </h1>
                    </Link>
                </div>
                <div className="egresso-header__links">
                    <Link to="/egresso/egressos" className="egresso-header__link">Egressos</Link>
                    <Link to="/egresso/depoimentos" className="egresso-header__link">Depoimentos</Link>
                    <Link to="/egresso/relatorios" className="egresso-header__link">Relatórios</Link>
                    <Link to="/egresso/oportunidades" className="egresso-header__link">Oportunidades</Link>
                </div>
                <div className="egresso-header__menu--buttons">
                    <Link to="/egresso/dashboard" className="egresso-header-icon"><FontAwesomeIcon icon={faHouse} /></Link>
                    <Link to="/egresso/discussao/listagem" className="egresso-header-icon"><FontAwesomeIcon icon={faUserGroup} /></Link>
                    <Link to="/egresso/edicao" className="egresso-header-icon"><FontAwesomeIcon icon={faGear} /></Link>
                    <button onClick={() => handleLogout() } className="egresso-header-icon"><FontAwesomeIcon icon={faRightFromBracket} /></button>
                </div>
            </nav>
        </header>
    );
};

export default Header;