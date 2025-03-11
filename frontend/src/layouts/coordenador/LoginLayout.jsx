import { Outlet } from 'react-router-dom';
import Header from "../../components/common/Header/Header.jsx";

const LoginLayout = () => {
    return (
        <div>
            {/* Cabeçalho Fixo */}
            <Header/>
            {/* Conteúdo Dinâmico das Páginas */}
            <main>
                <Outlet /> {/* Aqui as páginas filhas serão renderizadas */}
            </main>

        </div>
    );
};

export default LoginLayout;