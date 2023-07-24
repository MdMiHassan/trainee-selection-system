import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

function LoginGuard({ children }) {
  const { role} = useContext(AuthContext);
  switch (role) {
    case "ADMIN":
      return <Navigate to="/admin/dashboard" />;
    case "APPLICANT":
        return <Navigate to="/applicant" />;
    case "EVALUATOR":
      return <Navigate to="/evaluator" />;
    default:
        console.log(children);
      return children;
  }
}

export default LoginGuard;
