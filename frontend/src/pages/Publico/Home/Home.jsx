import React, {useEffect, useState} from 'react';
import './Home.css';
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../../contexts/AuthContext.jsx";
import api from "../../../services/api.jsx";
import PieChart from "../../../components/common/charts/PieChart.jsx";

const Home = () => {
    const navigate = useNavigate();
    const { user } = useAuth();
    const [role, setRole] = useState("");
    const [egressosPorNivel, setEgressosPorNivel] = useState([]);
    const [loading1, setLoading1] = useState(true);

    useEffect(() => {
        const decideRole = async () => {
            if (user) {
                if (user.role === "ROLE_COORDENADOR") {
                    setRole("/coordenador");
                } else if (user.role === "ROLE_EGRESSO") {
                    setRole("/egresso");
                }
            }
        }

        const fetchEgressosPorNivel = async () => {
            try {
                setLoading1(true);
                const response = await api.get(`/api/dashboard/egressosPorNivel`);
                setEgressosPorNivel(response.data);
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            } finally {
                setLoading1(false);
            }
        };

        decideRole();
        fetchEgressosPorNivel();
    }, [])

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
                <button className="botaoVerMais" onClick={() => {
                    navigate(`${role}/egressos`);
                    window.scrollTo(0, 0);
                }}>Ver mais</button>
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
                <button className="botaoVerMais" onClick={() => {
                    navigate(`${role}/depoimentos`);
                    window.scrollTo(0, 0);
                }}>Ver mais</button>
            </section>

            <section className="Relatórios">
                <h1 className="line-text">Relatórios</h1>
                <div>
                    {loading1 ? (
                        <div className="chart-skeleton">
                            <div className="skeleton-loader"></div>
                        </div>
                    ) : (
                        <PieChart
                            labels={Object.keys(egressosPorNivel)}
                            dataValues={Object.values(egressosPorNivel)}
                        />
                    )}
                </div>
                <button className="botaoVerMais" onClick={() => {
                    navigate(`${role}/relatorios`);
                    window.scrollTo(0, 0);
                }}>Ver mais</button>
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
                <button className="botaoVerMais" onClick={() => {
                    navigate(`${role}/oportunidades`);
                    window.scrollTo(0, 0);
                }}>Ver mais</button>
            </section>
        </div>
    );
};

export default Home;