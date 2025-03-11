import { useAuth } from '../contexts/AuthContext'; // Use the hook
import { Navigate, useLocation } from 'react-router-dom';

const PrivateRoute = ({ allowedRoles, children }) => {
    const { user, isAuthenticated, loading } = useAuth(); // Get auth state
    const location = useLocation();

    if (loading) return <div>Loading...</div>;

    if (!isAuthenticated) {
        return <Navigate to="/egresso_login" state={{ from: location }} replace />;
    }

    if (!allowedRoles.includes(user?.role)) {
        return <Navigate to="/egresso_login" replace />;
    }

    return children;
};

export default PrivateRoute;