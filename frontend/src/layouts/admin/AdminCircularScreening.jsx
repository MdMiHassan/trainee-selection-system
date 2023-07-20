import { EditOutlined, EllipsisOutlined, PoweroffOutlined, SettingOutlined, PlusOutlined } from "@ant-design/icons";
import { Avatar, Button, Card, Col, Divider, Modal, Row, Select, Skeleton, Switch, Tabs, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import { useState } from "react";
import ApplicationScreening from "./screening/ApplicationScreening";
import NewRoundForm from "../../components/forms/rounds/NewRoundForm";

function AdminCircularScreening() {
    const options = [];
    const {circularId,setCircularId}=useState(null);
    const handleChangeCircularSelect = (value) => {
        console.log(`selected ${value}`);
    };
    for (let i = 10; i < 36; i++) {
        options.push({
            value: i.toString(36) + i,
            label: i.toString(36) + i,
        });
    }
    const [isModalOpen, setIsModalOpen] = useState(false);
    const showNewRoundModal = () => {
        setIsModalOpen(true);
      };
    return (<>
        <Row justify={"space-between"} align={"middle"} style={{ margin: "10px 0" }}>
            <Col span={6}>
                <Typography.Title
                    level={3}
                    style={{ margin: '0' }}
                >
                    Screening Circular
                </Typography.Title>
            </Col>
            <Col span={18}>
                <Select
                    showSearch
                    style={{
                        width: '100%',
                    }}
                    placeholder="Select a circular"
                    onChange={handleChangeCircularSelect}
                    options={options}
                />
            </Col>
        </Row>
        <Typography.Paragraph>
            Please Select a circular for screening
        </Typography.Paragraph>
        <Divider></Divider>
        <Row justify="space-between" style={{ marginBottom: "30px" }}>
            <Col>
                <Typography.Title level={5} style={{ margin: "0 15px 0 0" }}>
                    Rounds
                </Typography.Title>
            </Col>
            <Col>
                <Button type="primary" icon={<PlusOutlined />} onClick={showNewRoundModal} >
                    create
                </Button>
                <NewRoundForm modalTitle={"New Round"} isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} circularId={circularId} />
            </Col>
        </Row>
        <Row>
            <Card
                style={{
                    width: 300,
                    marginTop: 16,
                }}
                actions={[
                    <PoweroffOutlined key="endround" />,
                    <EditOutlined key="editround" />,
                    <Switch defaultChecked />
                ]}
            >
                <Row justify='center'>
                    <Typography.Title
                        level={5}
                    >Application Open
                    </Typography.Title>
                </Row>
            </Card>
        </Row>
        <Divider></Divider>
        <Typography.Title level={5}>
            Round Wise Screening
        </Typography.Title>
        <Row>
            <Tabs
                type="card"
                defaultActiveKey="1"
                items={[
                    {
                        label: 'Application filtering',
                        key: '1',
                        children: <ApplicationScreening circularId={1} roundId={1} />,
                    },
                    {
                        label: 'Written Test',
                        key: '2',
                        children: 'Written Test',
                        disabled: true,
                    },
                    {
                        label: 'Apptitude Test',
                        key: '3',
                        children: 'Apptitude Test',
                    },
                    {
                        label: 'Technical Viva',
                        key: '4',
                        children: 'Technical Viva',
                    },
                    {
                        label: 'CEO Office Viva',
                        key: '5',
                        children: 'CEO Office Viva',
                    },
                    {
                        label: 'Selected Trainee',
                        key: '6',
                        children: 'Selected Trainees',
                    }
                ]}
            />
        </Row>
    </>);
}

export default AdminCircularScreening;