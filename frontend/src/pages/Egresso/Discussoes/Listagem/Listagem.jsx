import React from 'react';
import './Listagem.css'
import ListarDiscussaoCard from "../../../../components/Egresso/ListarDiscussaoCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";

const Listagem = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Seus Grupos de Discussão</h1>

                <div className="egresso-listagem-discussao-container">
                    <ListarDiscussaoCard titulo={'Título do Grupo'}/>
                    <ListarDiscussaoCard titulo={'Título do Grupo'}/>
                    <ListarDiscussaoCard titulo={'Título do Grupo'}/>

                    <div className="pagination-container">
                        <Pagination/>
                    </div>
                </div>

            </section>
        </div>
    );
};

export default Listagem;