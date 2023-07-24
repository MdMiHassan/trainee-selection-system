import { Button, Card, Form, Input, message } from "antd";
import { useContext, useRef } from "react";
import { AuthContext } from "../../context/AuthContext";
import { API_BASE_URL } from "../../Config";

function NewEvaluator() {
    const formRef = useRef(null);
    const { token } = useContext(AuthContext);
    const handleCreateEvaluator = (values) => {
        const { email,
            password,
            firstName,
            lastName } = values;
        const requestData = {
            email,
            password,
            firstName,
            lastName
        }
        console.log(requestData);
        fetch(API_BASE_URL + '/evaluators', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => {
                if(response.ok){
                    console.log(response);
                    message.success("Evaluator created successfully");
                    formRef.current.resetFields();
                }
            })
            .catch((error) => {
                console.error(error);
                message.error("Failed to create evaluator");
            });
    };
    return (
        <Card title={"Create New Evaluator"}>
            <Form 
            ref={formRef}
            onFinish={handleCreateEvaluator}>
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
        </Card>
    );
}

export default NewEvaluator;