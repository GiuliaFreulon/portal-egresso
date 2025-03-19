import React from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMagnifyingGlass, faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarEgressoCard from "../../../../components/Coordenador/GerenciarEgressoCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";
import {Link} from "react-router-dom";

const Gerenciamento = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Gerenciar Egressos</h1>

                <div className="gerenciamento-egresso-container">
                    <div className="gerenciamento-egresso-header">
                        <div className="search-container">
                            <FontAwesomeIcon icon={faMagnifyingGlass} className="search-icon" />
                            <input type="text" placeholder="Pesquisar" className="search-input" />
                        </div>

                        <Link to="/coordenador/egresso/cadastro" className="gerenciamento-egresso-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Cadastrar Egresso</p>
                        </Link>
                    </div>

                    <GerenciarEgressoCard nome={'Welderson Bruce Le Araujo de Sousa'}/>
                    <GerenciarEgressoCard nome={'Giulia de Araujo Freulon'}/>
                    <GerenciarEgressoCard nome={'Nome Completo do Egresso'}/>

                    <div className="pagination-container">
                        <Pagination/>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default Gerenciamento;