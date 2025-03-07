import React from 'react';
import './Perfil.css';

import { FaLinkedin } from "react-icons/fa";
import { FaGithub } from "react-icons/fa";
import { MdDownload } from "react-icons/md";

const Perfil = () => {
    return (
        <div className="main__container">
            <section className="egresso-perfil">
                <div className="egresso-infos">
                    <img alt='foto' className="egresso-foto"/>
                    <h1 className="egresso-nome">Nome e Sobrenome</h1>
                    <div className="egresso-descricao-perfil">
                        <div className="egresso-cursos">
                            <h2 className="egresso-curso">Ciência da Computação - Graduação (2014 - 2018)</h2>
                            <h2 className="egresso-curso">Ciência da Computação - Mestrado (2019 - 2021)</h2>
                        </div>
                        <span className="egresso-biografia">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris.</span>
                    </div>

                    <button className="button-with-icon">Currículo <MdDownload /></button>
                    <div className='links'>
                        <a className='link'><FaLinkedin></FaLinkedin></a>
                        <a className='link'><FaGithub></FaGithub></a>
                    </div>
                </div>
                <div className="egresso-cargos">
                    <h2 className="line-text cargos-titulo">Histórico de Cargos</h2>
                    <div className="cargo__container">
                        <p className="cargo__title">Titulo</p>
                        <p className="cargo__data">2021-2023</p>
                        <span className="cargo__descricao">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed.</span>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default Perfil;