import React, { useState } from 'react';
import { Breadcrumb, Layout, Menu, theme } from 'antd';
import { Route, Router, Routes } from 'react-router-dom';
import AdminPanelMenuItem from '../components/AdminPanelMenuItem';
import AdminNewCircular from '../layouts/admin/AdminNewCircular';
import AdminNavbar from '../layouts/admin/AdminNavbar';
import AdminCircularScreening from '../layouts/admin/AdminCircularScreening';
import AdminCircularUpdate from '../layouts/admin/AdminCircularUpdate';
import AdminAllCirculars from '../layouts/admin/AdminAllCirculars';
import AdminNewNotice from '../layouts/admin/AdminNewNotice';
import AdminAllNotice from '../layouts/admin/AdminAllNotice';
import AdminUpdateNotice from '../layouts/admin/AdminUpdateNotice';
import AdminEmailSentBySystem from '../layouts/admin/AdminEmailSentBySystem';
import AdminConfigureEmail from '../layouts/admin/AdminConfigureEmail';
import AdminConfigureAdmit from '../layouts/admin/AdminConfigureAdmit';
import AdminAllAdminUsers from '../layouts/admin/AdminAllAdmins';
import AdminNewAdmin from '../layouts/admin/AdminNewAdmin';
import AdminAllApplicantUsers from '../layouts/admin/AdminAllApplicantUsers';
import AdminAllFiles from '../layouts/admin/AdminAllFiles';
const { Content, Footer, Sider } = Layout;

function AdminPanel() {
    const [collapsed, setCollapsed] = useState(false);
    const {
        token: { colorBgContainer },
    } = theme.useToken();
    return (
        <Layout
            style={{
                minHeight: '100vh',
            }}
        >
            <Sider collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
                <div className="demo-logo-vertical" />
                <AdminPanelMenuItem />
            </Sider>
            <Layout>
                <AdminNavbar />
                <Content
                    style={{
                        margin: '0 16px',
                    }}
                >
                    <Breadcrumb
                        style={{
                            margin: '16px 0',
                        }}
                    >
                        <Breadcrumb.Item>Admin</Breadcrumb.Item>
                        <Breadcrumb.Item>Dashboard</Breadcrumb.Item>
                    </Breadcrumb>
                    <div
                        style={{
                            padding: 24,
                            minHeight: 360,
                            background: colorBgContainer,
                        }}
                    >
                        <Routes>
                            <Route exact path="/admin" element={<AdminNewCircular />} />
                            <Route path="/admin/stats" element={<AdminNewCircular />} />
                            <Route path="/admin/circular/new" element={<AdminNewCircular />} />
                            <Route path="/admin/circular/screening" element={<AdminCircularScreening />} />
                            <Route path="/admin/circular/update" element={<AdminCircularUpdate />} />
                            <Route path="/admin/circular/all" element={<AdminAllCirculars />} />
                            <Route path="/admin/notice/new" element={<AdminNewNotice />} />
                            <Route path="/admin/notice/all" element={<AdminAllNotice />} />
                            <Route path="/admin/notice/update" element={<AdminUpdateNotice />} />
                            <Route path="/admin/email/sent" element={<AdminEmailSentBySystem />} />
                            <Route path="/admin/email/configure" element={<AdminConfigureEmail />} />
                            <Route path="/admin/admit/configure" element={<AdminConfigureAdmit />} />
                            <Route path="/admin/user/admins" element={<AdminAllAdminUsers />} />
                            <Route path="/admin/user/new" element={<AdminNewAdmin />} />
                            <Route path="/admin/user/applicants" element={<AdminAllApplicantUsers />} />
                            <Route path="/admin/files" element={<AdminAllFiles />} />
                        </Routes>
                    </div>
                </Content>
                <Footer style={{textAlign: 'center'}}>
                    BJIT Academy ©2023 Created by Mehedi #30069
                </Footer>
            </Layout>
        </Layout>
    );
}
export default AdminPanel;