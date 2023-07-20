
import { Alert, Card, DatePicker, Upload, message } from "antd";
import {
    Button,
    Col,
    Form,
    Input,
    InputNumber,
    Row,
    Select,
} from 'antd';
import TextArea from "antd/es/input/TextArea";
import React, { useState } from 'react';
import { API_BASE_URL } from "../../../Config";
import { UploadOutlined } from "@ant-design/icons";
const { Option } = Select;
const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 8,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 16,
        },
    },
};
const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};
function AdminNoticeForm() {
    const [overview, setOverview] = useState('');
    const [form] = Form.useForm();
    const authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIkFETUlOIl0sInN1YiI6InN1cGVyLmFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjg5NjUyNDg2LCJleHAiOjE2OTIyNDQ0ODZ9.Yx0wSSepGudpDPs5a0mCygANdowZ_U2xLB2nhV4RCMI";
    const [attachmentId, setAttachmentId] = useState(null);
    const headers = {
        Authorization: authToken,
    };
    const handleAttachmentUploadchange = (info) => {
        if (info.file.status === 'done') {
            const response = info.file.response;
            setAttachmentId(response.id);
        }
    };

    const onFinish = (values) => {
        const {
            title,
            details,
        } = values;

        const noticeData = {
            title,
            details,
            attachmentId
        };
        console.log(JSON.stringify(noticeData));
        fetch(API_BASE_URL + '/v1/notices', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authToken
            },
            body: JSON.stringify(noticeData)
        })
            .then((response) => response.json())
            .then((data) => {
                message.success("Notice Posted");
            })
            .catch((error) => {
                message.error("Notice Posting failed!");
                console.log(error);
            });
        console.log('Received values of form: ', values);
    };

    return (<Card title="New Circular">
        <Row justify={'center'}>
            <Col xs={16} sm={16} md={16} lg={16} xl={16} xxl={16}>
                <Form
                    {...formItemLayout}
                    form={form}
                    name="register"
                    onFinish={onFinish}
                    initialValues={{
                        residence: ['zhejiang', 'hangzhou', 'xihu'],
                        prefix: '86',
                    }}
                    style={{
                        maxWidth: 600,
                    }}
                    scrollToFirstError
                >
                    <Form.Item
                        name="title"
                        label="Notice Title"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input notice title!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name="details"
                        label="Description of the notice"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input description',
                            },
                        ]}
                    >
                        <TextArea
                            value={overview}
                            onChange={(e) => setOverview(e.target.value)}
                            placeholder="Overview of the training"
                            autoSize={{
                                minRows: 5,
                                maxRows: 10,
                            }}
                        />
                    </Form.Item>
                    <Form.Item
                        name="attachment"
                        label="Attachment"
                        colon={false}
                    >
                        <Upload
                            action={API_BASE_URL + "/resource/upload"}
                            listType="picture"
                            maxCount={1}
                            headers={headers}
                            onChange={handleAttachmentUploadchange}
                        >
                            <Button icon={<UploadOutlined />}>Attachment</Button>
                        </Upload>
                    </Form.Item>
                    <Form.Item {...tailFormItemLayout} wrapperCol={{ span: 24 }} style={{ textAlign: 'right' }}>
                        <Button type="primary" htmlType="submit">
                            Post
                        </Button>
                    </Form.Item>
                </Form>

            </Col>
            {/* <Col xs={22} sm={22} md={20} lg={8} xl={8} xxl={10}>
                <Alert
                    message="Informational Notes"
                    description="You can insert new line to indicate bullet point in duities & skills"
                    type="info"
                    showIcon
                />
            </Col> */}
        </Row>
    </Card>);
}

export default AdminNoticeForm;