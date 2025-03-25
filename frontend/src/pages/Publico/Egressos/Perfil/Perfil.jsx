import React, {useEffect, useState} from 'react';
import './Perfil.css';

import {FaGithub, FaLinkedin} from "react-icons/fa";
import {MdDownload} from "react-icons/md";
import {useParams} from "react-router-dom";
import api from "../../../../services/api.jsx";
import CargoCard from "../../../../components/Egresso/CargoCard.jsx";

const Perfil = () => {
    const { id } = useParams()
    const [egresso, setEgresso] = useState(null);

    useEffect(() => {
        const fetchEgresso = async () => {
            try {
                const response = await api.get(`/api/egresso/buscarPorId/${id}`);
                setEgresso(response.data);
                console.log(response.data);
            } catch (error) {
                console.log("Erro ao buscar egresso:", error);
            }
        }

        fetchEgresso();
    },[id])

    const handleDownloadCurriculo = () => {
        if (!egresso?.curriculo) {
            alert("Currículo não disponível");
            return;
        }

        // Decodifica o Base64
        const byteCharacters = atob(egresso.curriculo);
        const byteNumbers = new Array(byteCharacters.length);

        for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
        }

        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: 'application/pdf' });

        // Cria link temporário
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `${egresso.nome.replace(/\s/g, '_')}_Curriculo.pdf`);
        document.body.appendChild(link);
        link.click();

        // Limpeza
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    };


    return (
        <div className="main__container">
            <section className="egresso-perfil">
                <div className="egresso-infos">
                    <img alt={`foto do egresso: ${egresso?.nome}`} className="egresso-foto" src={`data:image/jpeg;base64, ${egresso?.foto}`} />
                    <h1 className="egresso-nome">{egresso?.nome}</h1>
                    <div className="egresso-descricao-perfil">
                        <div className="egresso-cursos">
                            {egresso?.cursos?.map((curso) => (
                                <h2 className="egresso-curso">
                                    {curso.curso.nome} - {curso.curso.nivel} ({curso.anoInicio} - {curso.anoFim})
                                </h2>
                            ))}
                        </div>
                        <span className="egresso-biografia">{egresso?.descricao}</span>
                    </div>

                    <button className="button-with-icon" onClick={handleDownloadCurriculo}>Currículo <MdDownload /></button>
                    <div className='links'>
                        <a href={egresso?.linkedin} className='link'><FaLinkedin></FaLinkedin></a>
                        <a href={egresso?.github} className='link'><FaGithub></FaGithub></a>
                    </div>
                </div>
                <div className="egresso-cargos">
                    <h2 className="line-text cargos-titulo">Histórico de Cargos</h2>
                        {egresso?.cargos?.map((cargo) => (
                            <CargoCard
                                local={cargo.local}
                                anoInicio={cargo.anoInicio}
                                anoFim={cargo.anoFim}
                                descricao={cargo.descricao}
                            />
                        ))}
                </div>
            </section>
        </div>
    );
};

export default Perfil;