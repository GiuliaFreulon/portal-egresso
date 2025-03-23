import React, {useEffect, useState} from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarDiscussaoCard from "../../../../components/Egresso/GerenciarDiscussaoCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";
import {Link} from "react-router-dom";
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Gerenciamento = () => {

    const { user } = useAuth();
    const [discussoes, setDiscussoes] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5); // Itens por página
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchDiscussoes = async () => {
            try {
                setLoading(true);
                const response = await api.get(`/api/discussao/listarTodos`);
                setDiscussoes(response.data.filter(
                    (discussao) => discussao.egresso.id_egresso === user.id
                )); // Dados da página atual
            } catch (error) {
                console.error("Erro ao buscar discussoes:", error);
            } finally {
                setLoading(false);
            }

        };

        fetchDiscussoes();
    }, []);

    const totalPages = Math.ceil(discussoes.length / itemsPerPage);
    const paginatedDiscussoes = discussoes.slice(
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
                <h1 className="line-text">Seus Grupos de Discussão</h1>

                <div className="egresso-gerenciamento-discussao-container">
                    <div className="egresso-gerenciamento-discussao-header">
                        <Link to="/egresso/discussao/criacao" className="egresso-gerenciamento-discussao-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Novo Grupo</p>
                        </Link>
                    </div>

                    {loading ? (
                        <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                            <div className="skeleton-loader"></div>
                        </div>
                    ) : (
                        <></>
                    )}

                    <div className="cards-discussoes">
                        {paginatedDiscussoes?.map((discussao) => (
                                <GerenciarDiscussaoCard
                                    titulo={discussao.titulo}
                                    id={discussao?.id_discussao}
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