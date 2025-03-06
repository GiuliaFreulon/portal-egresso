import CoordenadorLayout from '../../../layouts/CoordenadorLayout/CoordenadorLayout';
import { StatsPanel, RecentActivities } from '../../../pages/Coordenador/Dashboard/Dashboard';

const CoordenadorDashboard = () => {
    return (
        <CoordenadorLayout>
            <div className="dashboard-content">
                <h1>Painel do Coordenador</h1>
                <StatsPanel />
                <RecentActivities />
            </div>
        </CoordenadorLayout>
    );
};

export default CoordenadorDashboard;