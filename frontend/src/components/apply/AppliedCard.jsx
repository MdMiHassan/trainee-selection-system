import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../context/AuthContext";
import { Button, Card, Collapse, Row, Steps, Typography } from "antd";
import JobDescriptionCard from "../../layouts/Jobdescription";
import { API_BASE_URL } from "../../Config";

function AppliedCard({ circularId }) {
    const [circular, setCircular] = useState(null);
    const [status, setStatus] = useState(false);
    const { token } = useContext(AuthContext);
    const [downloadLink, setDownloadLink] = useState(null);
    const apllicationSteps = [
        {
            title: 'Finished',
            description: 'Short Listed',
        },
        {
            title: 'Finished',
            description: 'Written Test',
        },
        {
            title: 'In Process',
            description: 'Aptitude Test',
        },
        {
            title: 'Waiting',
            description: 'Technical Viva',
        },
        {
            title: 'Waiting',
            description: 'CEO Office Viva',
        },
    ];
    useEffect(() => {
        if (circularId) {
            fetch(API_BASE_URL + '/circulars/' + circularId, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => response.json())
                .then((data) => {
                    setCircular(data);
                })
                .catch((error) => {
                    message.error("Circular fetching failed")
                });
        }

    }, [circularId]);
    useEffect(() => {
        if (circularId) {
            console.log(`Authorization Bearer ${token}`);
            fetch(API_BASE_URL + '/circulars/' + circularId + "/meta", {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => response.json())
                .then((data) => {
                    // setStatus(userRound<=data.currentRoundSerialNo)
                })
                .catch((error) => {
                    message.error("Circular fetching failed")
                });
        }

    }, [circularId]);
    useEffect(() => {
        if (circularId) {
            console.log(`Authorization Bearer ${token}`);
            fetch(API_BASE_URL + '/admits/current/' + circularId, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => response.json())
                .then((data) => {
                    setDownloadLink(`${API_BASE_URL}/admits/verify/${data.currentRoundAdmitId}`);
                    // setStatus(userRound<=data.currentRoundSerialNo)
                })
                .catch((error) => {
                    message.error("Circular fetching failed")
                });
        }

    }, [circularId]);
    const handleDownloadAdmit = () => {
        window.open(downloadLink, "_blank");
    };

    return (<Card>
        <Collapse
            bordered={false}
            items={<JobDescriptionCard circular={circular} />}
            style={{ marginBottom: "20px" }}

        >
            <Collapse.Panel header="Training Description">
                <JobDescriptionCard circular={circular} />
            </Collapse.Panel>
        </Collapse>
        <Steps
            // current={userRound.serialNo}
            current={1}
            status={status}
            items={apllicationSteps}
        />
        <Row justify={"end"} align={"middle"} style={{ marginTop: 16 }}>
            <Typography.Text style={{margin:"0 10px 0 0"}}>
                Admit Card Available for current round
            </Typography.Text>
            <Button onClick={handleDownloadAdmit}>
                Download
            </Button>
        </Row>
    </Card>);
}

export default AppliedCard;