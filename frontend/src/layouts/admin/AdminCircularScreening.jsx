import { EditOutlined, EllipsisOutlined, PoweroffOutlined, SettingOutlined, PlusOutlined } from "@ant-design/icons";
import { Avatar, Button, Card, Col, Divider, Modal, Row, Select, Skeleton, Switch, Tabs, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import { useEffect, useState } from "react";
import ApplicationScreening from "./screening/ApplicationScreening";
import NewRoundForm from "../../components/forms/rounds/NewRoundForm";
import { API_BASE_URL } from "../../Config";
import CircularRounds from "../../components/round/CircularRounds";

function AdminCircularScreening() {
    const [circularsOptions, setCircularsOptions] = useState([]);
    const [circulars, setCirculars] = useState([]);
    const [circularId, setCircularId] = useState(null);

    const handleChangeCircularSelect = (value) => {
        setCircularId(value);
    };
    const [isModalOpen, setIsModalOpen] = useState(false);
    const showNewRoundModal = () => {
        setIsModalOpen(true);
    };
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(API_BASE_URL + '/circulars');
                const data = await response.json();
                const newData = data.content;
                const sortedCircularData = newData
                    ? [...newData].sort((a, b) => b.id - a.id)
                    : [];

                const options = sortedCircularData.map((circular) => ({
                    value: circular.id,
                    label: circular.title,
                }));
                setCircularsOptions(options);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);

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
                    options={circularsOptions}
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
                <NewRoundForm modalTitle={"New Round"} isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} circularId={1} />
            </Col>
        </Row>
        <Row>
            <CircularRounds circularId={circularId}/>
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