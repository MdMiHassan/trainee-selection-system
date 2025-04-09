import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

function IndexGuard({ children }) {
    const { isAuthenticated,getAccessRoles  } = useContext(AuthContext);

    if (isAuthenticated()) {
        const role=getAccessRoles()[0];
        console.log(role)
        switch (role) {
            case "APPLICANT":
                return <Navigate to="/applicant" />;
            case "ADMIN":
                return <Navigate to="/admin/dashboard" />;
            case "EVALUATOR":
                return <Navigate to="/evaluator" />;
            default:
                return <Navigate to="/404" />;
        }
    }else{
        return <Navigate to="/login" />;
    }
}

export default IndexGuard;
