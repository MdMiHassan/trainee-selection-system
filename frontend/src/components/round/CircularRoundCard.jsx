import {  Card,Row, Typography } from "antd";

function CircularRoundCard({ roundinfo, currentRoundSerialNo, circularId }) {
    return (
        <Card
            key={roundinfo.serialNo}
            style={roundinfo.serialNo == currentRoundSerialNo ? {
                backgroundColor: "#0e153a", width: 300,
                margin: "16px",
            } : {
                width: 300,
                margin: "16px",
            }}
        >
            <Row justify='center'>
                <Typography.Title level={5}>{roundinfo.title}{roundinfo.serialNo == currentRoundSerialNo ? <Row justify={"center"}><Typography.Text>Current Round</Typography.Text></Row> : ""}</Typography.Title>
            </Row>
        </Card>
    );
}

export default CircularRoundCard;