import React, {useEffect, useState} from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMagnifyingGlass, faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarCursoCard from "../../../../components/Coordenador/GerenciarCursoCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";
import {Link} from "react-router-dom";
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Gerenciamento = () => {

    const { user } = useAuth();
    const [cursos, setCursos] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(10); // Itens por página
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchCursos = async () => {
            try {
                setLoading(true);
                const response = await api.get(`/api/curso/listarTodos`);
                setCursos(response.data.filter(
                    (curso) => curso.coordenador.id_coordenador === user.id
                ));
            } catch (error) {
                console.error("Erro ao buscar cursos:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchCursos();
    }, []);

    // Filtra e divide os dados no cliente
    const filteredCursos = cursos.filter(curso =>
        curso.nome.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
        window.scrollTo(0, 0); //rolar para o topo
    };

    const totalPages = Math.ceil(filteredCursos.length / itemsPerPage);
    const paginatedCursos = filteredCursos.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    return (
        <div className="main__container">
            <section>
                <h1 className="line-text">Gerenciar Cursos</h1>

                <div className="gerenciamento-curso-container">
                    <div className="gerenciamento-curso-header">
                        <div className="search-container">
                            <FontAwesomeIcon icon={faMagnifyingGlass} className="search-icon" />
                            <input type="text" placeholder="Pesquisar" className="search-input" value={searchTerm}
                                   onChange={(e) => {
                                       setSearchTerm(e.target.value);
                                       setCurrentPage(1); // Reset para primeira página
                                   }}/>
                        </div>

                        <Link to="/coordenador/curso/cadastro" className="gerenciamento-curso-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Cadastrar Curso</p>
                        </Link>
                    </div>

                    {loading ? (
                        <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                            <div className="skeleton-loader"></div>
                        </div>
                    ) : (
                        <></>
                    )}

                    <div className="cards-coordenador-gerenciamento-cursos">
                        {paginatedCursos?.map((cursos) => (
                            <GerenciarCursoCard
                                nome={cursos.nome}
                                nivel={cursos.nivel}
                                id={cursos?.id_curso}
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