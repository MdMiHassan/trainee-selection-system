import { Avatar, Card, Col, Layout, Row, Space, Typography } from "antd";
import AppHeader from "../layouts/AppHeader";
import { Content } from "antd/es/layout/layout";
import AppFooter from "../layouts/AppFooter";

function EvaluatorPanel() {
    return (
        <Layout className='applicant-panel'>
            <AppHeader />
            <Content>
                <Row justify={"center"}>
                    <Col>
                        <Card
                            style={{
                                width: 300,
                            }}
                        >
                            <Space wrap size={16}>
                                <Avatar shape="square" size={64} icon={<img  />} />
                            </Space>
                            <Typography.Title
                        </Card>
                    </Col>
                </Row>
            </Content>
            <AppFooter />
        </Layout>
    );
}

export default EvaluatorPanel;