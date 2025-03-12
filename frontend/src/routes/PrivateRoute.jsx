import { useAuth } from '../contexts/AuthContext'; // Use the hook
import {Navigate, Outlet, useLocation} from 'react-router-dom';

const PrivateRoute = ({ allowedRoles }) => {
    const { user, isAuthenticated, loading } = useAuth(); // Get auth state
    const location = useLocation();

    if (loading) return <div>Loading...</div>;

    if (!isAuthenticated) {
        return <Navigate to="/egresso/login" state={{ from: location }} replace />;
    }

    if (!allowedRoles.includes(user.role)) {
        return <Navigate to="/acesso-negado" replace />;
    }

    return <Outlet />;
};

export default PrivateRoute;