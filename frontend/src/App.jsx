import { Routes, Route, Navigate } from 'react-router-dom';
import LoginForm from './context/LoginForm';
import AdminPanel from './pages/AdminPanel';
import ApplicantPanel from './pages/ApplicantPanel';
import EvaluatorPanel from './pages/EvaluatorPanel';
import PrivateRoute from './components/auth/PrivateRoute';
import NotFoundPage from './pages/error/NotFoundPage';
import LoginGurd from './components/auth/LoginGurd';
import IndexGuard from './components/auth/IndexGurd';

function App() {
    return (
        <>
            <Routes>
                <Route index element={<IndexGuard children={<ApplicantPanel />} />} />
                <Route path="/login" element={<LoginGurd children={<LoginForm />} />} />
                <Route path="/admin/*" element={<PrivateRoute allowedRole={"ADMIN"} children={<AdminPanel />} />} />
                <Route path="/applicant/*" element={<PrivateRoute allowedRole={'APPLICANT'} children={<ApplicantPanel />} />} />
                <Route path="/evaluator/*" element={<PrivateRoute allowedRole={'EVALUATOR'} children={<EvaluatorPanel />} />} />
                <Route path='/404' element={<NotFoundPage />} />
                <Route path="*" element={<NotFoundPage />} />
            </Routes>
        </>
    );
}

export default App;
