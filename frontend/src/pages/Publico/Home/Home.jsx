import React from 'react';
import './Home.css';
import BarChart from "../../../components/common/charts/BarChart.jsx";
import Header from "../../../components/common/Header/Header.jsx";

const Home = () => {
    return (
        <div className="main__container">
            <section className="Egressos">
                <h1 className="line-text">Egressos</h1>
                <div className="egressos__list">
                    <a>
                        <img alt="perfil egresso" className="egresso__link"/>
                        Giulia de Araujo Freulon Vecanandre Sthapani DE hahaha bala de icekiss
                    </a>
                    <a>
                        <img alt="perfil egresso" className="egresso__link"/>
                        Welderson Bruce Le Araujo de Sousa
                    </a>
                    <a>
                        <img alt="perfil egresso" className="egresso__link"/>
                        Nome do Egresso
                    </a>
                    <a>
                        <img alt="perfil egresso" className="egresso__link"/>
                        Nome do Egresso
                    </a>
                    <a>
                        <img alt="perfil egresso" className="egresso__link"/>
                        Nome do Egresso
                    </a>
                    <a>
                        <img alt="perfil egresso" className="egresso__link"/>
                        Nome do Egresso
                    </a>
                    <a>
                        <img alt="perfil egresso" className="egresso__link"/>
                        Nome do Egresso
                    </a>
                    <a>
                        <img alt="perfil egresso" className="egresso__link"/>
                        Nome do Egresso
                    </a>
                </div>
                <button className="botaoVerMais" onClick={() => window.location.href = "/login"}>Ver mais</button>
            </section>

            <section className="Depoimentos">
                <h1 className="line-text">Depoimentos</h1>
                <div className="depoimentos__list">
                    <div className="depoimento__container">
                        <img alt="foto egresso" className="egresso__foto"/>
                        <div className="depoimento__text__container">
                            <span className="depoimento__text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed </span>
                        </div>
                    </div>
                    <div className="depoimento__container">
                        <img alt="foto egresso" className="egresso__foto"/>
                        <div className="depoimento__text__container">
                            <span className="depoimento__text"></span>
                        </div>
                    </div>
                    <div className="depoimento__container">
                        <img alt="foto egresso" className="egresso__foto"/>
                        <div className="depoimento__text__container">
                            <span className="depoimento__text"></span>
                        </div>
                    </div>
                </div>
                <button className="botaoVerMais" onClick={() => window.location.href = "/login"}>Ver mais</button>
            </section>

            <section className="Relatórios">
                <h1 className="line-text">Relatórios</h1>
                <div>
                    <BarChart />
                </div>
                <button className="botaoVerMais" onClick={() => window.location.href = "/login"}>Ver mais</button>
            </section>

            <section className="Oportunidades">
                <h1 className="line-text">Oportunidades</h1>
                <div className="oportunidades__list">
                    <div className="oportunidade__container">
                        <p className="oportunidade__title">Titulo</p>
                        <div className="oportunidade__text_container">
                            <span className="oportunidade__text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris</span>
                        </div>
                    </div>
                    <div className="oportunidade__container">
                        <p className="oportunidade__title">Titulo</p>
                        <div className="oportunidade__text_container">
                            <span className="oportunidade__text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris</span>
                        </div>
                    </div>
                </div>
                <button className="botaoVerMais" onClick={() => window.location.href = "#"}>Ver mais</button>
            </section>
        </div>
    );
};

export default Home;