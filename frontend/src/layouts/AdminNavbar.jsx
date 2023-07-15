import React, { useState } from 'react';
import { Layout, Menu, Dropdown, Button, Avatar, Badge } from 'antd';
import { BellOutlined, UserOutlined, LogoutOutlined } from '@ant-design/icons';
import '../styles/AdminNavbar.css'
const { Header } = Layout;

const AdminNavbar = () => {
  const [notificationCount, setNotificationCount] = useState(5); // Example notification count

  const handleLogout = () => {
    // Perform logout logic here
    console.log('Logout clicked');
  };

  const handleProfileUpdate = () => {
    // Redirect to profile update page or show a modal for profile update
    console.log('Profile Update clicked');
  };

  const notificationMenu = (
    <Menu>
      <Menu.Item key="1">Notification 1</Menu.Item>
      <Menu.Item key="2">Notification 2</Menu.Item>
      <Menu.Item key="3">Notification 3</Menu.Item>
    </Menu>
  );

  const userMenu = (
    <Menu>
      <Menu.Item key="profile" onClick={handleProfileUpdate}>
        Profile
      </Menu.Item>
      <Menu.Item key="logout" onClick={handleLogout}>
        Logout
      </Menu.Item>
    </Menu>
  );

  return (
    <Header>
      <div className="navbar">
        <div className="right-section">
          <Dropdown overlay={notificationMenu} trigger={['click']}>
            <Badge count={notificationCount}>
              <Button shape="circle" icon={<BellOutlined />} />
            </Badge>
          </Dropdown>
          <Dropdown overlay={userMenu} trigger={['click']}>
            <div className="user-info">
              <Avatar icon={<UserOutlined />} />
            </div>
          </Dropdown>
        </div>
      </div>
    </Header>
  );
};

export default AdminNavbar;
