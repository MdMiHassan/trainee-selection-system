import { Col, DatePicker, Row, Typography } from "antd";
function DashBoard() {
    const currentDate = new Date();
    return (
        <Row justify={"center"} align={"middle"}>
            <Col>
                <Typography.Title>
                    Welcome to Trainee Selection System
                </Typography.Title>
                <Row justify={"center"} align={"middle"}>
                    <Typography.Text>
                        {currentDate.toUTCString()}
                    </Typography.Text>
                </Row>
            </Col>
        </Row>
    );
}

export default DashBoard;