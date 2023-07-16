import { useContext } from "react";
import { Navigate} from "react-router-dom";
import { AuthContext } from "../context/AuthContext";


function PrivateRoute({ children, allowedRole }) {
    const { role, token } = useContext(AuthContext);
    const isAuthenticated = () => {
        return token ? true : false;
    };
    const hasAuthority = () => {
        return role === allowedRole;
    };

    if (!isAuthenticated()) {
        return <Navigate to="/login" />;
    }

    if (!hasAuthority()) {
        return <Navigate to="/login" />;
    }
    return children;
};
export default PrivateRoute;