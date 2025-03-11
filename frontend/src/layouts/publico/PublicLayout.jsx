import { Outlet } from 'react-router-dom';
import Header from "../../components/Publico/Header/Header.jsx";

const PublicLayout = ({usuario}) => {
    return (
        <div>
            {/* Cabeçalho Fixo */}
            <Header usuario={usuario} />
            {/* Conteúdo Dinâmico das Páginas */}
            <main>
                <Outlet /> {/* Aqui as páginas filhas serão renderizadas */}
            </main>

        </div>
    );
};

export default PublicLayout;