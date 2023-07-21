import { Layout, Row, Col, Card } from 'antd';
import AppFooter from "../layouts/AppFooter";
import AppHeader from "../layouts/AppHeader";
import ApplicantActivity from "../layouts/Applicant/ApplicantActivity";
import ApplicantHero from "../layouts/Applicant/ApplicantHero";
import '../styles/ApplicantPanel.css'
const { Content } = Layout;

function ApplicantPanel() {
  return (
    <Layout className='applicant-panel'>
      <AppHeader/>
      <Content>
        <Row gutter={[16, 16]}>
          <Col xs={24}>
            <ApplicantHero />
          </Col>
          <Col xs={24}>
            <Card style={{minHeight:"300px"}}>
            <ApplicantActivity />
            </Card>
          </Col>
        </Row>
      </Content>
      <AppFooter />
    </Layout>
  );
}

export default ApplicantPanel;
