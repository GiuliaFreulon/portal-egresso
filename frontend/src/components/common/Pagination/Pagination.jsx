import { useState } from "react";
import './Pagination.css';

export default function Pagination({ totalPages }) {
    const [currentPage, setCurrentPage] = useState(1);

    const goToPage = (page) => {
        if (page >= 1 && page <= totalPages) {
            setCurrentPage(page);
        }
    };

    const renderPages = () => {
        let pages = [];
        const MAX_VISIBLE_PAGES = 3; // Número de páginas visíveis no início e no fim

        // Caso 1: Total de páginas é pequeno (exibe todas as páginas)
        if (totalPages <= 6) {
            for (let i = 1; i <= totalPages; i++) {
                pages.push(i);
            }
        }
        // Caso 2: Total de páginas é grande (exibe páginas com "..." no meio)
        else {
            // Sempre exibe as primeiras páginas
            pages.push(1, 2, 3);

            // Adiciona "..." se a página atual estiver longe do início
            if (currentPage > MAX_VISIBLE_PAGES + 1) {
                pages.push("...");
            }

            // Exibe a página atual e suas vizinhas (se estiver no meio)
            if (currentPage > MAX_VISIBLE_PAGES && currentPage < totalPages - 2) {
                pages.push(currentPage - 1, currentPage, currentPage + 1);
            }

            // Adiciona "..." se a página atual estiver longe do final
            if (currentPage < totalPages - 2) {
                pages.push("...");
            }

            // Sempre exibe as últimas páginas
            pages.push(totalPages - 2, totalPages - 1, totalPages);
        }

        return pages;
    };

    return (
        <div className="pagination">
            <button
                onClick={() => goToPage(currentPage - 1)}
                disabled={currentPage === 1}
            >
                {"<"}
            </button>

            {renderPages().map((page, index) =>
                page === "..." ? (
                    <span key={index} className="dots">...</span>
                ) : (
                    <button
                        key={index}
                        className={page === currentPage ? "active" : ""}
                        onClick={() => goToPage(page)}
                    >
                        {page}
                    </button>
                )
            )}

            <button
                onClick={() => goToPage(currentPage + 1)}
                disabled={currentPage === totalPages}
            >
                {">"}
            </button>
        </div>
    );
}