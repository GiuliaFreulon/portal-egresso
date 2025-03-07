import React from 'react';
import './Depoimentos.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFilter} from "@fortawesome/free-solid-svg-icons";
import DepoimentoCard from "../../../components/Egresso/DepoimentoCard.jsx";

const Depoimentos = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Painel de Depoimentos</h1>

                <div className="depoimentos-filter-container">
                    <button className="depoimentos-filter-btn"><FontAwesomeIcon icon={faFilter} />Filtros</button>
                </div>

                <div className="depoimentos-cards-egresso">
                    <DepoimentoCard
                        foto="https://pbs.twimg.com/ext_tw_video_thumb/1451000252190892033/pu/img/omuzIHGdbNc90sQw.jpg"
                        nome="Hisalana Silva"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed "
                        data="26/02/2025"
                    />
                    <DepoimentoCard
                        foto="foto"
                        nome="Nome do Aluno"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed."
                        data="26/02/2025"
                    />
                    <DepoimentoCard
                        foto="foto"
                        nome="Nome do Aluno"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed."
                        data="26/02/2025"
                    />
                    <DepoimentoCard
                        foto="foto"
                        nome="Nome do Aluno"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed."
                        data="26/02/2025"
                    />
                </div>
            </section>
        </div>
    );
};

export default Depoimentos;