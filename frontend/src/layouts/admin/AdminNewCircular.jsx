import { Alert, Card, DatePicker } from "antd";
import { API_BASE_URL } from "../../Config";
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
function AdminNewCircular() {
    const [overview, setOverview] = useState('');
    const [duties, setDuties] = useState('');
    const [skills, setSkills] = useState('');
    const [form] = Form.useForm();
    const authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIkFETUlOIl0sInN1YiI6InN1cGVyLmFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjg5NjUyNDg2LCJleHAiOjE2OTIyNDQ0ODZ9.Yx0wSSepGudpDPs5a0mCygANdowZ_U2xLB2nhV4RCMI";
    const onFinish = (values) => {
        const {
            title,
            closingDate,
            trainingType,
            careerLevel,
            requiredEducation,
            hiringLocation,
            salary,
            currency,
            vacancy,
            minExp,
            maxExp
        } = values;

        const circularData = {
            title,
            closingDate,
            overview,
            trainingType,
            vacancy,
            careerLevel,
            requiredEducation,
            hiringLocation,
            skills,
            duties,
            salary,
            currency,
            minExp,
            maxExp
        };
        console.log(JSON.stringify(circularData));
        fetch(API_BASE_URL + '/circulars', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authToken
            },
            body: JSON.stringify(circularData)
        })
            .then((response) => response.json())
            .then((data) => {
                console.log("Circular posted");
            })
            .catch((error) => {
                console.log(error);
            });
        console.log('Received values of form: ', values);
    };

    return (<Card title="New Circular">
        <Row justify={'center'}>
            {/* <Col xs={16} sm={16} md={16} lg={16} xl={16} xxl={16}> */}
            <Col span={15}>
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
                        label="Training Title"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input your training title!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name="trainingType"
                        label="Training Type"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please select training type!',
                            },
                        ]}
                    >
                        <Select placeholder="select training type">
                            <Option value="FULLTIME">Full Time</Option>
                            <Option value="PARTTIME">Part Time</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="careerLevel"
                        label="Career Level"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please select career level!',
                            },
                        ]}
                    >
                        <Select placeholder="select career level">
                            <Option value="SENIOR">SENIOR</Option>
                            <Option value="MIDDLE">MIDDLE</Option>
                            <Option value="ENTRY">ENTRY</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="vacancy"
                        label="Vacancy"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input vacency!',
                            },
                        ]}
                    >
                        <InputNumber />
                    </Form.Item>
                    <Form.Item
                        name="minExp"
                        colon={false}
                        label="Minumum Experience">
                        <InputNumber />
                    </Form.Item>
                    <Form.Item
                        name="salary"
                        colon={false}
                        label="Salary">
                        <InputNumber />
                    </Form.Item>
                    <Form.Item
                        name="currency"
                        colon={false}
                        label="Salary Currecny">
                        <Input />
                    </Form.Item>

                    <Form.Item
                        name="maxExp"
                        colon={false}
                        label="Maximum Experience">
                        <InputNumber />
                    </Form.Item>
                    <Form.Item
                        name="requiredEducation"
                        label="Required Education"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input required education!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name="hiringLocation"
                        label="Hiring Location"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input hiring location!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        name="overview"
                        label="Overview of the Training"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input Intro',
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
                        name="duties"
                        label="Duties of the Training"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input duties',
                            },
                        ]}
                    >
                        <TextArea
                            value={duties}
                            onChange={(e) => setDuties(e.target.value)}
                            placeholder="Duties of the training"
                            autoSize={{
                                minRows: 5,
                                maxRows: 10,
                            }}
                        />
                    </Form.Item>
                    <Form.Item
                        name="skills"
                        label="Required skills for the Training"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please input required skills',
                            },
                        ]}
                    >
                        <TextArea
                            value={skills}
                            onChange={(e) => setSkills(e.target.value)}
                            placeholder="Skills required for the training"
                            autoSize={{
                                minRows: 5,
                                maxRows: 10,
                            }}
                        />
                    </Form.Item>
                    <Form.Item
                        name="gender"
                        label="Trainee Gender"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please select gender!',
                            },
                        ]}
                    >
                        <Select placeholder="select gender">
                            <Option value="MALE">Male</Option>
                            <Option value="FEMALE">Female</Option>
                            <Option value="ANY">Any</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="closingDate"
                        label="Closing Date"
                        colon={false}
                        rules={[
                            {
                                required: true,
                                message: 'Please select closing date!',
                            },
                        ]}
                    >
                        <DatePicker />
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

export default AdminNewCircular;