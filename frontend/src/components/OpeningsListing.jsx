import { Card } from 'antd';
import Openings from './Openings';
import '../styles/openingslisting.css'
import { Row, Col } from 'antd';
function OpeningsListing() {
    return (
        <Row justify='center'>
            <Col xs={22} sm={20} md={20} lg={20} xl={18} xxl={16}>
                <Openings title="Software Engineer Trainee" type={"full time"} closing={"3 June 2024"} vacency={20}/>
            </Col>
        </Row>
    );
}

export default OpeningsListing;