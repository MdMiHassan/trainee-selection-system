import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { BrowserRouter, Router } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext.jsx';
import { ConfigProvider, theme } from 'antd';

ReactDOM.createRoot(document.getElementById('root')).render(
    <ConfigProvider theme={{ algorithm: theme.darkAlgorithm }}>
    <BrowserRouter>
        <AuthProvider>
            <App />
        </AuthProvider>
    </BrowserRouter>
     </ConfigProvider>
)
