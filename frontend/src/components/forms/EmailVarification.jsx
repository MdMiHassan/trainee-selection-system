import React, { useContext } from 'react';
import { Form, Input, Button, message } from 'antd';
import { API_BASE_URL } from '../../Config';
import { AuthContext } from '../../context/AuthContext';
import { decodeToken } from '../../utils/auth';

const EmailVarification = ({ setStep, user }) => {
    const { updateRole, updateToken, } = useContext(AuthContext);
    const handlePrevious = () => {
        setStep(1);
    };

    const onFinishVerification = (values) => {
        console.log('Step 2:', values);
        const { verificationCode } = values;
        const email = user.email;
        const password = user.password;
        const requestData = {
            email,
            verificationCode,
        };
        console.log(JSON.stringify(requestData));
        fetch(API_BASE_URL + '/applicants/register/email/verify', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.verified) {
                    message.success("Email verified!")
                    const loginRequestData = {
                        email,
                        password,
                    };
                    console.log(loginRequestData);
                    fetch(API_BASE_URL + '/auth/login', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(loginRequestData),
                    })
                        .then((response) => response.json())
                        .then((data) => {
                            if (data.success) {
                                const userToken = data.token;
                                const userRole = decodeToken(userToken).role[0];
                                updateRole(userRole);
                                updateToken(userToken);
                                message.success("Loged in");
                                setStep(3);
                            } else {
                                console.error('Login response:', data);
                            }
                        })
                        .catch((error) => {
                            console.error(error);
                            message.error('Email verification failed!');
                        });
                } else {
                    console.error('Login response:', data);
                }
            })
            .catch((error) => {
                message.error('Email verification failed!');
            });
    };
    return (
        <Form layout='vertical'
            onFinish={onFinishVerification}>
            <Form.Item
                label="Verification Code"
                name="verificationCode"
                rules={[{ required: true, requiredMark: false, message: 'Please enter your last name', clon: false }]}
            >
                <Input />
            </Form.Item>
            <Form.Item wrapperCol={{ span: 24 }} style={{ textAlign: 'right' }}>
                <Button type="default" style={{ marginRight: '10px' }} onClick={handlePrevious}>
                    Back
                </Button>
                <Button type="primary" htmlType="submit">
                    Verify
                </Button>
            </Form.Item>
        </Form>
    );
};

export default EmailVarification;
