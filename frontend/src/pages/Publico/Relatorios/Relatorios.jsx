import React, {useEffect, useState} from 'react';
import './Relatorios.css'
import PieChart from "../../../components/common/charts/PieChart.jsx";
import BarChart from "../../../components/common/charts/BarChart.jsx";
import api from '../../../services/api.jsx';

const Relatorios = () => {

    const [egressosPorNivel, setEegressosPorNivel] = useState([]);
    const [egressosPorCurso, setEegressosPorCurso] = useState([]);
    const [loading1, setLoading1] = useState(true);
    const [loading2, setLoading2] = useState(true);

    useEffect(() => {

        const fetchEgressosPorNivel = async () => {
            try {
                setLoading1(true);
                const response = await api.get(`/api/dashboard/egressosPorNivel`);
                setEegressosPorNivel(response.data);
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            } finally {
                setLoading1(false);
            }
        };

        const fetchEgressosPorCurso = async () => {
            try {
                setLoading2(true);
                const response = await api.get(`/api/dashboard/egressosPorCurso`);
                setEegressosPorCurso(response.data);
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            } finally {
                setLoading2(false);
            }
        };

        fetchEgressosPorNivel();
        fetchEgressosPorCurso();
    }, []);


    return (
        <div className="main__container">
            <section className="relatorios-graficos">
                <h1 className="line-text">Painel de Relatórios</h1>
                <div className="relatorios-grafico">
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
                    <p className="relatorios-graficos-titulo">Egressos por Nível</p>
                </div>
                <div className="relatorios-grafico">
                    {loading2 ? (
                        <div className="chart-skeleton">
                            <div className="skeleton-loader"></div>
                        </div>
                    ) :(
                        <PieChart
                            labels={Object.keys(egressosPorCurso)}
                            dataValues={Object.values(egressosPorCurso)}
                        />
                    )}
                    <p className="relatorios-graficos-titulo">Egressos por Curso</p>
                </div>
                <div className="relatorios-grafico">
                    <BarChart />
                    <p className="relatorios-graficos-titulo">Gráfico</p>
                </div>
            </section>
        </div>
    );
};

export default Relatorios;