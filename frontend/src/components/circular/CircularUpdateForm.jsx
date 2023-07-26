import { Button, Card, Col, DatePicker, Form, Input, InputNumber, Row, Select, Space } from "antd";
import TextArea from "antd/es/input/TextArea";
import { useContext, useEffect, useState } from "react";
import { API_BASE_URL } from "../../Config";
import { AuthContext } from "../../context/AuthContext";

function CircularUpdateForm({ circularId }) {
    const [overview, setOverview] = useState('');
    const [duties, setDuties] = useState('');
    const [skills, setSkills] = useState('');
    const { token } = useContext(AuthContext);
    const [form] = Form.useForm();
    useEffect(() => {
        fetch(API_BASE_URL + `/circulars/${circularId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then((response) => response.json())
            .then((data) => {
                form.setFieldsValue(data);
            })
            .catch((error) => {
                message.error("Something Went Wrong");
                console.log(error);
            });
    }, [circularId])
    const onFinish = (values) => {
        const {
            title,
            // closingDate,
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
            // closingDate,
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
        fetch(API_BASE_URL + `/circulars${circularId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(circularData)
        })
            .then((response) => {
                if (response.ok) {
                    message.success("Circular posted");
                }
            })
            .catch((error) => {
                message.error("Something Went Wrong");
                console.log(error);
            });
    };
    const formItemLayout = {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 8 },
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 16 },
        },
    };
    return (<Card>
        <Row justify={'center'}>
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
                    scrollToFirstError
                    layout="vertical"
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
                        style={{ width: '200px' }}
                    >
                        <InputNumber style={{ width: '405px' }} />

                    </Form.Item>
                    {/* <Form.Item
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
                        <DatePicker style={{ width: '480px' }} />
                    </Form.Item> */}
                    <Space.Compact>
                        <Form.Item
                            name="minExp"
                            colon={false}
                            label="Minumum Experience">
                            <InputNumber placeholder="Minimum" style={{ width: '200px' }} />
                        </Form.Item>
                        <Form.Item
                            name="maxExp"
                            colon={false}
                            label="Maximum Experience">
                            <InputNumber placeholder="Maximum" style={{ width: '205px' }} />
                        </Form.Item>
                    </Space.Compact>
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
                    <Form.Item wrapperCol={{ span: 16 }} style={{ textAlign: 'right' }}>
                        <Button type="primary" htmlType="submit">
                            Post
                        </Button>
                    </Form.Item>
                </Form>
            </Col>
        </Row>
    </Card>);
}

export default CircularUpdateForm;