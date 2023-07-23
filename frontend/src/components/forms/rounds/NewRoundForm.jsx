import React, { useState, useContext, useEffect } from 'react';
import {
    Button,
    Card,
    Cascader,
    DatePicker,
    Form,
    Input,
    InputNumber,
    Modal,
    Radio,
    Select,
    Space,
    Switch,
    TreeSelect,
    message,
} from 'antd';
import { AuthContext } from '../../../context/AuthContext';
import { API_BASE_URL } from '../../../Config';
function NewRoundForm({ modalTitle, isModalOpen, setIsModalOpen, circularId }) {
    const [componentSize, setComponentSize] = useState('default');
    const [form] = Form.useForm();
    const [serialNo, setSelectedSerialNo] = useState(null);
    const [roundData, setRoundData] = useState([]);
    const { token } = useContext(AuthContext);
    const onFormLayoutChange = ({ size }) => {
        setComponentSize(size);
    };
    const handleRoundOrderChange = (value) => {
        setSelectedSerialNo(value);
    };
    const handleNewRoundOk = () => {
        form.validateFields().then((values) => {
            const { title, description, maxMark, passMark, requiredAdmitCard } = values;
            const requestData = {
                title,
                description,
                serialNo,
                maxMark,
                passMark,
                requiredAdmitCard,
            };
            console.log(JSON.stringify(requestData));
            fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify(requestData),
            })
                .then((response) => response.json())
                .then((data) => {
                    message.success("Round Added");
                    setIsModalOpen(false);
                })
                .catch((error) => {
                    message.error("Round Creation failed!")
                });

        });
    };
    const handleNewRoundCancel = () => {
        setIsModalOpen(false);
    };
    const { loading, setLoading } = useState(true);
    useEffect(() => {

        fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds', {
            method: 'GET',
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            .then((response) => response.json())
            .then((data) => {
                if (Array.isArray(data)) {
                    const sortedRoundData = data ? [...data].sort((a, b) => a.serialNo - b.serialNo) : null;
                    const optionData = sortedRoundData.slice(0, data.length - 1);
                    setRoundData(optionData);
                } else {
                    console.error('Fetched data is not an array:', data);
                }
            })
            .catch((error) => {
                message.error("Round Creation failed!")
            });
    }, [circularId]);

    return (
        <Modal title={modalTitle}
            open={isModalOpen}
            onCancel={handleNewRoundCancel}
            footer={[
                <Button key="cancel" onClick={handleNewRoundCancel}>
                    Cancel
                </Button>,
                <Button key="submit" type="primary" loading={loading} onClick={handleNewRoundOk}>
                    Save
                </Button>,
            ]}
        >
            <Card>
                <Form
                    form={form}
                    labelCol={{
                        span: 10,
                    }}
                    wrapperCol={{
                        span: 14,
                    }}
                    layout="horizontal"
                    initialValues={{
                        size: componentSize,
                    }}
                    onValuesChange={onFormLayoutChange}
                    size={componentSize}
                >
                    <Form.Item label="Round Name"
                        name="title"
                    >
                        <Input placeholder='round name' />
                    </Form.Item>
                    <Form.Item label="Short Description"
                        name="description"
                    >
                        <Input placeholder='round description' />
                    </Form.Item>
                    <Form.Item label="Round Order"
                    >
                        <Select onChange={handleRoundOrderChange}>
                            {roundData
                                ? roundData.map((option) => (
                                    <Select.Option key={option.serialNo} value={option.serialNo + 1}>
                                        After {option.title}
                                    </Select.Option>
                                ))
                                : null}
                        </Select>
                    </Form.Item>
                    <Space.Compact>
                        <Form.Item label="Marks"
                            maxMark="maxMark"
                        >
                            <InputNumber placeholder='maximum marks' />
                        </Form.Item>
                        <Form.Item
                            passMark="passMark"
                        >
                            <InputNumber placeholder='pass marks' />
                        </Form.Item>
                    </Space.Compact>

                    <Form.Item
                        label="Closing Date"

                    >
                        <DatePicker />
                    </Form.Item>
                    <Form.Item
                        label="Required Admit Card"
                        name="requiredAdmitCard"
                        valuePropName="checked"
                    >
                        <Switch />
                    </Form.Item>
                </Form>
            </Card>
        </Modal>

    );
};
export default NewRoundForm;