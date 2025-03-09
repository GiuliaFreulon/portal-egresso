import './Dashboard.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSuitcase, faUserGroup, faRectangleList} from "@fortawesome/free-solid-svg-icons";
import React from "react";


const EgressoDashboard = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Dashboard</h1>
                <div className="dashboard-frame">
                    <div className="dashboard-button-container">
                        <button className="dashboard-button"><FontAwesomeIcon icon={faUserGroup} className="dashboard-icon"/>Gerenciar Grupos de Discussão</button>
                    </div>
                    <div className="dashboard-button-container">
                        <button className="dashboard-button"><FontAwesomeIcon icon={faRectangleList} className="dashboard-icon"/>Gerenciar Depoimentos</button>
                    </div>
                    <div className="dashboard-button-container">
                        <button className="dashboard-button"><FontAwesomeIcon icon={faSuitcase} className="dashboard-icon"/>Gerenciar Oportunidades</button>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default EgressoDashboard;