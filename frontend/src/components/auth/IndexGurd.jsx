import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

function IndexGuard({ children }) {
    const { role, token } = useContext(AuthContext);
    const isAuthenticated = () => {
        return token ? true : false;
    };
    const isApplicant = () => {
        return role === "APPLICANT";
    }
    if (!isAuthenticated() || isApplicant()) {
        return children;
    }
    switch (role) {
        case "ADMIN":
            return <Navigate to="/admin" />;
        case "EVALUATOR":
            return <Navigate to="/evaluator" />;
        default:
            return <Navigate to="/404" />;
    }
}

export default IndexGuard;
