import React, { useState } from 'react';
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
} from 'antd';
function NewRoundForm({ modalTitle,isModalOpen, setIsModalOpen, circularId }) {
  const [componentSize, setComponentSize] = useState('default');
  const onFormLayoutChange = ({ size }) => {
    setComponentSize(size);
  };
  const handleNewRoundOk = () => {
    setIsModalOpen(false);
  };
  const handleNewRoundCancel = () => {
    setIsModalOpen(false);
  };
  const { loading, setLoading } = useState(true);
  return (
    <Modal title={modalTitle}
      open={isModalOpen}
      onOk={handleNewRoundOk} onCancel={handleNewRoundCancel}
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
          style={{textAlign:"left"}}
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
          <Form.Item label="Round Name">
            <Input placeholder='round name'/>
          </Form.Item>
          <Form.Item label="Short Description">
            <Input placeholder='round description'/>
          </Form.Item>
          <Form.Item label="Round Order">
            <Select>
              <Select.Option value="1">After 1</Select.Option>
              <Select.Option value="2">After 2</Select.Option>
              <Select.Option value="3">After 3</Select.Option>
              <Select.Option value="4">After 4</Select.Option>
            </Select>
          </Form.Item>
          <Space.Compact>
          <Form.Item label="Max Mark">
            <InputNumber />
          </Form.Item>
          <Form.Item label="Pass Mark">
            <InputNumber />
          </Form.Item>
          </Space.Compact>
         
          <Form.Item label="Closing Date">
            <DatePicker />
          </Form.Item>
        </Form>
      </Card>
    </Modal>

  );
};
export default NewRoundForm;