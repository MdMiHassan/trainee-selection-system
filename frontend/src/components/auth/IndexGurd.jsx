import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

function IndexGuard({ children }) {
    const { role, token } = useContext(AuthContext);
    const isAuthenticated = () => {
        console.log("Token before checking: "+token);
        return token? true : false;
    };
    const isApplicant = () => {
        console.log("Role before checking: "+role);
        return role === "APPLICANT";
    }
    console.log("index gurd is authenticated: "+isAuthenticated());
    console.log("index gurd is applicant: "+isApplicant());
    if (!isAuthenticated() || isApplicant()) {
        console.log("Inside If!!")
        return children;
    }
    console.log("Outeside IF!!")
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
