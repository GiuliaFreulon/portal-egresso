import React, {useEffect, useState} from 'react';
import './Depoimentos.css'
import DepoimentoCard from "../../../components/Coordenador/DepoimentoCard.jsx";
import Pagination from "../../../components/common/Pagination/Pagination.jsx";
import api from "../../../services/api.jsx";
import {useAuth} from "../../../contexts/AuthContext.jsx";

const Depoimentos = () => {

    const { user } = useAuth();
    const [depoimentos, setDepoimentos] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5); // Itens por página
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchDepoimentos = async () => {
            try {
                setLoading(true);
                const response = await api.get(`/api/depoimento/listarTodos`);

                const depoimentosFiltrados = response.data.filter(depoimento =>
                    depoimento.egresso?.cursos?.some(curso =>
                        curso.curso?.coordenador?.id_coordenador === user.id
                    ) && depoimento.status === "AGUARDANDO"
                );

                setDepoimentos(depoimentosFiltrados);
            } catch (error) {
                console.error("Erro ao buscar depoimentos:", error);
            } finally {
                setLoading(false);
            }

        };

        fetchDepoimentos();
    }, []);

    const totalPages = Math.ceil(depoimentos.length / itemsPerPage);
    const paginatedDepoimentos = depoimentos.slice(
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
                <h1 className="line-text">Homologar Depoimentos</h1>

                <div className="depoimento-container">

                    {loading ? (
                        <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                            <div className="skeleton-loader"></div>
                        </div>
                    ) : (
                        <></>
                    )}

                    <div className="cards-depoimentos">
                        {paginatedDepoimentos.map((depoimento) => (
                                <DepoimentoCard
                                    depoimento={depoimento}
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

export default Depoimentos;