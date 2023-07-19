import { Routes, Route, Navigate, Outlet } from 'react-router-dom';
import LoginForm from './context/LoginForm';
import AdminPanel from './pages/AdminPanel';
import ApplicantPanel from './pages/ApplicantPanel';
import EvaluatorPanel from './pages/EvaluatorPanel';
import PrivateRoute from './components/auth/PrivateRoute';
import NotFoundPage from './pages/error/NotFoundPage';
import LoginGurd from './components/auth/LoginGurd';
import IndexGuard from './components/auth/IndexGurd';
import RegisterPage from './pages/RegisterPage';

function App() {
    return (
        <>
            <Routes>
                {/* <Route index element={<IndexGuard children={<ApplicantPanel ><Outlet /></ApplicantPanel>} />} /> */}
                <Route path='/register' element={<RegisterPage />} />
                <Route path="/login" element={<LoginGurd children={<LoginForm />} />} />
                {/* <Route path="/admin/*" element={<PrivateRoute allowedRole={"ADMIN"} children={<AdminPanel ><Outlet /></AdminPanel>} />} /> */}
                <Route path="/admin/*" element={<AdminPanel />} />
                <Route path="/applicant/*" element={<PrivateRoute allowedRole={'APPLICANT'} children={<ApplicantPanel ><Outlet/></ApplicantPanel>} />} />
                <Route path="/evaluator/*" element={<PrivateRoute allowedRole={'EVALUATOR'} children={<EvaluatorPanel ><Outlet /></EvaluatorPanel>} />} />
                <Route path='/404' element={<NotFoundPage />} />
                <Route path="*" element={<NotFoundPage />} />
            </Routes>
            <AdminPanel />
        </>
    );
}

export default App;
