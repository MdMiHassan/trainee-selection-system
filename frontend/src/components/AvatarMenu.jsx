import { Avatar, Dropdown, Menu } from 'antd';
import { UserOutlined, LogoutOutlined, UploadOutlined } from '@ant-design/icons';

const AvatarDropdown = () => {
    const handleMenuClick = (e) => {
        if (e.key === 'logout') {
            // Perform logout logic here
        }
    };

    const menu = (
        <Menu onClick={handleMenuClick}>
            <Menu.Item key="profile">
                <span style={{ paddingRight: '4px' }}>
                    <UserOutlined />
                </span>
                Update Profile
            </Menu.Item>
            <Menu.Item key="resume">
                <span style={{ paddingRight: '4px' }}>
                    <UploadOutlined />
                </span>
                Upload Resume
            </Menu.Item>
            <Menu.Item key="logout">
                <span style={{ paddingRight: '4px' }}>
                    <LogoutOutlined />
                </span>
                Logout
            </Menu.Item>
        </Menu>
    );

    return (
        <Dropdown overlay={menu} trigger={['click']}>
            <a className="ant-dropdown-link" onClick={(e) => e.preventDefault()}>
                <Avatar icon={<UserOutlined />} />
            </a>
        </Dropdown>
    );
};

export default AvatarDropdown;
