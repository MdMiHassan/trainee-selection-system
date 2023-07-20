
import React, { useEffect, useState } from 'react';
import { Table } from 'antd';

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
                text: 'Joe',
                value: 'Joe',
            },
            {
                text: 'Category 1',
                value: 'Category 1',
                children: [
                    {
                        text: 'Yellow',
                        value: 'Yellow',
                    },
                    {
                        text: 'Pink',
                        value: 'Pink',
                    },
                ],
            },
            {
                text: 'Category 2',
                value: 'Category 2',
                children: [
                    {
                        text: 'Green',
                        value: 'Green',
                    },
                    {
                        text: 'Black',
                        value: 'Black',
                    },
                ],
            },
        ],
        filterMode: 'tree',
        filterSearch: true,
        onFilter: (value, record) => record.name.includes(value),
        width: '30%',
    },
    {
        title: 'Degreename',
        dataIndex: 'degreeName',
        sorter: (a, b) => a.age - b.age,
    },
    {
        title: 'CGPA',
        dataIndex: 'cgpa',
        filters: [
            {
                text: 'London',
                value: 'London',
            },
            {
                text: 'New York',
                value: 'New York',
            },

        ],
        onFilter: (value, record) => record.address.startsWith(value),
        filterSearch: true,
        width: '40%',
    },
    {
        title: 'Passing Year',
        dataIndex: 'passingYear',
        filters: [
            {
                text: 'London',
                value: 'London',
            },
            {
                text: 'New York',
                value: 'New York',
            },
        ],
        onFilter: (value, record) => record.address.startsWith(value),
        filterSearch: true,
        width: '40%',
    },
    {
        title: 'Resume',
        dataIndex: '',
        key: 'x',
        render: () => <a>View</a>,
      },
    {
        title: 'Action',
        dataIndex: '',
        key: 'x',
        render: () => <a>Invite</a>,
      }
];
const onChange = (pagination, filters, sorter, extra) => {
    console.log('params', pagination, filters, sorter, extra);
};
function ScreeningRoundWithMaks() {
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                // fetch(API_BASE_URL + "/circulars/"+circularId+"/rounds/"+roundId+"/candidates")
                //     .then((response) => response.json())
                //     .then((data) => {
                //         const tableRows = data;
                //         console.log(tableRows)
                //         setTableData(tableRows);
                //         setLoading(false);
                        
                //     })
                //     .catch((error) => {
                //         console.log(error);
                //     });
            } catch (error) {
                setLoading(false);
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);
    return (<Table loading={loading} columns={columns} dataSource={tableData} onChange={onChange} bordered/>);
}

export default ScreeningRoundWithMaks;