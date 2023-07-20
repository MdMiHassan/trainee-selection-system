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
import AdminAllNotice from '../layouts/admin/AdminAllNotices';
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
                    <Breadcrumb style={{
                        margin: '16px',
                    }}
                        items={[
                            {
                                title: 'Home',
                            },
                            {
                                title: <a href="">Application Center</a>,
                            },
                            {
                                title: <a href="">Application List</a>,
                            },
                            {
                                title: 'An Application',
                            },
                        ]}
                    />
                    <div
                        style={{
                            padding: 24,
                            minHeight: 360,
                            background: colorBgContainer,
                        }}
                    >
                        <Routes >
                            <Route path="/stats" element={<AdminNewCircular />} />
                            <Route path="/circular/new" element={<AdminNewCircular />} />
                            <Route path="/circular/screening" element={<AdminCircularScreening />} />
                            <Route path="/circular/update" element={<AdminCircularUpdate />} />
                            <Route path="/circular/all" element={<AdminAllCirculars />} />
                            <Route path="/notice/new" element={<AdminNewNotice />} />
                            <Route path="/notice/all" element={<AdminAllNotice />} />
                            <Route path="/notice/update" element={<AdminUpdateNotice />} />
                            <Route path="/email/sent" element={<AdminEmailSentBySystem />} />
                            <Route path="/email" element={<AdminConfigureEmail />} />
                            <Route path="/admit/configure" element={<AdminConfigureAdmit />} />
                            <Route path="/user/admins" element={<AdminAllAdminUsers />} />
                            <Route path="/user/new" element={<AdminNewAdmin />} />
                            <Route path="/user/applicants" element={<AdminAllApplicantUsers />} />
                            <Route path="/files" element={<AdminAllFiles />} />
                        </Routes>
                    </div>
                </Content>
                <Footer style={{ textAlign: 'center' }}>
                    BJIT Academy ©2023 Created by Mehedi #30069
                </Footer>
            </Layout>
        </Layout>
    );
}
export default AdminPanel;