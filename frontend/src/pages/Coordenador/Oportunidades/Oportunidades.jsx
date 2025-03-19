import React from 'react';
import './Oportunidades.css'
import OportunidadeCard from "../../../components/Coordenador/GerenciarOportunidadeCard.jsx";
import Pagination from "../../../components/common/Pagination/Pagination.jsx";

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

                    <div className="pagination-container">
                        <Pagination/>
                    </div>

                </div>
            </section>
        </div>
    );
};

export default Oportunidades;