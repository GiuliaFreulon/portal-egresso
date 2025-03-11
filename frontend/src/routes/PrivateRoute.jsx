// import { useContext } from 'react';
// import { Navigate, useLocation } from 'react-router-dom';
// import { AuthContext } from '../contexts/AuthContext';
//
// const PrivateRoute = ({ allowedRoles, children }) => {
//     const { user } = useContext(AuthContext);
//     const location = useLocation();
//
//     if (!user) {
//         return <Navigate to="/login" state={{ from: location }} replace />;
//     }
//
//     if (!allowedRoles.includes(user.role)) {
//         return <Navigate to="/nao-autorizado" replace />;
//     }
//
//     return children;
// };
//
// export default PrivateRoute;