import React, { useState } from 'react';
import { Form, Input, Button, Steps, Row, Col, message } from 'antd';
import { API_BASE_URL } from '../../Config';

const EmailVarification = ({ setStep, email }) => {
    const [verificationCode, setVerificationCode] = useState('');

    const handlePrevious = () => {
        setStep(1);
    };

    const handleVerificationCodeChange = (e) => {
        setVerificationCode(e.target.value);
    };

    const onFinishVerification = (values) => {
        console.log('Step 2:', values);
        const { verificationCode } = values;
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
                    message.success("Email verified")
                    setStep(3);
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
