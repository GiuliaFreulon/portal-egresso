import React from 'react';
import './Listagem.css';

import EgressoCard from "../../../../components/Egresso/EgressoCard.jsx";

import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faFilter, faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

const Listagem = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Painel de Egressos</h1>

                <div className="search-container">
                    <FontAwesomeIcon icon={faMagnifyingGlass} className="search-icon" />
                    <input type="text" placeholder="Pesquisar..." className="search-input" />
                    <button className="filter-btn"><FontAwesomeIcon icon={faFilter} /></button>
                </div>

                <div className="cards-egresso">
                    <EgressoCard
                        foto="https://pbs.twimg.com/ext_tw_video_thumb/1451000252190892033/pu/img/omuzIHGdbNc90sQw.jpg"
                        nome="Hisalana Silva"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed "
                    />
                    <EgressoCard
                        foto="foto"
                        nome="Nome do Aluno"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed."
                    />
                    <EgressoCard
                        foto="foto"
                        nome="Nome do Aluno"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed."
                    />
                    <EgressoCard
                        foto="foto"
                        nome="Nome do Aluno"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed."
                    />
                </div>
            </section>
        </div>
    );
};

export default Listagem;