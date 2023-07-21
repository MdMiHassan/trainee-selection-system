import { EditFilled } from "@ant-design/icons";
import { Card, Row, Switch, Typography } from "antd";
import { useEffect, useState } from "react";
import { API_BASE_URL } from "../../Config";

function CircularRounds({ circularId }) {
    const [roundData, setRoundData] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds');
                const data = await response.json();
                const fetchedContent=data.content;
                    const sortedRoundData = fetchedContent ? [...fetchedContent].sort((a, b) => a.serialNo - b.serialNo) : null;
                    setRoundData(sortedRoundData);
                
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);
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