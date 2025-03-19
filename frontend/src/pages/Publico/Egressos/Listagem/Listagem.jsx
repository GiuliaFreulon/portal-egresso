import React, {useEffect, useState} from 'react';
import './Listagem.css';

import EgressoCard from "../../../../components/Egresso/EgressoCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";

import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faFilter, faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import api from '../../../../services/api.jsx';

const Listagem = () => {

    const [egressos, setEgressos] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(10); // Itens por página
    const [searchTerm, setSearchTerm] = useState('');


    useEffect(() => {
        const fetchEgressos = async () => {
            try {
                const response = await api.get(`/api/egresso/listarTodos`);
                setEgressos(response.data);
                console.log(response.data);
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            }
        };

        fetchEgressos();
    }, []);

    // Filtra e divide os dados no cliente
    const filteredEgressos = egressos.filter(egresso =>
        egresso.nome.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const totalPages = Math.ceil(filteredEgressos.length / itemsPerPage);
    const paginatedEgressos = filteredEgressos.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
        window.scrollTo(0, 0); //rolar para o topo
    };

    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Painel de Egressos</h1>

                <div className="search-container">
                    <FontAwesomeIcon icon={faMagnifyingGlass} className="search-icon" />
                    <input
                        type="text"
                        placeholder="Pesquisar"
                        className="search-input"
                        value={searchTerm}
                        onChange={(e) => {
                            setSearchTerm(e.target.value);
                            setCurrentPage(1); // Reset para primeira página
                        }}
                    />
                    <button className="filter-btn"><FontAwesomeIcon icon={faFilter} /></button>
                </div>

                <div className="cards-egresso">
                    {paginatedEgressos?.map((egresso) => (
                        <EgressoCard
                            id={egresso.id_egresso}
                            foto={egresso.foto}
                            nome={egresso.nome}
                            //curso={egresso.cursos[0]?.curso.nome}
                            curso={egresso.cursos?.map((curso) => curso.curso.nome).join(", ")}
                            descricao={egresso.descricao}
                        />
                    ))}
                </div>

                <div className="pagination-container">
                    <Pagination
                        currentPage={currentPage}
                        totalPages={totalPages}
                        onPageChange={handlePageChange}
                    />
                </div>

            </section>
        </div>
    );
};

export default Listagem;