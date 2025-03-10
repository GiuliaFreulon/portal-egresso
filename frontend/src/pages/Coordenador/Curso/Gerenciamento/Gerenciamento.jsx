import React from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMagnifyingGlass, faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarCursoCard from "../../../../components/Coordenador/GerenciarCursoCard.jsx";

const Gerenciamento = () => {
    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Gerenciar Cursos</h1>

                <div className="gerenciamento-curso-container">
                    <div className="gerenciamento-curso-header">
                        <div className="search-container">
                            <FontAwesomeIcon icon={faMagnifyingGlass} className="search-icon" />
                            <input type="text" placeholder="Pesquisar" className="search-input" />
                        </div>

                        <a href="#" className="gerenciamento-curso-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Cadastrar Curso</p>
                        </a>
                    </div>

                    <GerenciarCursoCard
                        nome={'Desenvolvimento Web'}
                        nivel={'Graduação'}
                    />
                    <GerenciarCursoCard
                        nome={'Ciência de Dados'}
                        nivel={'Pós-Graduação'}
                    />
                    <GerenciarCursoCard
                        nome={'Design UX/UI'}
                        nivel={'Graduação'}
                    />

                </div>
            </section>
        </div>
    );
};

export default Gerenciamento;