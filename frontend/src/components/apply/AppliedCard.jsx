import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../context/AuthContext";
import { Button, Card, Collapse, Row, Steps, Typography } from "antd";
import JobDescriptionCard from "../../layouts/Jobdescription";
import { API_BASE_URL } from "../../Config";

function AppliedCard({ application }) {
    const [circular, setCircular] = useState(null);
    const [status, setStatus] = useState(false);
    const { token } = useContext(AuthContext);
    const [downloadLink, setDownloadLink] = useState(null);
    const [applicationSteps, setApplicationSteps] = useState([])
    useEffect(() => {
        if (application) {
            fetch(API_BASE_URL + '/circulars/' + application.circularId, {
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

    }, [application]);
    useEffect(() => {
        if (application) {
            console.log(`Authorization Bearer ${token}`);
            fetch(API_BASE_URL + `/circulars/${application.circularId}/rounds`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => response.json())
                .then((data) => {
                    setStatus(application.currentRoundSerialNo <= data.currentRoundSerialNo);
                    const rounds = data.rounds.sort((a, b) => a.serialNo - b.serialNo);
                    setApplicationSteps(rounds);
                    if (application.requiredAdmitCard) {
                        setDownloadLink(`http://localhost:8080/api/v1/admits/verify/${application.currentRoundAdmitId}`);
                    }
                })
                .catch((error) => {
                    message.error("Circular fetching failed")
                });
        }

    }, [application]);
    const handleDownloadAdmit = () => {
        window.open(downloadLink, "_blank");
    };
    console.log(application);
    return (<Card style={{marginBottom:"20px"}}>
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
            current={application.currentRoundSerialNo}
            status={status}
            items={applicationSteps}
        />
        {application.requiredAdmitCard ?
            <Row justify={"end"} align={"middle"} style={{ marginTop: 16 }}>
                <Typography.Text style={{ margin: "0 10px 0 0" }}>
                    Admit Card Available for current round
                </Typography.Text>
                <Button onClick={handleDownloadAdmit}>
                    Download
                </Button>
            </Row> : null}
    </Card>);
}

export default AppliedCard;