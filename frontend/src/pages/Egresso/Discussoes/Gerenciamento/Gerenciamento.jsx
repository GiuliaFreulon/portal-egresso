import React from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarDiscussaoCard from "../../../../components/Egresso/GerenciarDiscussaoCard.jsx";
import {Link} from "react-router-dom";

const Gerenciamento = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Seus Grupos de Discussão</h1>

                <div className="egresso-gerenciamento-discussao-container">
                    <div className="egresso-gerenciamento-discussao-header">
                        <Link to="/egresso/discussao/criacao" className="egresso-gerenciamento-discussao-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Novo Grupo</p>
                        </Link>
                    </div>

                    <GerenciarDiscussaoCard titulo={'Título do Grupo'}/>
                    <GerenciarDiscussaoCard titulo={'Título do Grupo'}/>
                    <GerenciarDiscussaoCard titulo={'Título do Grupo'}/>

                </div>
            </section>
        </div>
    );
};

export default Gerenciamento;