import { Col, Row, Steps } from 'antd';
import JobDescriptionCard from '../layouts/Jobdescription';
import { useState } from 'react';
function AppliedChild({application}) {
    const description = 'This is a description';
    const {status,setStatus}=useState("error");
    const {currentRound,setCurrentRound}=useState(1);
    const apllicationSteps=[
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
    return (
        
        <>
            <Row justify='center' style={{padding:'20px 0px'}}>
                <Col xs={22} sm={20} md={20} lg={20} xl={16} xxl={16}>
                    <Steps
                        current={currentRound}
                        status={status}
                        items={apllicationSteps}
                    />
                </Col>
            </Row>
            <JobDescriptionCard />
        </>

    );
}

export default AppliedChild;