import { Link } from 'react-router-dom';
import { Menu } from 'antd';
import {
  DashboardOutlined,
  FileTextOutlined,
  PlusOutlined,
  FileSearchOutlined,
  FileSyncOutlined,
  UnorderedListOutlined,
  NotificationOutlined,
  ExperimentOutlined,
  IdcardOutlined,
  TeamOutlined,
  SafetyCertificateOutlined,
} from '@ant-design/icons';

const items = [
  {
    label: 'Dashboard',
    key: '1',
    icon: <DashboardOutlined />,
    path: '/admin/dashboard',
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
        path: '/admin/circular/new',
      },
      {
        label: 'Screening',
        key: '4',
        icon: <FileSearchOutlined />,
        path: '/admin/circular/screening',
      },
      {
        label: 'Update Circular',
        key: '5',
        icon: <FileSyncOutlined />,
        path: '/admin/circular/update',
      },
      {
        label: 'All Circular',
        key: '6',
        icon: <UnorderedListOutlined />,
        path: '/admin/circular/all',
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
        path: '/admin/notice/new',
      },
      {
        label: 'All Notice',
        key: '8',
        icon: <UnorderedListOutlined />,
        path: '/admin/notice/all',
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
        path: '/admin/admit/configure',
      },
    ],
  },
  {
    label: 'Evaluator',
    key: 'sub5',
    icon: <TeamOutlined />,
    children: [
      {
        label: 'New Evaluator',
        key: '13',
        icon: <SafetyCertificateOutlined />,
        path: '/admin/evaluators/new',
      },
    ],
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
  return <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" >{convertToMenuItems(items)}</Menu>;
};
export default AdminPanelMenuItem;

