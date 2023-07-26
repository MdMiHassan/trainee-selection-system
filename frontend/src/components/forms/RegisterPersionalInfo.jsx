import React, { useState, useContext } from 'react';
import { Row, Col, Form, Input, Button, Select, DatePicker, Upload, message, Steps, Space } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import '../../styles/RegisterForm.css'
import { AuthContext } from '../../context/AuthContext';
import { API_BASE_URL } from '../../Config';
import { useNavigate } from 'react-router-dom';
function RegisterPersonalInfo() {
    const [resumeId, setResumeId] = useState(null);
    const [profileImageId, setProfileImageId] = useState(null);
    const { token } = useContext(AuthContext);
    const navigateTo = useNavigate();
    const headers = {
        Authorization: `Bearer ${token}`,
    };
    const handleProfileUploadChange = (info) => {
        if (info.file.status === 'done') {
            const response = info.file.response;
            setProfileImageId(response.id);
        }
    };
    const handleResumeUploadChange = (info) => {
        if (info.file.status === 'done') {
            const response = info.file.response;
            setResumeId(response.id);
        }
    };
    const onFinishPersionalInfo = (values) => {
        console.log('Step 3:', values);
        const { firstName,
            lastName, gender, dateOfBirth,
            degreeName, institutionName,
            cgpa, passingYear,
            presentAddress, phone } = values;
        const requestData = {
            firstName,
            lastName,
            gender,
            dateOfBirth,
            degreeName,
            institutionName,
            cgpa,
            passingYear,
            presentAddress,
            phone,
            profileImageId,
            resumeId,
        };
        console.log(`Authorization: Bearer ${token}`)
        console.log(JSON.stringify(requestData));
        fetch(API_BASE_URL + '/applicants/profile', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => response.json())
            .then((data) => {
                navigateTo("/")
            })
            .catch((error) => {
                message.error("Registration failed!")
            });
    };
    return (<Form layout='vertical'
    >
        <Form.Item
            label="Gender"
            name="gender"
            rules={[{ required: true, message: 'Please select your gender' }]}
        >
            <Select>
                <Option value="male">Male</Option>
                <Option value="female">Female</Option>
                <Option value="other">Other</Option>
            </Select>
        </Form.Item>
        <Form.Item
            label="Date of Birth"
            name="dateOfBirth"
            rules={[{ required: true, message: 'Please select your date of birth' }]}
        >
            <DatePicker />
        </Form.Item>
        <Form.Item
            label="Contact Number"
            name="phone"
            rules={[{ required: true, message: 'Please enter your contact number' }]}
        >
            <Input />
        </Form.Item>
        <Form.Item
            label="Highest Degree Name"
            name="degreeName"
            rules={[{ required: true, message: 'Please enter your degree name' }]}
        >
            <Input />
        </Form.Item>
        <Form.Item
            label="Educational Institute"
            name="educationalInstitute"
            rules={[{ required: true, message: 'Please enter your educational institute' }]}
        >
            <Input />
        </Form.Item>
        <Form.Item
            label="CGPA"
            name="cgpa"
            rules={[{ required: true, message: 'Please enter your CGPA' }]}
        >
            <Input />
        </Form.Item>
        <Form.Item
            label="Passing Year"
            name="passingYear"
            rules={[{ required: true, message: 'Please enter your passing year' }]}
        >
            <Input />
        </Form.Item>
        <Form.Item
            label="Present Address"
            name="presentAddress"
            rules={[{ required: true, message: 'Please enter your present address' }]}
        >
            <Input.TextArea />
        </Form.Item>
        <Space.Compact>
            <Form.Item
                label="Upload Your Profile Picture"
                name="profilePicture"
                valuePropName="fileList"
                getValueFromEvent={(e) => e.fileList}
                rules={[{ required: true, message: 'Please upload your profile picture' }]}
            >
                <Upload
                    action={API_BASE_URL + "/resource/upload"}
                    listType="picture"
                    maxCount={1}
                    headers={headers}
                    onChange={handleProfileUploadChange}
                >
                    <Button icon={<UploadOutlined />}>Profile Picture</Button>
                </Upload>
            </Form.Item>
            <Form.Item
                label="Upload Your resume"
                name="resume"
                valuePropName="fileList"
                getValueFromEvent={(e) => e.fileList}
                rules={[{ required: true, message: 'Please upload your profile picture' }]}
            >
                <Upload
                    action={API_BASE_URL + "/resource/upload"}
                    listType='picture'
                    maxCount={1}
                    headers={headers}
                    onChange={handleResumeUploadChange}
                >
                    <Button icon={<UploadOutlined />}>Resume</Button>
                </Upload>
            </Form.Item>
        </Space.Compact>
        <Form.Item wrapperCol={{ span: 24 }} style={{ textAlign: 'right' }}>
            <Button type="primary" htmlType="submit" onClick={onFinishPersionalInfo}>
                Save
            </Button>
        </Form.Item>
    </Form>);
}

export default RegisterPersonalInfo;