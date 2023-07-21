import { Row, Col, Collapse, Card, Steps } from "antd";
import AppliedChild from "./AppliedChild";
import JobDescriptionCard from "../layouts/Jobdescription";
import { useState } from "react";
import "../styles/Applied.css"
const application = null;
const items = [
    {
        key: '1',
        label: 'Software Engineer Trainee',
        children: <JobDescriptionCard application={application} />,
    }
];
function Applied() {
    const description = 'This is a description';
    const { status, setStatus } = useState("error");
    const { currentRound, setCurrentRound } = useState(1);
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
    // <Col xs={22} sm={20} md={20} lg={20} xl={18} xxl={16}>
    //             <Collapse 
    //             bordered={false}
    //             items={items} />
    //         </Col>
    return (
        <Row justify='center' style={{ marginBottom: '25px' }}>
            <Col xs={22} sm={20} md={20} lg={20} xl={18} xxl={16}>
                <Card>
                    <Collapse
                        bordered={false}
                        items={items} style={{marginBottom:"20px"}}/>
                    <Steps
                        current={currentRound}
                        status={status}
                        items={apllicationSteps}
                    />
                </Card>
            </Col>
        </Row>
    );
}

export default Applied;