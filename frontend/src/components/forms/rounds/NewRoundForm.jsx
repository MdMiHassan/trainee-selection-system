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
                    message.success("Round Saved");
                    setIsModalOpen(false);
                })
                .catch((error) => {
                    message.error("Something Went Wrong!")
                });

        });
    };
    const handleNewRoundCancel = () => {
        setIsModalOpen(false);
    };
    const { loading, setLoading } = useState(true);
    useEffect(() => {
        if (circularId) {
            fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds', {
                method: 'GET',
                headers: {
                    "Authorization": "Bearer " + token
                }
            })
                .then((response) => response.json())
                .then((data) => {
                    const rounds = data.rounds;
                    const sortedRoundData = rounds ? [...rounds].sort((a, b) => a.serialNo - b.serialNo) : null;
                    const optionData = sortedRoundData.slice(0, rounds.length - 1);
                    setRoundData(optionData);
                })
                .catch((error) => {
                    message.error("Something went wrong!")
                });
        }
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

                    layout="vertical"
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
                        <Form.Item
                            label="Max Mark"
                            maxMark="maxMark"
                        >
                            <InputNumber placeholder='maximum marks' />
                        </Form.Item>
                        <Form.Item
                            label="Pass Mark"
                            passMark="passMark"
                        >
                            <InputNumber placeholder='pass marks' />
                        </Form.Item>
                        <Form.Item
                            label="Closing Date"

                        >
                            <DatePicker />
                        </Form.Item>
                        <Form.Item
                            label="Admit"
                            name="requiredAdmitCard"
                            valuePropName="checked"
                            style={{marginLeft:"15px"}}
                        >
                            <Switch />
                        </Form.Item>
                    </Space.Compact>
                </Form>
            </Card>
        </Modal >

    );
};
export default NewRoundForm;