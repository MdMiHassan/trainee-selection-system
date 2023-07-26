import React, { useState } from 'react';
import { Form, Input, Button, Upload, message, DatePicker, Card, Space, Row, Col } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { API_BASE_URL } from '../../Config';

const AdmitCardForm = ({ circularId }) => {
    const [form] = Form.useForm();
    const [authoritySignatureImageId, setAuthoritySignatureImageId] = useState([]);
    const { token } = useContext(AuthContext);
    const headers = {
        Authorization: `Bearer ${token}`,
    };
    const handlesetFileListSignatureChange = (info) => {
        if (info.file.status === 'done') {
            const response = info.file.response;
            setAuthoritySignatureImageId(response.id);
        }
    };
    const onFinish = async (values) => {
        const requestData = {
            ...values,
            authoritySignatureImageId,
        };
        fetch(API_BASE_URL + `/admits/info/${circularId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => response.json())
            .then((data) => {
                message.success("Admit Imformation Saved");
            })
            .catch((error) => {
                message.error("Admit Imformation Saving failed");
            });
    };

    return (

        <Row justify={"center"} style={{marginTop:"50px"}}>
            <Col>
                <Card title={"Provide Admit Card Information"}>
                    <Form form={form} layout="vertical" onFinish={onFinish}>
                        <Space.Compact>
                            <Form.Item label="Exam Date" name="examDate" rules={[{ required: true, message: 'Please select exam date' }]}>
                                <DatePicker />
                            </Form.Item>
                            <Form.Item label="Exam Time" name="time" rules={[{ required: true, message: 'Please enter exam time' }]}>
                                <Input />
                            </Form.Item>
                        </Space.Compact>
                        <Form.Item label="Location" name="location" rules={[{ required: true, message: 'Please enter location' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item label="Instructions" name="instructions" rules={[{ required: true, message: 'Please enter instructions' }]}>
                            <Input.TextArea />
                        </Form.Item>
                        <Form.Item label="Authority Name" name="authorityName" rules={[{ required: true, message: 'Please enter authority name' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item label="Division" name="division" rules={[{ required: true, message: 'Please enter division' }]}>
                            <Input />
                        </Form.Item>
                        <Form.Item label="Authority Signature" name="authoritySignatureImageId">
                            <Upload action={API_BASE_URL + "/resource/upload"}
                                listType="picture"
                                maxCount={1}
                                headers={headers}
                                onChange={handlesetFileListSignatureChange}>
                                <Button icon={<UploadOutlined />} style={{ width: "340px" }}>Upload Authority Signature</Button>
                            </Upload>
                        </Form.Item>
                        <Form.Item wrapperCol={24} style={{ textAlign: "end" }}>
                            <Button type="primary" htmlType="submit">Submit</Button>
                        </Form.Item>
                    </Form>

                </Card>
            </Col>
        </Row>
    );
};

export default AdmitCardForm;
