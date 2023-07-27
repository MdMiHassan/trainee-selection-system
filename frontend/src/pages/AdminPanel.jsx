import React, { useState } from 'react';
import { Breadcrumb, Col, Layout, Menu, Row, Typography, theme } from 'antd';
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
import AdminConfigureAdmit from '../layouts/admin/AdminConfigureAdmit';
import AdminAllEvaluator from '../layouts/admin/AdminAllEvaluator';
import NewEvaluator from '../components/evaluator/NewEvaluator';
import DashBoard from '../layouts/admin/DashBoard';
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
                <Row justify={"center"} style={{ margin: "30px auto" }}>
                    <Row>
                        <Col style={{ textAlign: "center" }}>
                            <Typography.Title level={4} style={{ margin: "0" }}>
                                TSS
                            </Typography.Title>
                        </Col>
                    </Row>
                </Row>
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
                                title: 'Greetings, Have a great day',
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
                            <Route path="/dashboard" element={<DashBoard />} />
                            <Route path="/circular/new" element={<AdminNewCircular />} />
                            <Route path="/circular/screening" element={<AdminCircularScreening />} />
                            <Route path="/circular/update" element={<AdminCircularUpdate />} />
                            <Route path="/circular/all" element={<AdminAllCirculars />} />
                            <Route path="/notice/new" element={<AdminNewNotice />} />
                            <Route path="/notice/all" element={<AdminAllNotice />} />
                            <Route path="/admit/configure" element={<AdminConfigureAdmit />} />
                            <Route path="/evaluators" element={<AdminAllEvaluator />} />
                            <Route path="/evaluators/new" element={<NewEvaluator />} />
                        </Routes>
                    </div>
                </Content>
                <Footer style={{ textAlign: 'center' }}>
                    BJIT Academy ©2023
                </Footer>
            </Layout>
        </Layout>
    );
}
export default AdminPanel;