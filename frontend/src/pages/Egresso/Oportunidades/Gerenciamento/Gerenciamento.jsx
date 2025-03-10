import React from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarOportunidadeCard from "../../../../components/Egresso/GerenciarOportunidadeCard.jsx";

const Gerenciamento = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Suas Oportunidades</h1>

                <div className="egresso-gerenciamento-oportunidade-container">
                    <div className="egresso-gerenciamento-oportunidade-header">
                        <a href="#" className="egresso-gerenciamento-oportunidade-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Nova Oportunidade</p>
                        </a>
                    </div>

                    <GerenciarOportunidadeCard titulo={'Título da Oportunidade'} status={'Enviado'}/>
                    <GerenciarOportunidadeCard titulo={'Título da Oportunidade'} status={'Rejeitado'}/>
                    <GerenciarOportunidadeCard titulo={'Título da Oportunidade'} status={'Aprovado'}/>

                </div>
            </section>
        </div>
    );
};

export default Gerenciamento;