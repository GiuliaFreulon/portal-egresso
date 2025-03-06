import { Outlet } from 'react-router-dom';
import EgressoHeader from '../../components/Egresso/Header';
import EgressoSidebar from '../../components/Egresso/Sidebar';

const EgressoLayout = () => {
    return (
        <div className="egresso-layout">
            <EgressoHeader />
            <div className="content-wrapper">
                <EgressoSidebar />
                <main className="main-content">
                    <Outlet /> {/* Aqui serão renderizadas as páginas filhas */}
                </main>
            </div>
        </div>
    );
};

export default EgressoLayout;