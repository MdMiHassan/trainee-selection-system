import { EditFilled } from "@ant-design/icons";
import { Button, Card, Input, Modal, Row, Typography } from "antd";
import { Form } from "react-router-dom";
import { API_BASE_URL } from "../../Config";
import { useState } from "react";

function CircularRoundCard({roundinfo,currentRoundSerialNo,circularId}) {
    const [modalVisible, setModalVisible] = useState(false);
    const [currentRoundId, setCurrentRoundId] = useState(3);
    const handleModalOpen = (roundId) => {
        setModalVisible(true);
        setCurrentRoundId(roundId);
    };
    const handleModalClose = () => {
        setModalVisible(false);
    };
    const handleCreateEvaluator = (values) => {
        fetch(API_BASE_URL + '/evaluators', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                circularId: circularId,
                roundId: currentRoundId,
                email: values.email,
                password: values.password,
                firstName: values.firstName,
                lastName: values.lastName,
            }),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                message.success("Evaluator created successfully");
            })
            .catch((error) => {
                console.error(error);
                message.error("Failed to create evaluator");
            });

        setModalVisible(false);
    };
    return (
        <>
            <Card
                key={roundinfo.serialNo}
                style={roundinfo.serialNo == currentRoundSerialNo ? {
                    backgroundColor: "#0e153a", width: 300,
                    margin: "16px",
                } : {
                    width: 300,
                    margin: "16px",
                }}
                actions={[
                    <EditFilled key={`editround${roundinfo.serialNo}`} onClick={handleModalOpen} />,
                ]}
            >
                <Row justify='center'>
                    <Typography.Title level={5}>{roundinfo.title}{roundinfo.serialNo == currentRoundId ? <Row justify={"center"}><Typography.Text>Current Round</Typography.Text></Row> : ""}</Typography.Title>
                </Row>
                <Modal
                    title="Create Evaluator"
                    open={modalVisible}
                    onCancel={handleModalClose}
                    footer={null}
                >
                    <Form onFinish={handleCreateEvaluator}>
                        <Form.Item
                            name="email"
                            label="Email"
                            rules={[{ required: true, message: "Email is required" }]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="password"
                            label="Password"
                            rules={[{ required: true, message: "Password is required" }]}
                        >
                            <Input.Password />
                        </Form.Item>

                        <Form.Item
                            name="firstName"
                            label="First Name"
                            rules={[{ required: true, message: "First name is required" }]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="lastName"
                            label="Last Name"
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit">Create Evaluator</Button>
                        </Form.Item>
                    </Form>
                </Modal>
            </Card>

        </>
    );
}

export default CircularRoundCard;