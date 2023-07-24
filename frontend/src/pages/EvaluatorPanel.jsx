import { Avatar, Card, Col, Divider, Layout, Row, Select, Space, Typography } from "antd";
import AppHeader from "../layouts/AppHeader";
import { Content } from "antd/es/layout/layout";
import AppFooter from "../layouts/AppFooter";
import AssignedApplicantPanel from "../layouts/evaluator/assignedapplicantpanel";
import { useState } from "react";

function EvaluatorPanel() {
    return (
        <Layout className='applicant-panel'>
            <AppHeader />
            <Content>
                <Row justify={"center"} align={"middle"} style={{ minHeight: "95vh" }}>
                    <Col>
                        <Row style={{ marginTop: "100px" }}>
                            <Col>
                                <Card type="inner" title="Evaluation panel">
                                    <Row style={{ marginBottom: "10px", textAlign: "center" }}>
                                        <Typography.Text style={{ paddingRight: "10px", margin: "0", textAlign: "midle" }}>
                                            You must upload marks of the the following candidates before deadline anounced by administrator!
                                        </Typography.Text>
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