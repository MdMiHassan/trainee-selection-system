import { useContext } from 'react';
import { LogoutOutlined } from '@ant-design/icons';
import { Menu } from 'antd';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { removeSortedToken } from '../../utils/auth';

function Logout() {
  const { updateRole, updateToken } = useContext(AuthContext);
  const navigate = useNavigate();

  const logout = () => {
    removeSortedToken();
    updateRole(null);
    updateToken(null);
  };

  return (
    <Menu.Item key="logout" onClick={logout}>
      <span style={{ paddingRight: '4px' }}>
        <LogoutOutlined />
      </span>
      Logout
    </Menu.Item>
  );
}

export default Logout;
