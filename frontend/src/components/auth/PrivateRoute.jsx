import { useContext } from "react";
import { Navigate} from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import { SoundTwoTone } from "@ant-design/icons";


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

    return children;
};
export default PrivateRoute;