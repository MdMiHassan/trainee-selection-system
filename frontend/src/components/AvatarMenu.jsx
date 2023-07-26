import { Avatar, Dropdown, Menu } from 'antd';
import { UserOutlined} from '@ant-design/icons';
import Logout from './auth/Logout';

const AvatarDropdown = () => {
    const menu = (
        <Menu>
            <Logout />
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
