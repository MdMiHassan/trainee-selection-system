import React, { useState } from 'react';
import { Layout, Menu, Dropdown, Button, Avatar, Badge, Row } from 'antd';
import { BellOutlined, UserOutlined, LogoutOutlined } from '@ant-design/icons';
import '../../styles/AdminNavbar.css'
import Logout from '../../components/auth/Logout';
import AvatarDropdown from '../../components/AvatarMenu';
const { Header } = Layout;

const AdminNavbar = () => {
    const [notificationCount, setNotificationCount] = useState(5);
    const handleProfileUpdate = () => {
        console.log('Profile Update clicked');
    };
    const userMenu = (
        <Menu>
            <Menu.Item key="profile" onClick={handleProfileUpdate}>
                Profile
            </Menu.Item>
            <Logout />
        </Menu>
    );

    return (
        <Row justify={"end"} style={{ padding: "10px 20px" }}>
            <AvatarDropdown />
        </Row>
    );
};

export default AdminNavbar;
