import React from 'react';
import './Discussoes.css'
import DiscussaoCard from "../../../components/Coordenador/DiscussaoCard.jsx";
import Pagination from "../../../components/common/Pagination/Pagination.jsx";

const Discussoes = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Gerenciar Grupos de Discussão</h1>

                <div className="discussao-container">
                    <DiscussaoCard
                        titulo={'Desenvolvimento Web'}
                    />
                    <DiscussaoCard
                        titulo={'Ciência de Dados'}
                    />
                    <DiscussaoCard
                        titulo={'Design UX/UI'}
                    />

                    <div className="pagination-container">
                        <Pagination/>
                    </div>

                </div>
            </section>
        </div>
    );
};

export default Discussoes;