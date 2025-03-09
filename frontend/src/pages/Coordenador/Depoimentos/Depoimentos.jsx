import React from 'react';
import './Depoimentos.css'
import DepoimentoCard from "../../../components/Coordenador/DepoimentoCard.jsx";

const Depoimentos = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Homologar Depoimentos</h1>

                <div className="depoimento-container">
                    <DepoimentoCard
                        nome={'Nome Completo do Egresso'}
                    />
                    <DepoimentoCard
                        nome={'Welderson Bruce Le Araujo de Sousa'}
                    />
                    <DepoimentoCard
                        nome={'Giulia de Araujo Freulon'}
                    />

                </div>
            </section>
        </div>
    );
};

export default Depoimentos;