import React from 'react';
import './Login.css'

const Login = () => {
    return (
        <div className="main__container">
            <section className="oportunidades">
                <h1 className="line-text">Painel de Oportunidades</h1>
                <div className="lista-oportunidades">
                    <div className="oportunidade-container">
                        <p className="oportunidade-titulo">Titulo da Vaga</p>
                        <p className="oportunidade-autor">Publicado por: Nego ney</p>
                        <span className="oportunidade-descricao">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed.</span>
                    </div>

                    <div className="oportunidades-card-separator">
                        <div className="separator-line"></div>
                    </div>

                    <div className="oportunidade-container">
                        <p className="oportunidade-titulo">Titulo da Vaga</p>
                        <p className="oportunidade-autor">Publicado por: Nego ney</p>
                        <span className="oportunidade-descricao">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed.</span>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default Oportunidades;