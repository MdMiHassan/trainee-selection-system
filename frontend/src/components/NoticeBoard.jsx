import { Card, Typography, List, Button, Row, Col, Divider } from 'antd';
import { useEffect, useState } from 'react';
import { API_BASE_URL } from '../Config';

const { Title } = Typography;

const NoticeBoard = () => {
    // const notices = [
    //     {
    //         id: 1,
    //         title: 'Notice 1',
    //         content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    //     },
    //     {
    //         id: 2,
    //         title: 'Notice 2',
    //         content: 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',
    //     },
    //     {
    //         id: 3,
    //         title: 'Notice 3',
    //         content: 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae.',
    //     },
    // ];
    const { notices, setNotices } = useState([
        {
            id: 1,
            title: 'Notice 1',
            content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        },
    ]);

    console.log(notices);
    useEffect(() => {
        const fetchData = async () => {
            try {
                fetch(API_BASE_URL + '/v1/notices')
                    .then((response) => response.json())
                    .then((data) => {
                        const noticesData = data.content;
                        console.log(noticesData)
                        setNotices(noticesData);
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);
    const formatDate = (timestamp) => {
        const date = new Date(timestamp);
        return date.toLocaleString();
    };
    return (
            <Row justify='center'>
                <Col xs={22} sm={20} md={20} lg={10} xl={10} xxl={10} align={"midle"}>
                    {notices ? notices.map((notice) => (
                        <Card title={notice.title} style={{ marginBottom: "20px", marginTop: "0" }}>
                            <Typography.Text style={{ margin: "0" }}>
                                Updated On {formatDate(notice.postedAt)}
                            </Typography.Text>
                            <p>{notice.details}</p>
                            <Row justify="end">
                                <Button type="primary" style={{ alignSelf: "right" }}>View Details</Button>
                            </Row>
                        </Card>)) : <Typography.Text>
                        No Data
                    </Typography.Text>}
                </Col>
            </Row>
    );
};

export default NoticeBoard;
