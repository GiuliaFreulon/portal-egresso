import './Dashboard.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUserGraduate, faBookOpen, faSuitcase, faUserGroup, faRectangleList} from "@fortawesome/free-solid-svg-icons";
import React from "react";


const CoordenadorDashboard = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Dashboard</h1>
                <div className="dashboard-frame">
                    <div className="dashboard-button-container">
                        <button className="dashboard-button"><FontAwesomeIcon icon={faUserGraduate} className="dashboard-icon"/>Gerenciar Egressos</button>
                    </div>
                    <div className="dashboard-button-container">
                        <button className="dashboard-button"><FontAwesomeIcon icon={faBookOpen} className="dashboard-icon"/>Gerenciar Cursos</button>
                    </div>
                    <div className="dashboard-button-container">
                        <button className="dashboard-button"><FontAwesomeIcon icon={faUserGroup} className="dashboard-icon"/>Gerenciar Grupos de Discussão</button>
                    </div>
                    <div className="dashboard-button-container">
                        <button className="dashboard-button"><FontAwesomeIcon icon={faRectangleList} className="dashboard-icon"/>Homologar Depoimentos</button>
                    </div>
                    <div className="dashboard-button-container">
                        <button className="dashboard-button"><FontAwesomeIcon icon={faSuitcase} className="dashboard-icon"/>Homologar Oportunidades</button>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default CoordenadorDashboard;