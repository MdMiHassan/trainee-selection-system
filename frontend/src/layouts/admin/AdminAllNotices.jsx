

import { Table } from "antd";
import React, { useEffect, useState } from "react";
import '../../styles/AdminAllCirculars.css'
import { API_BASE_URL } from "../../Config";
const columns = [
    { title: 'Id', dataIndex: 'id', key: 'id' },
    { title: 'Title', dataIndex: 'title', key: 'title' },
    { title: 'Details', dataIndex: 'details', key: 'details' },
    { title: 'Updated At', dataIndex: 'updatedAt', key: 'updatedAt' },
]

function AdminAllNotices() {
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                fetch(API_BASE_URL + '/v1/notices')
                    .then((response) => response.json())
                    .then((data) => {
                        const tableRows = data.content;
                        setTableData(tableRows);
                        setLoading(false);
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);
    // const columns = tableData.length > 0 ? Object.keys(tableData[0]).map((key) => ({
    //     title: key,
    //     dataIndex: key,
    //     key,
    //     ellipsis: true, // Enable text truncation
    //   })) : [];
    return (
        <Table
        columns={columns}
            dataSource={tableData}
            rowSelection={{}}
            pagination={{
                pageSize: 10,
            }}
            rowKey={(record) => record.id}
            loading={loading}
            tableLayout='auto'
            className="custom-table"
            expandable={{
                expandedRowRender: (record) => (
                    <p
                        style={{
                            margin: 0,
                        }}
                    >
                        {record.overview}
                    </p>
                ),
            }}
        />

    );
}

export default AdminAllNotices;