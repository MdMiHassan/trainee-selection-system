import { Avatar, Card, Col, Divider, Layout, Row, Select, Space, Typography } from "antd";
import AppHeader from "../layouts/AppHeader";
import { Content } from "antd/es/layout/layout";
import AppFooter from "../layouts/AppFooter";
import AssignedApplicantPanel from "../layouts/evaluator/assignedapplicantpanel";
import { useState } from "react";

function EvaluatorPanel() {
    const { assingedCirculars, setAssignedCircular } = useState([
        {
            value: '1',
            label: 'Not Identified',
        },
        {
            value: '2',
            label: 'Closed',
        },
        {
            value: '3',
            label: 'Communicated',
        },
        {
            value: '4',
            label: 'Identified',
        },
        {
            value: '5',
            label: 'Resolved',
        },
        {
            value: '6',
            label: 'Cancelled',
        },
    ]);
    return (
        <Layout className='applicant-panel'>
            <AppHeader />
            <Content>
                <Row justify={"center"} align={"middle"} style={{ minHeight: "95vh" }}>
                    <Col>
                        <Row style={{ marginTop: "100px" }}>
                            <Col>
                                <Card type="inner" title="Evalutors Informations" style={{ marginRight: "30px" }}>
                                    <Col align="middle" justify="center" >
                                        <Avatar shape="square" size={84} icon={<img />} />
                                        <Typography.Title
                                            level={5}
                                            style={{ marginBottom: "0" }}
                                        >
                                            Sakib Al Hassan
                                        </Typography.Title>
                                        <Typography.Text>
                                            Sr. Software Engineer (Java)
                                        </Typography.Text>
                                        <Divider />
                                        <Typography.Text>
                                            EMP ID: 41414124
                                        </Typography.Text>
                                    </Col>
                                </Card>
                            </Col>
                            <Col>
                                <Card type="inner" title="Evaluation panel" extra={<a href="#">More</a>}>
                                    <Typography.Text>
                                        Evaluation Reamining: | Ending Soon: Devops Engineer
                                    </Typography.Text>
                                    <Divider></Divider>
                                    <Row style={{marginBottom:"10px",textAlign:"center"}}>
                                        <Typography.Text style={{paddingRight:"10px",margin:"0" ,textAlign:"midle"}}>
                                            Select A circular 
                                        </Typography.Text>
                                        <Select
                                            showSearch
                                            style={{ width: 200 }}
                                            placeholder="Search to Select"
                                            optionFilterProp="children"
                                            filterOption={(input, option) => (option?.label ?? '').includes(input)}
                                            filterSort={(optionA, optionB) =>
                                                (optionA?.label ?? '').toLowerCase().localeCompare((optionB?.label ?? '').toLowerCase())
                                            }
                                            options={assingedCirculars}
                                        />
                                    </Row>
                                    <AssignedApplicantPanel />
                                </Card>
                            </Col>
                        </Row>
                    </Col>
                </Row>
            </Content>
            <AppFooter />
        </Layout>
    );
}

export default EvaluatorPanel;