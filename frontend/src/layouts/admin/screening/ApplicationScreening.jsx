
import React, { useContext, useEffect, useState } from 'react';
import { Table, message } from 'antd';
import { API_BASE_URL } from '../../../Config';
import { AuthContext } from '../../../context/AuthContext';


function ApplicationScreening({ circularId, roundId }) {
    const { token } = useContext(AuthContext);
    const columns = [
        {
            title: 'Application Id',
            dataIndex: 'id',
        },
        {
            title: 'Applicant Name',
            dataIndex: 'name',
        },
        {
            title: 'Gender',
            dataIndex: 'gender',
            filters: [
                {
                    text: 'Male',
                    value: 'MALE',
                },
                {
                    text: 'Female',
                    value: 'FEMALE',
                },
                {
                    text: 'Other',
                    value: 'OTHER',
                },
            ],
            filterMode: 'tree',
            filterSearch: true,
            onFilter: (value, record) => record.gender.includes(value),
        },
        {
            title: 'Degreename',
            dataIndex: 'degreeName',
        },
        {
            title: 'Institution',
            dataIndex: 'institutionName',
            sorter: (a, b) => a.institutionName.localeCompare(b.institutionName)
        },
        {
            title: 'CGPA',
            dataIndex: 'cgpa',
            sorter: (a, b) => a.cgpa - b.cgpa,
        },
        {
            title: 'Passing Year',
            dataIndex: 'passingYear',
            sorter: (a, b) => a.passingYear - b.passingYear,
        },
        {
            title: 'Resume',
            dataIndex: '',
            key: 'x',
            render: (text, record) => <a onClick={() => handleViewResume(record.resumeId)}>View</a>,
        },
        {
            title: 'Action',
            dataIndex: '',
            key: 'x',
            render: (text, record) => (
                <a onClick={() => handleInvite(record.id)}>Invite</a>
            ),
        }
    ];
    const handleViewResume = (resumeId) => {
        window.open(`${API_BASE_URL}/resource/${resumeId}`, "_blank");
    }
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
    const onChange = (pagination, filters, sorter, extra) => {
        console.log('params', pagination, filters, sorter, extra);
    };
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        if (circularId && roundId) {
            fetch(API_BASE_URL + "/circulars/" + circularId + "/rounds/" + roundId + "/candidates", {
                headers: { 'Authorization': `Bearer ${token}` }
            })
                .then((response) => response.json())
                .then((data) => {
                    const tableRows = data;
                    console.log(circularId + " " + roundId);
                    console.log("Table rows round application filtering" + tableRows)
                    console.log(data)
                    setTableData(tableRows);
                    setLoading(false);

                })
                .catch((error) => {
                    console.log(error);
                });
        }
    }, [circularId, roundId]);
    return (<Table loading={loading} columns={columns} dataSource={tableData} onChange={onChange} bordered />);
}

export default ApplicationScreening;