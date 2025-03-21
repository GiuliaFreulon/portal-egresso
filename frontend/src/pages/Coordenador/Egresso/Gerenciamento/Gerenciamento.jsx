import React, {useEffect, useState} from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMagnifyingGlass, faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarEgressoCard from "../../../../components/Coordenador/GerenciarEgressoCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";
import {Link} from "react-router-dom";
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Gerenciamento = () => {

    const { user } = useAuth();
    const [egressos, setEgressos] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(10); // Itens por página
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchEgressos = async () => {
            try {
                setLoading(true);
                const response = await api.get(`/api/egresso/listarTodos`);
                const egressosFiltrados = response.data.filter(egresso =>
                    egresso.cursos?.some(curso =>
                        curso.curso?.coordenador?.id_coordenador === user.id
                    )
                );

                setEgressos(egressosFiltrados);
            } catch (error) {
                console.error("Erro ao buscar egressos:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchEgressos();
    }, []);

    // Filtra e divide os dados no cliente
    const filteredEgressos = egressos.filter(egresso =>
        egresso.nome.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
        window.scrollTo(0, 0); //rolar para o topo
    };

    const totalPages = Math.ceil(filteredEgressos.length / itemsPerPage);
    const paginatedEgressos = filteredEgressos.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Gerenciar Egressos</h1>

                <div className="gerenciamento-egresso-container">
                    <div className="gerenciamento-egresso-header">
                        <div className="search-container">
                            <FontAwesomeIcon icon={faMagnifyingGlass} className="search-icon" />
                            <input type="text" placeholder="Pesquisar" className="search-input" value={searchTerm}
                                   onChange={(e) => {
                                       setSearchTerm(e.target.value);
                                       setCurrentPage(1); // Reset para primeira página
                                   }}/>
                        </div>

                        <Link to="/coordenador/egresso/cadastro" className="gerenciamento-egresso-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Cadastrar Egresso</p>
                        </Link>
                    </div>

                    {loading ? (
                        <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                            <div className="skeleton-loader"></div>
                        </div>
                    ) : (
                        <></>
                    )}

                    <div className="cards-coordenador-gerenciamento-egresso">
                        {paginatedEgressos?.map((egresso) => (
                            <GerenciarEgressoCard
                                nome={egresso.nome}
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
                </div>
            </section>
        </div>
    );
};

export default Gerenciamento;