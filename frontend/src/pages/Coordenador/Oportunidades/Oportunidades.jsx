import React from 'react';
import './Oportunidades.css'
import OportunidadeCard from "../../../components/Coordenador/OportunidadeCard.jsx";

const Oportunidades = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Homologar Oportunidades</h1>

                <div className="oportunidade-container">
                    <OportunidadeCard
                        nome={'Titulo da Oportunidade'}
                    />
                    <OportunidadeCard
                        nome={'Titulo da Oportunidade'}
                    />
                    <OportunidadeCard
                        nome={'Titulo da Oportunidade'}
                    />

                </div>
            </section>
        </div>
    );
};

export default Oportunidades;