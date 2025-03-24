import React, {useEffect, useState} from 'react';
import './Gerenciamento.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import GerenciarOportunidadeCard from "../../../../components/Egresso/GerenciarOportunidadeCard.jsx";
import Pagination from "../../../../components/common/Pagination/Pagination.jsx";
import {Link} from "react-router-dom";
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";
import GerenciarDepoimentoCard from "../../../../components/Egresso/GerenciarDepoimentoCard.jsx";

const Gerenciamento = () => {

    const { user } = useAuth();
    const [oportunidades, setOportunidades] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5); // Itens por página
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOportunidades = async () => {
            try {
                setLoading(true);
                const response = await api.get(`/api/oportunidade/listarTodos`);
                setOportunidades(response.data.filter(
                    (oportunidade) => oportunidade.egresso.id_egresso === user.id
                )); // Dados da página atual
            } catch (error) {
                console.error("Erro ao buscar oportunidades:", error);
            } finally {
                setLoading(false);
            }

        };

        fetchOportunidades();
    }, []);

    const totalPages = Math.ceil(oportunidades.length / itemsPerPage);
    const paginatedOportunidades = oportunidades.slice(
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
                <h1 className="line-text">Suas Oportunidades</h1>

                <div className="egresso-gerenciamento-oportunidade-container">
                    <div className="egresso-gerenciamento-oportunidade-header">
                        <Link to="/egresso/oportunidade/envio" className="egresso-gerenciamento-oportunidade-header-cadastrar">
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon" />
                            <p>Nova Oportunidade</p>
                        </Link>
                    </div>

                    {loading ? (
                        <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                            <div className="skeleton-loader"></div>
                        </div>
                    ) : (
                        <></>
                    )}

                    <div className="cards-oportunidades">
                        {paginatedOportunidades?.map((oportunidade) => (
                            <GerenciarOportunidadeCard
                                oportunidade={oportunidade}
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