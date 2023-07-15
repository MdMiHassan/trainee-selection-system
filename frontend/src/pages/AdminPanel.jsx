import React, { useState } from 'react';
import {
  DashboardOutlined,
  DesktopOutlined,
  FileOutlined,
  TeamOutlined,
  SafetyCertificateOutlined,
  FileTextOutlined,
  UnorderedListOutlined,
  PlusOutlined,
  FileSearchOutlined,
  FileSyncOutlined,
  NotificationOutlined,
  MailOutlined,
  CarryOutOutlined,
  ExperimentOutlined,
  IdcardOutlined,
  SubnodeOutlined,
  UserOutlined,
  SyncOutlined,
} from '@ant-design/icons';
import { Breadcrumb, Layout, Menu, theme } from 'antd';
import AdminNavbar from '../layouts/AdminNavbar';
import AdminPanelMenuItem from '../components/AdminPanelMenuItem';
const { Header, Content, Footer, Sider } = Layout;
function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    SyncOutlined,
    label,
  };
}
const items = [
  getItem('Dashboard', '1', <DashboardOutlined />),
  getItem('Stats', '2', <DesktopOutlined />),
  getItem('Circular', 'sub1', <FileTextOutlined />, [
    getItem('New Circular', '3', <PlusOutlined />),
    getItem('Screening', '4', <FileSearchOutlined />),
    getItem('Update Circular', '5', <FileSyncOutlined />),
    getItem('All Circular', '6', <UnorderedListOutlined />)
  ]),
  getItem('Notice', 'sub2', <NotificationOutlined />, [
    getItem('New Notice', '7', <PlusOutlined />),
    getItem('All Notice', '8', <UnorderedListOutlined />),
    getItem("Update Notice", "9", <SyncOutlined />)
  ]),
  getItem('Email', 'sub3', <MailOutlined />, [
    getItem('Sent on Behalf', '10', <CarryOutOutlined />),
    getItem('Configure', '11', <ExperimentOutlined />)]),
  getItem('Admit', 'sub4', <IdcardOutlined />, [
    getItem('Configure', '12', <ExperimentOutlined />)]),
  getItem('User', 'sub5', <TeamOutlined />, [
    getItem('Admins', '13', <SafetyCertificateOutlined />),
    getItem('New Admin', '14', <SubnodeOutlined />),
    getItem('Applicants', '11', <UserOutlined />)]),
  getItem('Files', '15', <FileOutlined />),
];
const AdminPanel = () => {
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
            Dashboard panel.
          </div>
        </Content>
        <Footer
          style={{
            textAlign: 'center',
          }}
        >
          BJIT Academy ©2023 Created by Mehedi #30069
        </Footer>
      </Layout>
    </Layout>
  );
};
export default AdminPanel;