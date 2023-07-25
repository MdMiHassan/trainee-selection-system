import { Row, Col, Collapse, Card, Steps } from "antd";
import { useContext, useEffect, useState } from "react";
import "../styles/Applied.css"
import { AuthContext } from "../context/AuthContext";
import AppliedCard from "./apply/AppliedCard";
import { API_BASE_URL } from "../Config";
const application = null;

function Applied() {
    const { token } = useContext(AuthContext);
    const [appliedCircularsId, setAppliedCircularsId] = useState([]);
    useEffect(() => {
        if (token) {
            console.log(`Athorization Bearer ${token}`)
            fetch(API_BASE_URL + '/applicants/current/applications', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
            })
                .then((response) => response.json())
                .then((data) => {
                    setAppliedCircularsId(data.circularIds);
                })
                .catch((error) => {
                    message.error("Circular fetching failed")
                });
        }

    }, []);

    return (
        <Row justify='center' style={{ marginBottom: '25px' }}>
            <Col xs={22} sm={20} md={20} lg={20} xl={18} xxl={16}>
                {appliedCircularsId ? appliedCircularsId.map((circularId) => (
                    <AppliedCard key={circularId} circularId={circularId} />
                )) :
                    <Row justify={"center"}>
                        <Typography.Text>
                            Nothing To Show for Now!
                        </Typography.Text>
                    </Row>}
            </Col>
        </Row>
    );
}

export default Applied;