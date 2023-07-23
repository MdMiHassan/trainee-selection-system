

import React, { useContext, useEffect, useState } from 'react';
import { Input, Modal, Switch, Table, message } from 'antd';
import { API_BASE_URL } from "../../Config";
import { AuthContext } from '../../context/AuthContext';


const EvaluationRound = ({ circularId, roundId }) => {
    const [rowId, setRowId] = useState(0);
    const [tableData, setTableData] = useState([]);
    const [selectedRowData, setSelectedRowData] = useState([]);
    const { token } = useContext(AuthContext);
    const [modalVisible, setModalVisible] = useState(false);
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
                            acc[`id_${marks.roundId}`] = marks.mark;
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
                        console.log(rowData);
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
    }, []);
    const additionalColumns = Object.keys(tableData[0] || {}).filter(key => key !== 'uid' && key !== 'fullName' && key !== 'currentRoundMark' && key !== 'totalMark' && key !== 'key' && key !== 'gender').map(key => ({
        title: key,
        dataIndex: key,
        key: key,
        width: 20,
    }));
    const columns = [
        {
            title: 'Uniqe Id',
            width: 100,
            dataIndex: 'uid',
            key: 'uid',
            fixed: 'left',
        },
        {
            title: 'Full Name',
            width: 100,
            dataIndex: 'fullName',
            key: 'name',
            fixed: 'left',
        },
        ...additionalColumns,
        {
            title: 'Current Round Mark',
            key: 'currentRoundMark',
            fixed: 'right',
            dataIndex: 'currentRoundMark',
            width: 20,
        },
        {
            title: 'Total Mark',
            key: 'totalMark',
            fixed: 'right',
            dataIndex: 'totalMark',
            width: 20
        },
        {
            title: 'Action',
            key: 'enterMark',
            fixed: 'right',
            dataIndex: 'enterMark',
            width: 20,
            render: (_, rowData) => (
                <a onClick={() => showModal(rowData)}>Enter Mark</a>
            ),
        },
        {
            title: 'Action',
            key: 'operation',
            fixed: 'right',
            width: 20,
            render: (text, record) => (
                <a onClick={() => handleInvite(record.uid)}>Invite</a>
            ),
        },
    ];
    const showModal = (rowData) => {
        setSelectedRowData(rowData);
        setModalVisible(true);
    };
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
                    if(response.ok){
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
                    }else{
                        message.error("Failed to save mark");
                    }
                    
                })
                .catch((error) => {
                    console.log(error);
                    message.error("Failed to save mark");
                });

        }
    };

    const handleModalCancel = () => {
        setModalVisible(false);
        setSelectedRowData(null);
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
                visible={modalVisible}
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
        </>
    );
};

export default EvaluationRound;