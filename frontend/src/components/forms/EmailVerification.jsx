import React, {useContext, useState} from 'react';
import {Button, Form, Input, message} from 'antd';
import {API_BASE_URL} from '../../Config';
import {AuthContext} from '../../context/AuthContext';
import {decodeToken} from '../../utils/auth';

const EmailVerification = ({setStep, user}) => {
    const {processing, setProcessing} = useState(false);
    const {activeSubmit, setActiveSubmit} = useState(true);
    const {login} = useContext(AuthContext);
    const handlePrevious = () => {
        setStep(1);
    };
    const signin=(email,password)=>{
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
                    login(data.token);
                    message.success("Signed in successfully");
                    setStep(3);
                } else {
                    console.error('Login response:', data);
                }
            })
            .catch((error) => {
                console.error(error);
                message.error('Signed in failed');
            })
    }
    const onFinishVerification = (values) => {
        setProcessing(true);
        console.log('Step 2:', values);
        const {verificationCode} = values;
        const email = user.email;
        const password = user.password;
        const requestData = {
            email,
            verificationCode,
        };
        console.log(JSON.stringify(requestData));
        const url=API_BASE_URL + '/applicants/register/email/verify';
        fetch(url, {
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
                    signin(email,password);
                } else {
                    console.error('Login response:', data);
                }
            })
            .catch((error) => {
                message.error('Failed to verify email!');
            }).finally(() => {
            setProcessing(false);
        });
    };
    return (
        <Form
            layout='vertical'
            onFinish={onFinishVerification}
        >
            <Form.Item
                label="Verification Code"
                name="verificationCode"
                rules={[{
                    required: true,
                    requiredMark: false,
                    message: 'Please enter your last name',
                    clone: false
                }]}
            >
                <Input> </Input>
            </Form.Item>
            <Form.Item wrapperCol={{span: 24}} style={{textAlign: 'right'}}>
                <Button type="default" style={{marginRight: '10px'}} onClick={handlePrevious}>
                    Back
                </Button>
                <Button type="primary" htmlType="submit" loading={processing} disabled={activeSubmit}>
                    Verify
                </Button>
            </Form.Item>
        </Form>
    );
};

export default EmailVerification;
