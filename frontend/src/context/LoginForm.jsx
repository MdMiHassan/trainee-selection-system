import React, { useContext } from 'react';
import { Form, Input, Button, Typography, Row, Col } from 'antd';
import { API_BASE_URL } from '../Config';
import { decodeToken} from '../utils/auth';
import { AuthContext } from './AuthContext';
import { useNavigate } from 'react-router-dom';

const { Title } = Typography;

const LoginForm = () => {
    const navigateTo=useNavigate();
    const { updateRole, updateToken } = useContext(AuthContext);
    const roleRedirections = {
        APPLICANT: '/',
        ADMIN: '/admin',
        EVALUATOR:'/evaluator'
    };

    const onFinish = (values) => {
        const { email, password } = values;
        const requestData = {
            email,
            password,
        };
        console.log(JSON.stringify(requestData));
        fetch(API_BASE_URL + '/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.success) {
                    const role = decodeToken(data.token).role[0];
                    console.log(role);
                    updateRole(role);
                    updateToken(data.token);
                    const redirectionRoute = roleRedirections[role] || '/default';
                    navigateTo(redirectionRoute)
                } else {
                    console.error('Login response:', data);
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };


    const handleForgot = () => {

    };

    return (
        <Row justify="center" align="middle" style={{ height: '100vh' }}>
            <Col span={5}>
                <div style={{ textAlign: 'center' }}>
                    <Title level={3}>Login</Title>
                </div>
                <Form onFinish={onFinish}>
                    <Form.Item
                        name="email"
                        rules={[{ required: true, message: 'Please enter your email' }]}
                    >
                        <Input placeholder='Email' />
                    </Form.Item>

                    <Form.Item
                        name="password"
                        rules={[{ required: true, message: 'Please enter your password' }]}
                        >
                        <Input.Password placeholder='Password' />
                    </Form.Item>

                    <Form.Item>
                        <Row justify="end">
                            <Col>
                                <Button type="link" onClick={handleForgot}>
                                    Forgot Password?
                                </Button>
                            </Col>
                            <Col>
                                <Button type="primary" htmlType="submit">
                                    Sign In
                                </Button>
                            </Col>
                        </Row>
                    </Form.Item>
                </Form>
            </Col>
        </Row>
    );
};

export default LoginForm;
