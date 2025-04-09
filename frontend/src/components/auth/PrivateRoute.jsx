import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext.jsx";
import PropTypes from "prop-types";

function PrivateRoute({ children, allowedRole }) {
    const { isAuthenticated, hasAuthority } = useContext(AuthContext);

    if (!isAuthenticated()) {
        return <Navigate to="/login" />;
    }

    if (!hasAuthority(allowedRole)) {
        return <Navigate to="/404" />;
    }

    return (
        <>
            {children}
            <Outlet />
        </>
    );
}

PrivateRoute.propTypes = {
    children: PropTypes.node.isRequired,
    allowedRole: PropTypes.string.isRequired,
};

export default PrivateRoute;
