import React, { useState, useContext, useEffect } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { API_BASE_URL } from '../../Config';
import { UploadOutlined } from '@ant-design/icons';
import {
    Button,
    Card,
    DatePicker,
    Form,
    Input,
    Modal,
    Select,
    Upload,
    message,
} from 'antd';

function ApplyModal({ isApplyModalOpen, setIsApplyModalOpen, circularId }) {
    const [componentSize, setComponentSize] = useState('default');
    const [form] = Form.useForm();
    const [resumeId, setResumeId] = useState(null);
    const [profileImageId, setProfileImageId] = useState(null);
    const { token } = useContext(AuthContext);
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
    const handleNewRoundOk = (event) => {
        event.stopPropagation();
        form.validateFields().then((values) => {
            const { firstName,
                lastName, gender, dateOfBirth,
                degreeName, institutionName,
                cgpa, passingYear,
                presentAddress, phone,email } = values;
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
                email,
            };
            console.log(JSON.stringify(requestData));
            fetch(API_BASE_URL + '/circulars/'+circularId+'/apply', {
                method: 'POST',
                headers: {
                    'Authorization':`Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            })
                .then((response) => response.json())
                .then((data) => {
                    message.success("Your Application received")
                    setIsApplyModalOpen(false)
                })
                .catch((error) => {
                    message.error("Failed to apply!")
                });
        });
    };
    const handleNewRoundCancel = (event) => {
        event.stopPropagation();
        setIsApplyModalOpen(false);
    };
    const { loading, setLoading } = useState(true);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds');
                const data = await response.json();
                if (Array.isArray(data)) {
                    const sortedRoundData = data ? [...data].sort((a, b) => a.serialNo - b.serialNo) : null;
                    const optionData = sortedRoundData.slice(0, data.length - 1);
                    setRoundData(optionData);
                } else {
                    console.error('Fetched data is not an array:', data);
                }
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);

    return (
        <Modal title={"Please Fill the form to submit application"}
            open={isApplyModalOpen}
            onCancel={handleNewRoundCancel}
            footer={[
                <Button key="cancel" onClick={handleNewRoundCancel}>
                    Cancel
                </Button>,
                <Button key="submit" type="primary" loading={loading} onClick={handleNewRoundOk}>
                    Apply
                </Button>,
            ]}
        >
            <Card>
                <Form layout='vertical'
                form={form}
                ><Form.Item
                    label="First Name"
                    name="firstName"
                    rules={[{ required: true, message: 'Please enter your first name' }]}
                >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="Last Name"
                        name="lastName"
                        rules={[{ required: true, message: 'Please enter your last name' }]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="Email"
                        name="email"
                        rules={[{ required: true, message: 'Please select your email' }]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="Gender"
                        name="gender"
                        rules={[{ required: true, message: 'Please select your gender' }]}
                    >
                        <Select>
                            <Option value="MALE">Male</Option>
                            <Option value="FEMALE">Female</Option>
                            <Option value="OTHER">Other</Option>
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
                        name="institutionName"
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
                </Form>
            </Card>
        </Modal>

    );
};
export default ApplyModal;