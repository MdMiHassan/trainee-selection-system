import { Col, Row, Select, Typography } from "antd";
import { API_BASE_URL } from "../../Config";
import { useEffect, useState } from "react";
import CircularUpdateForm from "../../components/circular/CircularUpdateForm";
function AdminCircularUpdate() {
    const [circularId, setCircularId] = useState(null);
    const [circularsOptions, setCircularsOptions] = useState([]);
    const handleChangeCircularSelect = (value) => {
        console.log(value);
        setCircularId(value);
    };
    useEffect(() => {
        fetch(API_BASE_URL + '/circulars', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => response.json())
            .then((data) => {

                const newData = data.content;
                const sortedCircularData = newData
                    ? [...newData].sort((a, b) => b.id - a.id)
                    : [];

                const options = sortedCircularData.map((circular) => ({
                    value: circular.id,
                    label: circular.title,
                }));
                console.log(options);
                setCircularsOptions(options);
            })
            .catch((error) => {
                message.error("Circular fetching failed");
            });

    }, [circularId]);

    return (
        <>
            <Row justify={"space-between"} align={"middle"} style={{ margin: "10px 0" }}>
                <Col span={6}>
                    <Typography.Title
                        level={3}
                        style={{ margin: '0' }}
                    >
                        Select a circular
                    </Typography.Title>
                </Col>
                <Col span={18}>
                    <Select
                        showSearch
                        style={{
                            width: '100%',
                        }}
                        placeholder="Select a circular"
                        onChange={handleChangeCircularSelect}
                        options={circularsOptions}
                    />
                </Col>
            </Row>
            {circularId ? <CircularUpdateForm circularId={circularId} /> :
                <Row justify={"center"}>
                    <Typography.Text style={{marginTop:"100px"}}>
                        Please Select a circular for updating
                    </Typography.Text>
                </Row>}
        </>);
}

export default AdminCircularUpdate;