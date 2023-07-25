import { useState } from 'react';
import { Modal, Input } from 'antd';

const MarkModal = ({ visible, onCancel, onSave }) => {
    const [marks, setMarks] = useState('');

    const handleSave = () => {
        onSave(marks);
    };

    return (
        <Modal
            visible={visible}
            onCancel={onCancel}
            onOk={handleSave}
            title="Enter Marks"
        >
            <Input
                value={marks}
                onChange={(e) => setMarks(e.target.value)}
                placeholder="Enter marks here"
            />
        </Modal>
    );
};

export default MarkModal;
