import {  Form, Input, Button, message,} from 'antd';
import '../../styles/RegisterForm.css';
import { useNavigate } from 'react-router-dom';
import { API_BASE_URL } from '../../Config';
function RegisterForm({ setStep,setUser }) {
    const navigateTo=useNavigate();
    const loginOnclick=()=>{
        navigateTo("/login")
    }
    const onFinishSignUpStep = (values) => {
        console.log('Step 1:', values);
        const { firstName, lastName, email, password } = values;
        const requestData = {
            firstName,
            lastName,
            email,
            password,
        };
        console.log(JSON.stringify(requestData));
        fetch(API_BASE_URL + '/applicants/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.success) {
                    const user={
                        email,
                        password,
                    }
                    setUser(user);
                    setStep(2);
                } else {
                    console.error('Login response:', data);
                }
            })
            .catch((error) => {
                message.error("Registration failed!")
            });
    };
    return (<Form layout='vertical'
        onFinish={onFinishSignUpStep} labelAlign='left'>
        <Form.Item
            label="First Name"
            name="firstName"
            rules={[{ required: true, message: 'Please enter your first name' }]}
        >
            <Input />
        </Form.Item>
        <Form.Item
            label="Last Name"
            name="lastName"
            rules={[{ required: true, requiredMark: false, message: 'Please enter your last name', clon: false }]}
        >
            <Input />
        </Form.Item>
        <Form.Item
            label="Email"
            name="email"
            rules={[{ required: true, message: 'Please enter your email' }, { type: 'email', message: 'Please enter a valid email' }]}
        >
            <Input />
        </Form.Item>
        <Form.Item
            label="Password"
            name="password"
            rules={[{ required: true, message: 'Please enter your password' }]}
        >
            <Input.Password />
        </Form.Item>
        <Form.Item wrapperCol={{ span: 24 }} style={{ textAlign: 'right' }}>
        <Button type="default" style={{ marginRight: 10 }} onClick={loginOnclick}>
          Login
        </Button>
        <Button type="primary" htmlType="submit">
          Signup
        </Button>
      </Form.Item>
    </Form>);
}

export default RegisterForm;