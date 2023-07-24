import { useState } from 'react';
import { Button, Form, Input, Popconfirm, Table } from 'antd';
import MarkModal from './MarkModal';

const AssignedApplicantPanel = () => {
    const [editingKey, setEditingKey] = useState('');
    const [markModalVisible, setMarkModalVisible] = useState(false);
    const [selectedRow, setSelectedRow] = useState(null);
    const [dataSource, setDataSource] = useState(null);
    const defaultColumns = [
        {
            title: '#UniqueId',
            dataIndex: 'uid',
        },
        {
            title: 'Marks',
            dataIndex: 'Marks',
            width: '30%',
            editable: true,
        },
        {
            title: 'Remarks',
            dataIndex: 'remarks',
            width: '30%',
            editable: true,
        },
        {
            title: 'Operation',
            dataIndex: 'operation',
            render: (_, record) =>
                dataSource.length >= 1 ? (
                    <Popconfirm title="Are You Sure to save?" onConfirm={() => handleSaveMark(record.key)}>
                        <a>Save</a>
                    </Popconfirm>
                ) : null,
        },
    ];

    const handleSave = (row) => {
        const newData = [...dataSource];
        const index = newData.findIndex((item) => row.key === item.key);
        const item = newData[index];
        newData.splice(index, 1, {
            ...item,
            ...row,
        });
        setDataSource(newData);
        setEditingKey('');
    };

    const handleSaveMark = async (key) => {
        const newData = dataSource.filter((item) => item.key !== key);
        setDataSource(newData);
        useEffect(() => {
            if (circularId) {
                fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                }
                )
                    .then((response) => response.json())
                    .then((data) => {
                        console.log(data);
                        const fetchedContent = data;
                        const sortedRoundData = fetchedContent ? [...fetchedContent].sort((a, b) => a.serialNo - b.serialNo) : null;
                        setRoundData(sortedRoundData);
                        console.log(sortedRoundData);
                    })
                    .catch((error) => {
                        message.error("Application failed!")
                    });
            }
        }, [circularId]);
    };

    const handleEdit = (record) => {
        setSelectedRow(record);
        setMarkModalVisible(true);
    };

    const handleCancelMarkModal = () => {
        setMarkModalVisible(false);
        setSelectedRow(null);
    };

    const columns = defaultColumns.map((col) => {
        if (!col.editable) {
            return col;
        }
        return {
            ...col,
            onCell: (record) => ({
                record,
                editable: col.editable,
                dataIndex: col.dataIndex,
                title: col.title,
                handleSave,
            }),
            render: (text, record) => {
                const editable = isEditing(record);
                return editable ? (
                    <span>
                        <a href="#" onClick={() => handleSaveMark(record.key)}>
                            Save
                        </a>
                    </span>
                ) : (
                    <div
                        className="editable-cell-value-wrap"
                        style={{ paddingRight: 24 }}
                        onClick={() => handleEdit(record)}
                    >
                        {text}
                    </div>
                );
            },
        };
    });

    return (
        <div>
            <Table
                bordered
                dataSource={dataSource}
                columns={columns}
            />
            {selectedRow && (
                <MarkModal
                    visible={markModalVisible}
                    onCancel={handleCancelMarkModal}
                    onSave={(marks) => {
                        setSelectedRow({ ...selectedRow, marks });
                        setMarkModalVisible(false);
                    }}
                />
            )}
        </div>
    );
};

export default AssignedApplicantPanel;
