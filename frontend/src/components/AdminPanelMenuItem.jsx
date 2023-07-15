import { Link } from 'react-router-dom';
import { Menu } from 'antd';
import {
  DashboardOutlined,
  DesktopOutlined,
  FileTextOutlined,
  PlusOutlined,
  FileSearchOutlined,
  FileSyncOutlined,
  UnorderedListOutlined,
  NotificationOutlined,
  MailOutlined,
  CarryOutOutlined,
  ExperimentOutlined,
  IdcardOutlined,
  TeamOutlined,
  SafetyCertificateOutlined,
  SubnodeOutlined,
  UserOutlined,
  FileOutlined,
} from '@ant-design/icons';

const items = [
  {
    label: 'Dashboard',
    key: '1',
    icon: <DashboardOutlined />,
    path: '/',
  },
  {
    label: 'Stats',
    key: '2',
    icon: <DesktopOutlined />,
    path: '/stats',
  },
  {
    label: 'Circular',
    key: 'sub1',
    icon: <FileTextOutlined />,
    children: [
      {
        label: 'New Circular',
        key: '3',
        icon: <PlusOutlined />,
        path: '/circular/new',
      },
      {
        label: 'Screening',
        key: '4',
        icon: <FileSearchOutlined />,
        path: '/circular/screening',
      },
      {
        label: 'Update Circular',
        key: '5',
        icon: <FileSyncOutlined />,
        path: '/circular/update',
      },
      {
        label: 'All Circular',
        key: '6',
        icon: <UnorderedListOutlined />,
        path: '/circular/all',
      },
    ],
  },
  {
    label: 'Notice',
    key: 'sub2',
    icon: <NotificationOutlined />,
    children: [
      {
        label: 'New Notice',
        key: '7',
        icon: <PlusOutlined />,
        path: '/notice/new',
      },
      {
        label: 'All Notice',
        key: '8',
        icon: <UnorderedListOutlined />,
        path: '/notice/all',
      },
      {
        label: 'Update Notice',
        key: '9',
        icon: <FileSyncOutlined />,
        path: '/notice/update',
      },
    ],
  },
  {
    label: 'Email',
    key: 'sub3',
    icon: <MailOutlined />,
    children: [
      {
        label: 'Sent on Behalf',
        key: '10',
        icon: <CarryOutOutlined />,
        path: '/email/sent',
      },
      {
        label: 'Configure',
        key: '11',
        icon: <ExperimentOutlined />,
        path: '/email/configure',
      },
    ],
  },
  {
    label: 'Admit',
    key: 'sub4',
    icon: <IdcardOutlined />,
    children: [
      {
        label: 'Configure',
        key: '12',
        icon: <ExperimentOutlined />,
        path: '/admit/configure',
      },
    ],
  },
  {
    label: 'User',
    key: 'sub5',
    icon: <TeamOutlined />,
    children: [
      {
        label: 'Admins',
        key: '13',
        icon: <SafetyCertificateOutlined />,
        path: '/user/admins',
      },
      {
        label: 'New Admin',
        key: '14',
        icon: <SubnodeOutlined />,
        path: '/user/new',
      },
      {
        label: 'Applicants',
        key: '15',
        icon: <UserOutlined />,
        path: '/user/applicants',
      },
    ],
  },
  {
    label: 'Files',
    key: '16',
    icon: <FileOutlined />,
    path: '/files',
  },
];

const convertToMenuItems = (items) => {
  return items.map((item) => {
    if (item.children) {
      return (
        <Menu.SubMenu key={item.key} icon={item.icon} title={item.label}>
          {convertToMenuItems(item.children)}
        </Menu.SubMenu>
      );
    }
    return (
      <Menu.Item key={item.key} icon={item.icon}>
        <Link to={item.path}>{item.label}</Link>
      </Menu.Item>
    );
  });
};

const AdminPanelMenuItem = () => {
  return <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">{convertToMenuItems(items)}</Menu>;
};
export default AdminPanelMenuItem;

