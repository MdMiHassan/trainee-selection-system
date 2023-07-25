import { useContext, useEffect, useState } from 'react';
import { Button, Form, Input, Popconfirm, Table, message, Modal } from 'antd';
import { AuthContext } from '../../context/AuthContext';
import { API_BASE_URL } from '../../Config';

const AssignedApplicantPanel = () => {
    const [dataSource, setDataSource] = useState([]);
    const { token } = useContext(AuthContext);
    const [selectedRowData, setSelectedRowData] = useState(null);
    const [modalVisible, setModalVisible] = useState(false);

    const handleModalCancel = () => {
        setModalVisible(false);
        setSelectedRowData(null);
    };

    const showModal = (rowData) => {
        setSelectedRowData(rowData);
        setModalVisible(true);
    };

    const columns = [
        {
            title: '#UniqueId',
            dataIndex: 'candidatesUid',
        },
        {
            title: 'Screening Round',
            dataIndex: 'screeningRound',
            render: (screeningRound) => screeningRound.roundId,
        },
        {
            title: 'Marks',
            dataIndex: 'marks',
            width: '30%',
        },
        {
            title: 'Mark Entry',
            key: 'enterMark',
            fixed: 'right',
            dataIndex: 'enterMark',
            width: 20,
            render: (_, rowData) => (
                <a onClick={() => showModal(rowData)}>Enter Mark</a>
            ),
        },
    ];

    useEffect(() => {
        if (token) {
            fetch(API_BASE_URL + '/evaluators/current/candidates', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
            })
                .then((response) => response.json())
                .then((data) => {
                    if (data && Array.isArray(data) && data.length > 0) {
                        console.log(data);
                        const transformedData = data.map((candidate) => ({
                            key: candidate.candidatesUid,
                            candidatesUid: candidate.candidatesUid,
                            screeningRound: candidate.screeningRound,
                            marks: candidate.mark,
                        }));
                        setDataSource(transformedData);
                    }
                })
                .catch((error) => {
                    message.error('Application failed!');
                });
        }
    }, [token]);

    const handleModalSave = () => {
        if (selectedRowData) {
            const enteredMark = parseFloat(selectedRowData.marks);
            const requestData = {
                candidateUid: selectedRowData.candidatesUid,
                totalMarks: enteredMark,
            };
            console.log(JSON.stringify(requestData));
            fetch(API_BASE_URL + '/evaluators/current/candidates/marks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(requestData),
            })
                .then((response) => {
                    if (response.ok) {
                        setDataSource((prevData) => {
                            return prevData.map((row) => {
                                if (row.key === selectedRowData.key) {
                                    return { ...row, marks: enteredMark }; 
                                }
                                return row;
                            });
                        });
                        setModalVisible(false);
                        setSelectedRowData(null);
                        message.success('Mark Updated successfully');
                    } else {
                        console.log(response);
                        message.error('Failed to save mark');
                    }
                })
                .catch((error) => {
                    message.error('Mark submission failed!');
                });
        }
    };

    return (
        <div>
            <Table bordered dataSource={dataSource} columns={columns} />
            <Modal
                title="Please enter total marks"
                open={modalVisible} 
                onOk={handleModalSave}
                onCancel={handleModalCancel}
                okText="Save"
            >
                <Input
                    type="number"
                    value={selectedRowData ? selectedRowData.marks : ''}
                    onChange={(e) =>
                        setSelectedRowData((prevData) => ({
                            ...prevData,
                            marks: e.target.value,
                        }))
                    }
                />
            </Modal>
        </div>
    );
};

export default AssignedApplicantPanel;
