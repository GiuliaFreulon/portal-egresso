import React from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarOportunidadeCard from "../../../../components/Egresso/GerenciarOportunidadeCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";
import {Link} from "react-router-dom";

const Gerenciamento = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Suas Oportunidades</h1>

                <div className="egresso-gerenciamento-oportunidade-container">
                    <div className="egresso-gerenciamento-oportunidade-header">
                        <Link to="/egresso/oportunidade/envio" className="egresso-gerenciamento-oportunidade-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Nova Oportunidade</p>
                        </Link>
                    </div>

                    <GerenciarOportunidadeCard titulo={'Título da Oportunidade'} status={'Enviado'}/>
                    <GerenciarOportunidadeCard titulo={'Título da Oportunidade'} status={'Rejeitado'}/>
                    <GerenciarOportunidadeCard titulo={'Título da Oportunidade'} status={'Aprovado'}/>

                    <div className="pagination-container">
                        <Pagination/>
                    </div>

                </div>
            </section>
        </div>
    );
};

export default Gerenciamento;