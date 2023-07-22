import { EditFilled } from "@ant-design/icons";
import { Card, Row, Switch, Typography, message } from "antd";
import { useContext, useEffect, useState } from "react";
import { API_BASE_URL } from "../../Config";
import { AuthContext } from "../../context/AuthContext";

function CircularRounds({ circularId }) {
    const [roundData, setRoundData] = useState([]);
    const {token}=useContext(AuthContext);
    useEffect(() => {
        if (circularId) {
            fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds',{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization':`Bearer ${token}`
            }}
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
    return (
        <>
            {roundData ? (
                roundData.map((option) => (
                    <Card
                        key={option.serialNo}
                        style={{
                            width: 300,
                            margin: "16px",
                        }}
                        actions={[
                            <EditFilled key="editround" />,
                            <Switch defaultChecked />
                        ]}
                    >
                        <Row justify='center'>
                            <Typography.Title level={5}>{option.title}</Typography.Title>
                        </Row>
                    </Card>
                ))
            ) : null}
        </>
    );
}

export default CircularRounds;