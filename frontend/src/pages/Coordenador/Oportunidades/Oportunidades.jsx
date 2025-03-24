import React, {useEffect, useState} from 'react';
import './Oportunidades.css'
import OportunidadeCard from "../../../components/Coordenador/GerenciarOportunidadeCard.jsx";
import Pagination from "../../../components/common/Pagination/Pagination.jsx";
import api from "../../../services/api.jsx";
import {useAuth} from "../../../contexts/AuthContext.jsx";

const Oportunidades = () => {

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

                const oportunidadesFiltradas = response.data.filter(oportunidade =>
                    oportunidade.egresso?.cursos?.some(curso =>
                        curso.curso?.coordenador?.id_coordenador === user.id
                    )
                );

                setOportunidades(oportunidadesFiltradas);
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
                <h1 className="line-text">Homologar Oportunidades</h1>

                <div className="oportunidade-container">

                    {loading ? (
                        <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                            <div className="skeleton-loader"></div>
                        </div>
                    ) : (
                        <></>
                    )}

                    <div className="cards-oportunidades">
                        {paginatedOportunidades?.filter((oportunidades) => oportunidades.status === "AGUARDANDO" )
                            .map((oportunidade) => (
                                <OportunidadeCard
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

export default Oportunidades;