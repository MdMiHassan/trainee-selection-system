import { Row, Col, Form, Input, Button, Select, DatePicker, Upload, message, Steps } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import '../../styles/RegisterForm.css'
function RegisterPersonalInfo() {
    const onFinishPersionalInfo = (values) => {
        console.log('Step 2:', values);
        // Perform form submission or further processing here
        message.success('Registration successful!');
    };
    return (<Form layout='vertical'
        onFinish={onFinishPersionalInfo}
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
            name="dob"
            rules={[{ required: true, message: 'Please select your date of birth' }]}
        >
            <DatePicker />
        </Form.Item>
        <Form.Item
            label="Contact Number"
            name="contactNumber"
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
        <Form.Item
            label="Upload Resume"
            name="resume"
            valuePropName="fileList"
            getValueFromEvent={(e) => e.fileList}
            rules={[{ required: true, message: 'Please upload your resume' }]}
        >
            <Upload multiple={false} beforeUpload={() => false}>
                <Button icon={<UploadOutlined />}>Upload</Button>
            </Upload>
        </Form.Item>
        <Form.Item
            label="Upload Profile Picture"
            name="profilePicture"
            valuePropName="fileList"
            getValueFromEvent={(e) => e.fileList}
            rules={[{ required: true, message: 'Please upload your profile picture' }]}
        >
            <Upload multiple={false} beforeUpload={() => false}>
                <Button icon={<UploadOutlined />}>Upload</Button>
            </Upload>
        </Form.Item>
        <Form.Item wrapperCol={{ span: 24 }} style={{ textAlign: 'right' }}>
            <Button type="primary" htmlType="submit">
                Save
            </Button>
        </Form.Item>
    </Form>);
}

export default RegisterPersonalInfo;