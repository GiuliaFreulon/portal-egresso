import React from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarDepoimentoCard from "../../../../components/Egresso/GerenciarDepoimentoCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";
import {Link} from "react-router-dom";

const Gerenciamento = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Seus Depoimentos</h1>

                <div className="egresso-gerenciamento-depoimento-container">
                    <div className="egresso-gerenciamento-depoimento-header">
                        <Link to="/egresso/depoimento/envio" className="egresso-gerenciamento-depoimento-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Novo Depoimento</p>
                        </Link>
                    </div>

                    <GerenciarDepoimentoCard texto={'Texto do Depoimento'} status={'Enviado'}/>
                    <GerenciarDepoimentoCard texto={'Texto do Depoimento'} status={'Rejeitado'}/>
                    <GerenciarDepoimentoCard texto={'Texto do Depoimento'} status={'Aprovado'}/>

                    <div className="pagination-container">
                        <Pagination/>
                    </div>

                </div>
            </section>
        </div>
    );
};

export default Gerenciamento;