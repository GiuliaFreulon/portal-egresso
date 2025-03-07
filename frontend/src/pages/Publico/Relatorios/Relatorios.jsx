import React from 'react';
import './Relatorios.css'
import PieChart from "../../../components/common/charts/PieChart.jsx";
import BarChart from "../../../components/common/charts/BarChart.jsx";

const Relatorios = () => {
    return (
        <div className="main__container">
            <section className="relatorios-graficos">
                <h1 className="line-text">Painel de Relatórios</h1>
                <div className="relatorios-grafico">
                    <PieChart />
                    <p className="relatorios-graficos-titulo">Gráfico</p>
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