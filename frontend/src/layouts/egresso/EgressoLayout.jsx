import { Outlet } from 'react-router-dom';
import EgressoHeader from "../../components/Egresso/Header/Header.jsx";

const EgressoLayout = () => {
    return (
        <div>
            {/* Cabeçalho Fixo */}
            <EgressoHeader />
            {/* Conteúdo Dinâmico das Páginas */}
            <main>
                <Outlet /> {/* Aqui as páginas filhas serão renderizadas */}
            </main>
        </div>
    );
};

export default EgressoLayout;