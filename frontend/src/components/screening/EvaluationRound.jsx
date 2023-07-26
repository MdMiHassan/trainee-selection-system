import React, { useContext, useEffect, useState } from 'react';
import { Input, Modal, Select, Switch, Table, message } from 'antd';
import { API_BASE_URL } from "../../Config";
import { AuthContext } from '../../context/AuthContext';

const EvaluationRound = ({ circularId, roundId }) => {
    const { token } = useContext(AuthContext);
    const [tableData, setTableData] = useState([]);
    const [selectedRowData, setSelectedRowData] = useState([]);
    const [modalVisible, setModalVisible] = useState(false);
    const [assignModalVisible, setAssignModalVisible] = useState(false);
    const [evaluators, setEvaluators] = useState([]);
    const [selectedEvaluatorId, setSelectedEvaluatorId] = useState(null);
    const [reloadRequired,setReloadRequired]=useState(false);
    const additionalColumns = Object.keys(tableData[0] || {}).filter(key =>
        key !== 'uid' &&
        key !== 'fullName' &&
        key !== 'currentRoundMark' &&
        key !== 'totalMark' &&
        key !== 'key' &&
        key !== 'gender'
    ).map(key => ({
        title: key,
        dataIndex: key,
        key: key,
        width: 20,
        sorter: (a, b) => a[key] - b[key],
    }));
    const columns = [
        {
            title: 'Uniqe Id',
            width: 5,
            dataIndex: 'uid',
            key: 'uid',
        },
        {
            title: 'Full Name',
            width: 25,
            dataIndex: 'fullName',
            key: 'name',
        },
        ...additionalColumns,
        {
            title: 'Total Mark',
            key: 'totalMark',
            fixed: 'right',
            dataIndex: 'totalMark',
            width: 5
        },
        {
            title: 'Evaluator',
            key: 'evaluator',
            fixed: 'right',
            dataIndex: 'evaluator',
            width: 5,
            render: (_, rowData) => (
                <a onClick={() => showAssignModal(rowData)}>Assign</a>
            ),
        },
        {
            title: 'Mark Entry',
            key: 'enterMark',
            fixed: 'right',
            dataIndex: 'enterMark',
            width: 5,
            render: (_, rowData) => (
                <a onClick={() => showModal(rowData)}>Enter Mark</a>
            ),
        },
        {
            title: 'Next Round',
            key: 'operation',
            fixed: 'right',
            width: 5,
            render: (text, record) => (
                <a onClick={() => handleInvite(record.uid)}>Invite</a>
            ),
        },
    ];
    useEffect(() => {
        const fetchData = async () => {
            fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds/' + roundId + '/candidates', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => response.json())
                .then((data) => {
                    console.log(data);
                    const rows = data.map((application) => {
                        const { id, name, gender, roundMarks } = application;
                        let totalMark = 0;
                        const rowMarkData = roundMarks.reduce((acc, marks) => {
                            totalMark += marks.mark;
                            let key = marks.title.replace(/\s/g, "");
                            acc[`${key}`] = marks.mark;
                            return acc;
                        }, {});

                        const rowData = {
                            ...rowMarkData,
                            key: id,
                            totalMark,
                            uid: id,
                            fullName: name,
                            gender: gender,
                        };
                        console.log("Row data " + JSON.stringify(rowData));
                        return rowData;
                    });
                    console.log(rows);
                    setTableData(rows);
                })
                .catch((error) => {
                    message.error("Round Data Fetching Fialed!")
                });
        };
        fetchData();
    }, [reloadRequired]);

    useEffect(() => {
        fetch(API_BASE_URL + '/evaluators', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setEvaluators(data);
            })
            .catch((error) => {
                console.error('Failed to fetch evaluators:', error);
            });
    }, [assignModalVisible]);

    const handleInvite = (applicationId) => {
        fetch(
            API_BASE_URL +
            `/circulars/${circularId}/rounds/next/applications/${applicationId}/actions/invite`,
            {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            }
        )
            .then((response) => {
                if (response.ok) {
                    message.success('Invitation sent successfully.');
                    setReloadRequired(!reloadRequired);
                } else {
                    message.error('Failed to send invitation.');
                }
            })
            .catch((error) => {
                console.error('Error sending invitation:', error);
                message.error('An error occurred while sending the invitation.');
            });
    };
    const handleModalSave = () => {
        if (selectedRowData) {
            const enteredMark = parseFloat(selectedRowData.currentRoundMark);
            fetch(API_BASE_URL + `/circulars/${circularId}/rounds/${roundId}/candidates/${selectedRowData.uid}?mark=${enteredMark}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => {
                    if (response.ok) {
                        setTableData(prevData => {
                            return prevData.map(row => {
                                if (row.key === selectedRowData.key) {
                                    return { ...row, currentRoundMark: enteredMark };
                                }
                                return row;
                            });
                        });
                        setModalVisible(false);
                        setSelectedRowData(null);
                        message.success("Mark Updated successfully");
                        setReloadRequired(!reloadRequired);
                    } else {
                        message.error("Failed to save mark");
                    }

                })
                .catch((error) => {
                    console.log(error);
                    message.error("Failed to save mark");
                });

        }
    };
    const handleModalAssign = () => {
        if (selectedEvaluatorId && selectedRowData) {
            const enteredMark = parseFloat(selectedRowData.currentRoundMark);
            fetch(API_BASE_URL + `/evaluators/${selectedEvaluatorId}/candidates/assign?candidate=${selectedRowData.uid}&round=${roundId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => {
                    if (response.ok) {
                        setTableData(prevData => {
                            return prevData.map(row => {
                                if (row.key === selectedRowData.key) {
                                    return { ...row, currentRoundMark: enteredMark };
                                }
                                return row;
                            });
                        });
                        setModalVisible(false);
                        setSelectedRowData(null);
                        message.success("Evaluator assigned successfully");
                    } else {
                        message.error("Failed to assign evaluator");
                    }

                })
                .catch((error) => {
                    console.log(error);
                    message.error("Failed to save mark");
                });
        }
    }
    const handleModalCancel = () => {
        setModalVisible(false);
        setAssignModalVisible(false);
        setSelectedRowData(null);
    };
    const handleEvaluatorChange = (value) => {
        setSelectedEvaluatorId(value);
    };
    const showAssignModal = (rowData) => {
        setSelectedRowData(rowData);
        setAssignModalVisible(true);
    }
    const showModal = (rowData) => {
        setSelectedRowData(rowData);
        setModalVisible(true);
    };
    return (
        <>
            <Table
                columns={columns}
                dataSource={tableData}
                scroll={{ x: 1500 }}
                sticky
            />
            <Modal
                title="Enter Current Round Mark"
                open={modalVisible}
                onOk={handleModalSave}
                onCancel={handleModalCancel}
                okText={"Save"}
            >
                <Input
                    type="number"
                    value={selectedRowData ? selectedRowData.currentRoundMark : ''}
                    onChange={(e) =>
                        setSelectedRowData((prevData) => ({
                            ...prevData,
                            currentRoundMark: e.target.value,
                        }))
                    }
                />
            </Modal>
            <Modal
                title="Assign Candidate to An Evaluator"
                open={assignModalVisible}
                onOk={handleModalAssign}
                onCancel={handleModalCancel}
                okText="Assign"
            >
                <Select
                    style={{ width: '100%' }}
                    value={selectedEvaluatorId}
                    onChange={handleEvaluatorChange}
                >
                    {evaluators.map((evaluator) => (
                        <Option key={evaluator.id} value={evaluator.id}>
                            {evaluator.email}
                        </Option>
                    ))}
                </Select>
            </Modal>
        </>
    );
};

export default EvaluationRound;