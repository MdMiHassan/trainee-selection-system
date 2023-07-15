import { Card } from 'antd';
import Openings from './Openings';
import '../styles/openingslisting.css'
import { Row, Col } from 'antd';
function OpeningsListing() {
    return (
        <Row justify='center'>
            <Col xs={22} sm={20} md={20} lg={20} xl={18} xxl={16}>
                <Openings />
                <Openings />
                <Openings />
                <Openings />
            </Col>
        </Row>
    );
}

export default OpeningsListing;