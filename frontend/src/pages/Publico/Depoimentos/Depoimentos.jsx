import React, {useEffect, useState} from 'react';
import './Depoimentos.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFilter} from "@fortawesome/free-solid-svg-icons";
import DepoimentoCard from "../../../components/Egresso/DepoimentoCard.jsx";
import Pagination from "../../../components/common/Pagination/Pagination.jsx";
import api from "../../../services/api.jsx";

const Depoimentos = () => {

    const [depoimentos, setDepoimentos] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(5); // Itens por página


    useEffect(() => {
        const fetchDepoimentos = async () => {
            try {
                const response = await api.get(`/api/depoimento/listarTodos`);
                setDepoimentos(response.data); // Dados da página atual
                console.log(response.data);
            } catch (error) {
                console.error("Erro ao buscar depoimentos:", error);
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
                <h1 className="line-text">Painel de Depoimentos</h1>

                <div className="depoimentos-filter-container">
                    <button className="depoimentos-filter-btn"><FontAwesomeIcon icon={faFilter} />Filtros</button>
                </div>

                <div className="depoimentos-cards-egresso">
                    {paginatedDepoimentos?.filter((depoimento) => depoimento.status === "AGUARDANDO")
                        .map((depoimento) => (
                        <DepoimentoCard
                            id={depoimento.id}
                            foto={depoimento.foto}
                            nome={depoimento.egresso.nome}
                            curso={depoimento.egresso.cursos?.map((curso) => curso.curso.nome).join(", ")}
                            descricao={depoimento.texto}
                            data={depoimento.data}
                        />
                    ))}

                    <DepoimentoCard
                        foto="https://pbs.twimg.com/ext_tw_video_thumb/1451000252190892033/pu/img/omuzIHGdbNc90sQw.jpg"
                        nome="Hisalana Silva"
                        curso="Ciência da Computação - Graduação"
                        descricao="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dignissim libero ipsum, sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies mauris sit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed. In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed In ex nisi, sagittis ac odio non, fermentum ultricies maurissit amet placerat orci suscipit sed "
                        data="26/02/2025"
                    />
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