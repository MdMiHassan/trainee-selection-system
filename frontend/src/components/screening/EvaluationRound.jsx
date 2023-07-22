

import React, { useEffect, useState } from 'react';
import { Switch, Table } from 'antd';
import { API_BASE_URL } from "../../Config";
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
        dataIndex: 'name',
        key: 'name',
        fixed: 'left',
    },
    {
        title: 'Column 1',
        dataIndex: 'address',
        key: '1',
        width: 150,
    },
    {
        title: 'Column 2',
        dataIndex: 'address',
        key: '2',
        width: 150,
    },
    {
        title: 'Column 3',
        dataIndex: 'address',
        key: '3',
        width: 150,
    },
    {
        title: 'Column 4',
        dataIndex: 'address',
        key: '4',
        width: 150,
    },
    {
        title: 'Column 5',
        dataIndex: 'address',
        key: '5',
        width: 150,
    },
    {
        title: 'Column 6',
        dataIndex: 'address',
        key: '6',
        width: 150,
    },
    {
        title: 'Column 7',
        dataIndex: 'address',
        key: '7',
        width: 150,
    },
    {
        title: 'Column 8',
        dataIndex: 'address',
        key: '8',
    },
    {
        title: 'Action',
        key: 'operation',
        fixed: 'right',
        width: 100,
        render: () => <a>Invite</a>,
    },
];
const data = [];
for (let i = 0; i < 100; i++) {
    data.push({
        key: i,
        name: `Edward ${i}`,
        age: 32,
        address: `London Park no. ${i}`,
    });
}
const EvaluationRound = ({ circularId, roundId }) => {
    const [fixedTop, setFixedTop] = useState(false);
    //   /{circularId}/rounds/{roundId}/candidates
    const [tableData,setTableData]=useState([]);
    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await fetch(API_BASE_URL + '/' + circularId + '/rounds/' + roundId + '/candidates');
            const applications = await response.json();
            const rows = applications.map((application) => {
              const { id, firstName, lastName, gender, roundMarks } = application;
      
              // Create an object with the application data and include round_id properties with corresponding marks
              const rowData = roundMarks.reduce((acc, round, index) => {
                acc[`round${index + 1}_id`] = round.roundId;
                acc[`round${index + 1}_mark`] = round.mark;
                return acc;
              }, {
                applicationId: id,
                fullName: firstName + " " + lastName,
                gender: gender,
              });
      
              return rowData;
            });
            console.log(rows);
            setTableData(rows);
          } catch (error) {
            console.error("Error fetching data:", error);
          }
        };
        fetchData();
      }, []);

    return (
        <Table
            columns={columns}
            dataSource={tableData}
            scroll={{
                x: 1500,
            }}
            summary={() => (
                <Table.Summary fixed={fixedTop ? 'top' : 'bottom'}>
                   
                </Table.Summary>
            )}
            sticky
        />
    );
};
export default EvaluationRound;