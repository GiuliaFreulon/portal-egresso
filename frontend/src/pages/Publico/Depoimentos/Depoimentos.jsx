import React, {useEffect, useState} from 'react';
import './Depoimentos.css'
import DepoimentoCard from "../../../components/Egresso/DepoimentoCard.jsx";
import Pagination from "../../../components/common/Pagination/Pagination.jsx";
import api from "../../../services/api.jsx";

const Depoimentos = () => {

    const [depoimentos, setDepoimentos] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5); // Itens por página
    const [loading, setLoading] = useState(true);
    const [filtro, setFiltro] = useState('')
    const [searchTermAno, setSearchTermAno] = useState('');
    const [searchTermCurso, setSearchTermCurso] = useState('');
    const [filteredDepoimentos, setFilteredDepoimentos] = useState([]);


    useEffect(() => {
        const fetchDepoimentos = async () => {
            try {
                setLoading(true);

                let response;

                if (filtro === 'recentes') {
                    response = await api.get(`/api/depoimento/buscarRecentes`);
                } else {
                    response = await api.get(`/api/depoimento/listarTodos`);
                }

                setDepoimentos(response.data.filter((depoimento) => depoimento.status === "APROVADO"));

                console.log(response.data);
            } catch (error) {
                console.error("Erro ao buscar depoimentos:", error);
            } finally {
                setLoading(false);
            }

        };

        fetchDepoimentos();
    }, [filtro]);

    // Filtra e divide os dados no cliente
    useEffect(() => {
        setFilteredDepoimentos(depoimentos.filter(depoimento => {
            const ano = searchTermAno.toString();
            const curso = searchTermCurso.toLowerCase()

            return depoimento.egresso.cursos?.some(cursoEgresso =>
                cursoEgresso.curso.nome.toLowerCase().includes(curso)
                && depoimento.data?.[0].toString().toLowerCase().includes(ano)
            );
        }));
    }, [searchTermAno, searchTermCurso, depoimentos]);



    const totalPages = Math.ceil(filteredDepoimentos.length / itemsPerPage);
    const paginatedDepoimentos = filteredDepoimentos.slice(
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
                <h1 className="line-text">Painel de Depoimentos</h1>

                <div className="depoimentos-filter-container">
                    <div className="filters">
                        <div>
                            {/* Campo de Input para Ano */}
                            <input
                                type="text"
                                name="ano"
                                placeholder="Filtrar por ano"
                                value={searchTermAno}
                                onChange={(e) => {
                                    setSearchTermAno(e.target.value);
                                    setCurrentPage(1); // Reset para primeira página
                                }}
                            />

                            {/* Filtro de Curso */}
                            <input
                                type="text"
                                name="curso"
                                placeholder="Filtrar por curso"
                                value={searchTermCurso}
                                onChange={(e) => {
                                    setSearchTermCurso(e.target.value);
                                    setCurrentPage(1); // Reset para primeira página
                                }}
                            />
                        </div>
                        <select
                            className="depoimentos-filter-btn"
                            value={filtro}
                            onChange={(e) => setFiltro(e.target.value)}
                        >
                            <option value="recentes">Mais Recentes</option>
                            <option value="antigos">Mais Antigos</option>
                        </select>

                    </div>
                </div>

                {loading ? (
                    <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                        <div className="skeleton-loader"></div>
                    </div>
                ) : (
                    <></>
                )}

                <div className="depoimentos-cards-egresso">
                    {paginatedDepoimentos
                        .map((depoimento) => (
                        <DepoimentoCard
                            id={depoimento.id}
                            foto={depoimento.egresso.foto}
                            nome={depoimento.egresso.nome}
                            curso={depoimento.egresso.cursos?.map((curso) => curso.curso.nome).join(", ")}
                            descricao={depoimento.texto}
                            data={depoimento.data}
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

export default Depoimentos;