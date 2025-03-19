import './Pagination.css';

export default function Pagination({ currentPage, totalPages, onPageChange }) {
    const renderPages = () => {
        const pages = new Set();
        const MAX_VISIBLE_PAGES = 3;

        // Adiciona as primeiras páginas
        for (let i = 1; i <= Math.min(MAX_VISIBLE_PAGES, totalPages); i++) {
            pages.add(i);
        }

        // Adiciona as últimas páginas
        const lastStart = Math.max(totalPages - MAX_VISIBLE_PAGES + 1, MAX_VISIBLE_PAGES + 1);
        for (let i = lastStart; i <= totalPages; i++) {
            pages.add(i);
        }

        // Adiciona páginas adjacentes à atual se estiver no meio
        if (currentPage > MAX_VISIBLE_PAGES && currentPage < lastStart) {
            pages.add(currentPage - 1);
            pages.add(currentPage);
            pages.add(currentPage + 1);
        }

        // Processa as lacunas e insere "..."
        const sortedPages = Array.from(pages).sort((a, b) => a - b);
        const processedPages = [];
        let prevPage = 0;

        for (const page of sortedPages) {
            if (page - prevPage > 1) {
                processedPages.push("...");
            }
            processedPages.push(page);
            prevPage = page;
        }

        return processedPages;
    };

    return (
        <div className="pagination">
            {/* Botões Anterior/Próximo mantidos */}
            <button
                key="prev"
                onClick={() => onPageChange(currentPage - 1)}
                disabled={currentPage === 1}
            >
                {"<"}
            </button>

            {renderPages().map((page, index) => {
                if (page === "...") {
                    return (
                        <span
                            key={`dots-${index}`}
                            className="dots"
                        >
                ...
            </span>
                    );
                }

                return (
                    <button
                        key={page}
                        className={page === currentPage ? "active" : ""}
                        onClick={() => onPageChange(page)}
                        disabled={page === currentPage}
                    >
                        {page}
                    </button>
                );
            })}

            <button
                key="next"
                onClick={() => onPageChange(currentPage + 1)}
                disabled={currentPage === totalPages}
            >
                {">"}
            </button>
        </div>
    );
}