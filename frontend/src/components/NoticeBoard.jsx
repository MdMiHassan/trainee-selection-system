import { Card, Typography, List, Button, Row, Col } from 'antd';

const { Title } = Typography;

const NoticeBoard = () => {
    const notices = [
        {
            id: 1,
            title: 'Notice 1',
            content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        },
        {
            id: 2,
            title: 'Notice 2',
            content: 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',
        },
        {
            id: 3,
            title: 'Notice 3',
            content: 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae.',
        },
    ];

    return (
        <Row justify='center'>
            <Col xs={22} sm={20} md={20} lg={20} xl={18} xxl={16}>
                <Card title="Notice Board">
                    <Row gutter={[16, 16]}>
                        {notices.map((notice) => (
                            <Col xs={24} sm={12} md={8} key={notice.id}>
                                <div className="notice-item">
                                    <Title level={4}>{notice.title}</Title>
                                    <p>{notice.content}</p>
                                    <Button type="primary">View Details</Button>
                                </div>
                            </Col>
                        ))}
                    </Row>
                </Card>
            </Col>
        </Row>

    );
};

export default NoticeBoard;
