import React, {useEffect, useState} from 'react';
import './Home.css';
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../../contexts/AuthContext.jsx";
import api from "../../../services/api.jsx";
import PieChart from "../../../components/common/charts/PieChart.jsx";
import DepoimentoCard from "../../../components/Egresso/DepoimentoCard.jsx";
import OportunidadeCard from "../../../components/Egresso/OportunidadeCard.jsx";

const Home = () => {
    const navigate = useNavigate();
    const { user } = useAuth();
    const [role, setRole] = useState("");
    const [egressos, setEgressos] = useState([]);
    const [depoimentos, setDepoimentos] = useState([]);
    const [egressosPorNivel, setEgressosPorNivel] = useState([]);
    const [oportunidades, setOportunidades] = useState([]);
    const [loadingEgressos, setLoadingEgressos] = useState(false);
    const [loadingDepoimentos, setLoadingDepoimentos] = useState(true);
    const [loadingOportunidades, setLoadingOportunidades] = useState(true);
    const [loadingRelatorio, setLoadingRelatorio] = useState(true);

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

        const fetchEgressos = async () => {
            try {
                setLoadingEgressos(true);
                const response = await api.get(`/api/egresso/listarNomeIdTodos`);
                setEgressos(response.data.slice(0, 8));
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            } finally {
                setLoadingEgressos(false);
            }
        };

        const fetchDepoimentos = async () => {
            try {
                setLoadingDepoimentos(true);
                const response = await api.get(`/api/depoimento/listarTodos`);
                setDepoimentos(response.data.filter((depoimento) => depoimento.status === "APROVADO").slice(0,2)); // Dados da página atual
            } catch (error) {
                console.error("Erro ao buscar depoimentos:", error);
            } finally {
                setLoadingDepoimentos(false);
            }

        };

        const fetchEgressosPorNivel = async () => {
            try {
                setLoadingRelatorio(true);
                const response = await api.get(`/api/dashboard/egressosPorNivel`);
                setEgressosPorNivel(response.data);
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            } finally {
                setLoadingRelatorio(false);
            }
        };

        const fetchOportunidades = async () => {
            try {
                setLoadingOportunidades(true);
                const response = await api.get(`/api/oportunidade/listarTodos`);
                setOportunidades(response.data.filter((oportunidade) => oportunidade.status === "APROVADO").slice(0, 2)); // Dados da página atual
            } catch (error) {
                console.error("Erro ao buscar oportunidades:", error);
            } finally {
                setLoadingOportunidades(false);
            }

        };

        decideRole();
        fetchEgressos();
        fetchDepoimentos()
        fetchEgressosPorNivel();
        fetchOportunidades();
    }, [])

    return (
        <div className="main__container">
            <section className="Egressos">
                <h1 className="line-text">Egressos</h1>
                {loadingEgressos ? (
                    <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                        <div className="skeleton-loader"></div>
                    </div>
                ) : (
                    <div className="egressos__list">
                        <a>
                            <img src={`data:image/jpeg;base64, ${egressos[0]?.foto}`} alt={`perfil do ${egressos[0]?.nome}`} className="egresso__link"/>
                            {egressos[0]?.nome}
                        </a>
                        <a>
                            <img src={`data:image/jpeg;base64, ${egressos[1]?.foto}`} alt={`perfil do ${egressos[1]?.nome}`} className="egresso__link"/>
                            {egressos[1]?.nome}
                        </a>
                        <a>
                            <img src={`data:image/jpeg;base64, ${egressos[2]?.foto}`} alt={`perfil do ${egressos[2]?.nome}`} className="egresso__link"/>
                            {egressos[2]?.nome}
                        </a>
                        <a>
                            <img src={`data:image/jpeg;base64, ${egressos[3]?.foto}`} alt={`perfil do ${egressos[3]?.nome}`} className="egresso__link"/>
                            {egressos[3]?.nome}
                        </a>
                        <a>
                            <img src={`data:image/jpeg;base64, ${egressos[4]?.foto}`} alt={`perfil do ${egressos[4]?.nome}`} className="egresso__link"/>
                            {egressos[4]?.nome}
                        </a>
                        <a>
                            <img src={`data:image/jpeg;base64, ${egressos[5]?.foto}`} alt={`perfil do ${egressos[5]?.nome}`} className="egresso__link"/>
                            {egressos[5]?.nome}
                        </a>
                        <a>
                            <img src={`data:image/jpeg;base64, ${egressos[6]?.foto}`} alt={`perfil do ${egressos[6]?.nome}`} className="egresso__link"/>
                            {egressos[6]?.nome}
                        </a>
                        <a>
                            <img src={`data:image/jpeg;base64, ${egressos[7]?.foto}`} alt={`perfil do ${egressos[7]?.nome}`} className="egresso__link"/>
                            {egressos[7]?.nome}
                        </a>
                    </div>
                )}
                <button className="botaoVerMais" onClick={() => {
                    navigate(`${role}/egressos`);
                    window.scrollTo(0, 0);
                }}>Ver mais</button>
            </section>

            <section className="Depoimentos">
                <h1 className="line-text">Depoimentos</h1>
                {loadingDepoimentos ? (
                    <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                        <div className="skeleton-loader"></div>
                    </div>
                ) : (
                    <></>
                )}
                <div className="depoimentos__list">

                    {depoimentos
                        .map((depoimento) => (
                            <DepoimentoCard
                                id={depoimento.id}
                                foto={depoimento.egresso?.foto}
                                nome={depoimento.egresso.nome}
                                curso={depoimento.egresso.cursos?.map((curso) => curso.curso.nome).join(", ")}
                                descricao={depoimento.texto}
                                data={depoimento.data}
                            />
                        ))
                    }
                </div>
                <button className="botaoVerMais" onClick={() => {
                    navigate(`${role}/depoimentos`);
                    window.scrollTo(0, 0);
                }}>Ver mais</button>
            </section>

            <section className="Relatórios">
                <h1 className="line-text">Relatórios</h1>
                <div>
                    {loadingRelatorio ? (
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
                {loadingOportunidades ? (
                    <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                        <div className="skeleton-loader"></div>
                    </div>
                ) : (
                    <></>
                )}
                <div className="oportunidades__list">
                    {oportunidades
                        .map((oportunidade) => (
                            <OportunidadeCard
                                titulo={oportunidade.titulo}
                                autor={oportunidade.egresso.nome}
                                descricao={oportunidade.descricao}
                            />
                        ))}
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