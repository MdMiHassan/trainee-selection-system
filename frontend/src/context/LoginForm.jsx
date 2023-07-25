import React, { useContext } from 'react';
import { Form, Input, Button, Row, Col, Card, message } from 'antd';
import { API_BASE_URL } from '../Config';
import { decodeToken, removeSortedToken } from '../utils/auth';
import { AuthContext } from './AuthContext';
import { useNavigate } from 'react-router-dom';
import { LockOutlined, UserOutlined } from '@ant-design/icons';

const LoginForm = () => {
    const navigateTo = useNavigate();
    const { updateRole, updateToken } = useContext(AuthContext);
    const roleRedirections = {
        APPLICANT: '/',
        ADMIN: '/admin/dashboard',
        EVALUATOR: '/evaluator'
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
                    removeSortedToken();
                    const role = decodeToken(data.token).role[0];
                    console.log(role);
                    updateRole(role);
                    updateToken(data.token);
                    const redirectionRoute = roleRedirections[role] || '/404';
                    navigateTo(redirectionRoute)
                    message.success("Succesfully Loged In");
                } else {
                    message.error("Login Failed");
                    console.error('Login response:', data);
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return (
        <Row justify="center" align="middle" style={{ height: '100vh' }}>
            <Col xs={22} sm={10} md={10} lg={6} xl={6} xxl={4}>
                <Card type="inner" title={"Sign in"} >
                    <Form
                        name="normal_login"
                        className="login-form"
                        initialValues={{
                            remember: true,
                        }}
                        onFinish={onFinish}
                    >
                        <Form.Item
                            name="email"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your email!',
                                },
                            ]}
                        >
                            <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
                        </Form.Item>
                        <Form.Item
                            name="password"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Password!',
                                },
                            ]}
                        >
                            <Input
                                prefix={<LockOutlined className="site-form-item-icon" />}
                                type="password"
                                placeholder="Password"
                            />
                        </Form.Item>
                        <Form.Item>
                            <Button wrapperCol={24} type="primary" htmlType="submit" className="login-form-button" style={{ width: "100%" }}>
                                Log in
                            </Button>
                            Or <a href="/register">register now!</a>
                        </Form.Item>
                    </Form>
                </Card>
            </Col>
        </Row>
    );
};

export default LoginForm;
