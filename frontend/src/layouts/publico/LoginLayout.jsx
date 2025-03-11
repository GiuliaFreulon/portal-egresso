import { Outlet } from 'react-router-dom';
import LoginHeader from "../../components/Publico/Header/LoginHeader.jsx";

const LoginLayout = ({usuario}) => {
    return (
        <div>
            {/* Cabeçalho Fixo */}
            <LoginHeader usuario={usuario} />
            {/* Conteúdo Dinâmico das Páginas */}
            <main>
                <Outlet /> {/* Aqui as páginas filhas serão renderizadas */}
            </main>

        </div>
    );
};

export default LoginLayout;