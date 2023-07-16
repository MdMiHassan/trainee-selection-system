import { Routes, Route, Navigate } from 'react-router-dom';
import LoginForm from './context/LoginForm';
import AdminPanel from './pages/AdminPanel';
import ApplicantPanel from './pages/ApplicantPanel';
import EvaluatorPanel from './pages/EvaluatorPanel';
import PrivateRoute from './components/PrivateRoute';

function App() {
    return (
        <>
            <Routes>
                <Route path='/' element={<LoginForm />} />
                <Route path="/login" element={<LoginForm />} />
                <Route path="/admin/*" element={<PrivateRoute allowedRole={"ADMIN"} children={<AdminPanel />} />} />
                <Route path="/applicant/*" element={<PrivateRoute allowedRole={'APPLICANT'} children={<ApplicantPanel />} />} />
                <Route path="/evaluator/*" element={<PrivateRoute allowedRole={'EVALUATOR'} children={<EvaluatorPanel />} />} />
            </Routes>
        </>
    );
}

export default App;
