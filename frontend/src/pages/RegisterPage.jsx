import React, { useState } from 'react';
import { Row, Col, Card, Steps } from 'antd';
import RegisterForm from '../components/forms/RegisterForm';
import EmailVarification from '../components/forms/EmailVarification';
import RegisterPersonalInfo from '../components/forms/RegisterPersionalInfo';
import '../styles/RegisterPage.css';
const items = [
    {
        title: 'Sign Up'
    },
    {
        title: 'Verify Email'
    },
    {
        title: 'Basic Info',
    },
];
const RegisterPage = () => {
    const [step, setStep] = useState(1);
    const [email, setEmail] = useState(null);

    return (
        <Row justify="center" align="middle" style={{ minHeight: '100vh' }}>
            <Col xs={22} sm={22} md={20} lg={8} xl={8} xxl={10}>
                <Card type="inner"
                    title={<div className='card-heading-steps'><Steps current={step - 1}
                        labelPlacement="vertical" items={items} size="small" direction='horizontal' /></div>}
                    style={{ marginTop: "10px" }} >
                    {step === 1 && (<RegisterForm setStep={setStep} setUserEmail={setEmail} />)}
                    {step === 2 && (<EmailVarification setStep={setStep} email={email}/>)}
                    {step === 3 && (<RegisterPersonalInfo setStep={setStep} />)}
                </Card>
            </Col>
        </Row>
    );
};

export default RegisterPage;
