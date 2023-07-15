import { Row, Col, Collapse } from "antd";
import AppliedChild from "./AppliedChild";

const items = [
    {
        key: '1',
        label: 'Software Engineer Trainee',
        children: <AppliedChild />,
    },
    {
        key: '2',
        label: 'Depops Engineer Trainee',
        children: <AppliedChild />,
    },
    {
        key: '3',
        label: 'Mern Stack Trainee',
        children: <AppliedChild />,
    },
];
function Applied() {
    return (
        <Row justify='center' style={{marginBottom:'25px'}}>
            <Col xs={22} sm={20} md={20} lg={20} xl={18} xxl={16}>
                <Collapse items={items} />
            </Col>
        </Row>
    );
}

export default Applied;