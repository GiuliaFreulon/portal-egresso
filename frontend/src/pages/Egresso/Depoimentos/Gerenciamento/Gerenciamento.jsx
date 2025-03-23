import React, {useEffect, useState} from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarDepoimentoCard from "../../../../components/Egresso/GerenciarDepoimentoCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";
import {Link} from "react-router-dom";
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Gerenciamento = () => {

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
                setDepoimentos(response.data.filter(
                    (depoimento) => depoimento.egresso.id_egresso === user.id
                )); // Dados da página atual
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
                <h1 className="line-text">Seus Depoimentos</h1>

                <div className="egresso-gerenciamento-depoimento-container">
                    <div className="egresso-gerenciamento-depoimento-header">
                        <Link to="/egresso/depoimento/envio" className="egresso-gerenciamento-depoimento-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Novo Depoimento</p>
                        </Link>
                    </div>

                    {loading ? (
                        <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                            <div className="skeleton-loader"></div>
                        </div>
                    ) : (
                        <></>
                    )}

                    <div className="cards-depoimentos">
                        {paginatedDepoimentos?.map((depoimento) => (
                            <GerenciarDepoimentoCard
                                texto={depoimento.texto}
                                status={depoimento.status}
                            />
                        ))}
                    </div>

                    <GerenciarDepoimentoCard texto={'Texto do Depoimento'} status={'Enviado'}/>
                    <GerenciarDepoimentoCard texto={'Texto do Depoimento'} status={'Rejeitado'}/>
                    <GerenciarDepoimentoCard texto={'Texto do Depoimento'} status={'Aprovado'}/>

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