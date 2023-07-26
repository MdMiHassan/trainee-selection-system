import { PlusOutlined } from "@ant-design/icons";
import { Button, Col, Divider,Row, Select, Tabs, Typography, message } from "antd";
import { useContext, useEffect, useState } from "react";
import ApplicationScreening from "./screening/ApplicationScreening";
import NewRoundForm from "../../components/forms/rounds/NewRoundForm";
import { API_BASE_URL } from "../../Config";
import CircularRounds from "../../components/round/CircularRounds";
import EvaluationRound from "../../components/screening/EvaluationRound";
import { AuthContext } from "../../context/AuthContext";

function AdminCircularScreening() {
    const [circularsOptions, setCircularsOptions] = useState([]);
    const [circularId, setCircularId] = useState(null);
    const [rounds, setRounds] = useState([]);
    const [reloadRequired, setReloadRequired] = useState([]);
    const { token } = useContext(AuthContext);
    const handleChangeCircularSelect = (value) => {
        console.log(value);
        setCircularId(value);
    };
    const [isModalOpen, setIsModalOpen] = useState(false);
    const showNewRoundModal = () => {
        setIsModalOpen(true);
    };
    useEffect(() => {
        fetch(API_BASE_URL + '/circulars', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => response.json())
            .then((data) => {

                const newData = data.content;
                const sortedCircularData = newData
                    ? [...newData].sort((a, b) => b.id - a.id)
                    : [];

                const options = sortedCircularData.map((circular) => ({
                    value: circular.id,
                    label: circular.title,
                }));
                console.log(options);
                setCircularsOptions(options);
            })
            .catch((error) => {
                message.error("Circular fetching failed");
            });

    }, [circularId, isModalOpen]);

    useEffect(() => {
        if (circularId) {
            fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => response.json())
                .then((data) => {
                    const fetchedRounds = data.rounds;
                    const sortedRounds = fetchedRounds ? fetchedRounds.sort((a, b) => a.serialNo - b.serialNo) : [];
                    setRounds(sortedRounds);
                })
                .catch((error) => {
                    message.error("Something went wrong!");
                });
        }
    }, [circularId, isModalOpen]);
    const handleEndCurrentRound = (event) => {
        fetch(API_BASE_URL + `/circulars/${circularId}/rounds/current/actions/end`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then((response) => {
                if(response.ok){
                    setReloadRequired(!reloadRequired);
                    setCircularId(circularId);
                    message.success("Current Round Closed Successfully");
                }else{
                    message.error("Something went wrong!")
                }
            })
            .catch((error) => {
                message.error("Something went wrong!")
            });
    }
    return (<>
        <Row justify={"space-between"} align={"middle"} style={{ margin: "10px 0" }}>
            <Col span={6}>
                <Typography.Title
                    level={3}
                    style={{ margin: '0' }}
                >
                    Screening Application
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
                <Button type="danger" style={{ marginRight: "20px", backgroundColor: "#ff304f" }} onClick={handleEndCurrentRound}>
                    End Current Round
                </Button>
                <Button type="primary" icon={<PlusOutlined />} onClick={showNewRoundModal} >
                    Create Round
                </Button>
                <NewRoundForm modalTitle={"New Round"} isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} circularId={circularId} />
            </Col>
        </Row>
        <Row>
            <CircularRounds circularId={circularId} isModalOpen={isModalOpen} reloadRequired={reloadRequired}/>
        </Row>
        <Divider></Divider>
        <Typography.Title level={5}>
            Round Wise Screening
        </Typography.Title>
        <Row>
            <Col span={24}>
                <Tabs
                    type="card"
                    defaultActiveKey={rounds.length > 0 ? rounds[0].roundId.toString() : "default"}
                    items={rounds.length > 0 ? (
                        rounds.map((round) => ({
                            label: round.title,
                            key: round.roundId.toString(),
                            children: round.serialNo < 1 ? (
                                <ApplicationScreening circularId={circularId} roundId={round.roundId} />
                            ) : (
                                <EvaluationRound circularId={circularId} roundId={round.roundId} />
                            ),
                        }))
                    ) : (
                        [{
                            label: "No rounds available",
                            key: "default",
                            children: "No rounds available for the selected circular.",
                        }]
                    )}
                />
            </Col>
        </Row>
    </>);
}

export default AdminCircularScreening;