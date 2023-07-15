import { Layout, Row, Col, Typography } from 'antd';

import '../styles/AppFooter.css'
const { Footer } = Layout;
const { Title, Text } = Typography;

const AppFooter = () => {
  return (
    <Footer className="footer-section" style={{ padding: 0 }}>
      {/* <div className="container">
        <Row gutter={[16, 16]} justify="space-around" align="middle">
          <Col>
            <div className="single-cta">
              <i class="fas fa-map-marker-alt"></i>
              <div className="cta-text">
                <Title level={4} style={{ marginTop: '0' }}>Find us</Title>
                <Text>Block # J, Baridhara, Badda, Dhaka-1212</Text>
              </div>
            </div>
          </Col>
          <Col>
            <div className="single-cta">
              <i class="fas fa-phone"></i>
              <div className="cta-text">
                <Title level={4} style={{ marginTop: '0' }}>Call us</Title>
                <Text>01776730842</Text>
              </div>
            </div>
          </Col>
          <Col>
            <div className="single-cta">
              <i class="far fa-envelope-open"></i>
              <div className="cta-text">
                <Title level={4} style={{ marginTop: '0' }}>Mail us</Title>
                <Text>mail@info.com</Text>
              </div>
            </div>
          </Col>
        </Row>
      </div> */}
      <div className="copyright-area">
        <div className="container">
          <Row justify="center" align="middle">
            <Col>
              <div className="copyright-text">
                <p>
                  Copyright &copy; 2023, All Right Reserved{' '}
                  <a href="">BJIT ACADEMY</a>
                </p>
              </div>
            </Col>
          </Row>
        </div>
      </div>
    </Footer>
  );
};

export default AppFooter;
