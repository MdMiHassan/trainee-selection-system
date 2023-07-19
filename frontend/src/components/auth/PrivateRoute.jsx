import { useContext } from "react";
import { Navigate, Outlet} from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";


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
    if (isAuthenticated()&&!hasAuthority()) {
        return <Navigate to="/404" />;
    }

    return (<>
    {children}<Outlet />
    </>);
};
export default PrivateRoute;