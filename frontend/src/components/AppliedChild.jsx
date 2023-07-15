import { Col, Row, Steps } from 'antd';
import JobDescriptionCard from '../layouts/Jobdescription';
function AppliedChild() {
    const description = 'This is a description';
    return (
        <>
            <Row justify='center' style={{padding:'20px 0px'}}>
                <Col xs={22} sm={20} md={20} lg={20} xl={16} xxl={16}>
                    <Steps
                        current={1}
                        status="error"
                        items={[
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
                        ]}
                    />
                </Col>
            </Row>
            <JobDescriptionCard />
        </>

    );
}

export default AppliedChild;