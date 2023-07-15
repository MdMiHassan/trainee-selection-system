import { Input, Button, Typography, Row, Col } from 'antd';
import '../styles/apphero.css';
const { Title, Paragraph } = Typography;

const AppHero = () => {
  return (
    <div className='hero'>
      <div className='hero-container'>
        <div className='hero-mission'>
          <Row justify='center'>
            <Col xs={20} sm={20} md={16} lg={12} xl={10} xxl={8}>
              <Title level={1} style={{ textAlign: 'center' }}>
                Gain Insights from Industry Experts: Elevate Your Learning Experience
              </Title>
              <Paragraph style={{ textAlign: 'center' }}>
                We offer globally recognized training programs adhering to the highest industry standards.
                Stay informed about the latest opportunities in the field by subscribing to our newsletter.
              </Paragraph>
            </Col>
          </Row>
        </div>

        <div className='news-letter'>
          <Row justify='center'>
            <Col xs={18} sm={16} md={12} lg={8} xl={6} xxl={4}>
              <Input type='email' name='newsletteremail' placeholder='Your email address' required />
            </Col>
            <Col>
              <Button type='primary' shape='default' icon={<i className='fa-light fa-paper-plane'></i>} />
            </Col>
          </Row>
        </div>
      </div>
    </div>
  );
};

export default AppHero;
