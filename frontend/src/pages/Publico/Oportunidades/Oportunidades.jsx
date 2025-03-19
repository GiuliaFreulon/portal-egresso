import React, {useEffect, useState} from 'react';
import './Oportunidades.css'
import Pagination from "../../../components/common/Pagination/Pagination.jsx";
import api from "../../../services/api.jsx";
import OportunidadeCard from "../../../components/Egresso/OportunidadeCard.jsx";

const Oportunidades = () => {

    const [oportunidades, setOportunidades] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5); // Itens por página
    const [loading, setLoading] = useState(true);


    useEffect(() => {
        const fetchOportunidades = async () => {
            try {
                setLoading(true);
                const response = await api.get(`/api/oportunidade/listarTodos`);
                setOportunidades(response.data); // Dados da página atual
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
            <section className="oportunidades">
                <h1 className="line-text">Painel de Oportunidades</h1>
                <div className="lista-oportunidades">

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
                                    titulo={oportunidade.titulo}
                                    autor={oportunidade.egresso.nome}
                                    descricao={oportunidade.descricao}
                                />
                            ))}
                    </div>

                    <div className="oportunidades-card-separator">
                        <div className="separator-line"></div>
                    </div>

                    <div className="oportunidade-container">
                        <p className="oportunidade-titulo">Titulo da Vaga</p>
                        <p className="oportunidade-autor">Publicado por: Nego ney</p>
                        <span className="oportunidade-descricao">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed.</span>
                    </div>

                    <div className="oportunidades-card-separator">
                        <div className="separator-line"></div>
                    </div>
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

export default Oportunidades;