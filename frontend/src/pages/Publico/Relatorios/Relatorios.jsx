import React, {useEffect, useState} from 'react';
import './Relatorios.css'
import PieChart from "../../../components/common/charts/PieChart.jsx";
import BarChart from "../../../components/common/charts/BarChart.jsx";
import api from '../../../services/api.jsx';

const Relatorios = () => {

    const [empregabilidadePorCurso, setEmpregabilidadePorCurso] = useState({});
    const [egressosPorNivel, setEgressosPorNivel] = useState([]);
    const [egressosPorCurso, setEgressosPorCurso] = useState([]);
    const [loading0, setLoading0] = useState(true);
    const [loading1, setLoading1] = useState(true);
    const [loading2, setLoading2] = useState(true);

    useEffect(() => {

        const fetchEmpregabilidadePorCurso = async () => {
            try {
                setLoading0(true);
                const response = await api.get(`/api/dashboard/empregabilidadePorCurso`);
                setEmpregabilidadePorCurso(response.data);
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            } finally {
                setLoading0(false);
            }
        };

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

        const fetchEgressosPorCurso = async () => {
            try {
                setLoading2(true);
                const response = await api.get(`/api/dashboard/egressosPorCurso`);
                setEgressosPorCurso(response.data);
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            } finally {
                setLoading2(false);
            }
        };

        fetchEmpregabilidadePorCurso();
        fetchEgressosPorNivel();
        fetchEgressosPorCurso();
    }, []);


    return (
        <div className="main__container">
            <section className="relatorios-graficos">
                <h1 className="line-text">Painel de Relatórios</h1>
                <div className="relatorios-grafico">
                    <p className="relatorios-graficos-titulo">Situação de Emprego por Curso</p>
                    {loading0 ? (
                        <div className="chart-skeleton">
                            <div className="skeleton-loader"></div>
                        </div>
                    ) :(
                        <BarChart
                            data={empregabilidadePorCurso}
                        />
                    )}
                </div>
                <div className="separator-line"></div>
                <div className="relatorios-grafico">
                    <p className="relatorios-graficos-titulo">Egressos por Nível</p>
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
                <div className="separator-line"></div>
                <div className="relatorios-grafico">
                    <p className="relatorios-graficos-titulo">Egressos por Curso</p>
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
                </div>
            </section>
        </div>
    );
};

export default Relatorios;