import { Card, Typography, List, Button, Row, Col, message } from 'antd';
import { useContext, useEffect, useState } from 'react';
import { API_BASE_URL } from '../Config';
import { AuthContext } from '../context/AuthContext';

const NoticeBoard = () => {
    const { token } = useContext(AuthContext);
    const { notices, setNotices } = useState([]);
    console.log(notices);
    useEffect(() => {
        fetch(API_BASE_URL + '/notices',
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            }
        )
            .then((response) => response.json())
            .then((data) => {
                const noticesData = data.content;
                console.log(noticesData)
                setNotices(noticesData);
            })
            .catch((error) => {
                message.error("Failed to load notices");
                console.log(error);
            });
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
                    </Card>)) :
                    <Row justify={"center"}>
                        <Typography.Text>
                            Nothing To Show for Now!
                        </Typography.Text>
                    </Row>}
            </Col>
        </Row>
    );
};

export default NoticeBoard;
