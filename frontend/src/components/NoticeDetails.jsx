import { Card, Typography, Button } from 'antd';

const { Title, Paragraph } = Typography;

const NoticeDetails = () => {
  const notice = {
    id: 1,
    title: 'Notice 1',
    content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    date: '20 July 2023',
  };

  return (
    <Card title={notice.title}>
      <div>
        <Title level={5}>Date: {notice.date}</Title>
        <Paragraph>{notice.content}</Paragraph>
        <Button type="primary">Go Back</Button>
      </div>
    </Card>
  );
};

export default NoticeDetails;
