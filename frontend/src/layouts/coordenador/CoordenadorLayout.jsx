import { Outlet } from 'react-router-dom';
import CoordenadorHeader from "../../components/Coordenador/Header/Header.jsx";

const CoordenadorLayout = () => {
    return (
        <div>
            {/* Cabeçalho Fixo */}
            <CoordenadorHeader />
            {/* Conteúdo Dinâmico das Páginas */}
            <main>
                <Outlet /> {/* Aqui as páginas filhas serão renderizadas */}
            </main>
        </div>
    );
};

export default CoordenadorLayout;