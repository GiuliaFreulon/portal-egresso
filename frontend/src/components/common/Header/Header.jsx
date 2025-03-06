import React from 'react';

const Header = () => {
    return (
        <header className="header">
            <img src={"../../assets/images/headerLogo.png"} alt="ícone da logo" className="header__icon" />
            <nav className="header__menu">
                <a href="#" className="header__menu__link">Sobre Mim</a>
                <a href="#skills" className="header__menu__link">Habilidades</a>
                <a href="#projects" className="header__menu__link">Projetos</a>
                <a href="#" className="header__menu__link">Contato</a>
            </nav>
        </header>
    );
};

export default Header;