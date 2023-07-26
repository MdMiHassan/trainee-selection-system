import {Row,message } from "antd";
import { useContext, useEffect, useState } from "react";
import { API_BASE_URL } from "../../Config";
import { AuthContext } from "../../context/AuthContext";
import CircularRoundCard from "./CircularRoundCard";

function CircularRounds({ circularId,isModalOpen,reloadRequired }) {
    const [roundData, setRoundData] = useState([]);
    const [currentRoundSerialNo, setCurrentRoundSerialNo] = useState(3);
    const { token } = useContext(AuthContext);
    useEffect(() => {
        if (circularId) {
            fetch(API_BASE_URL + '/circulars/' + circularId + '/rounds', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            }
            )
                .then((response) => response.json())
                .then((data) => {
                    console.log(data);
                    const fetchedContent = data.rounds;
                    setCurrentRoundSerialNo(data.currentRoundSerialNo);
                    const sortedRoundData = fetchedContent ? [...fetchedContent].sort((a, b) => a.serialNo - b.serialNo) : null;
                    setRoundData(sortedRoundData);
                    console.log(sortedRoundData);
                })
                .catch((error) => {
                    message.error("Round data fetching failed!")
                });
        }
    }, [circularId,isModalOpen,reloadRequired]);
    return (
        <Row>
                {roundData ? (
                    roundData.map((option) => (
                        <CircularRoundCard key={option.serialNo} 
                        roundinfo={option} 
                        currentRoundSerialNo={currentRoundSerialNo} />
                    ))
                ) : null}
        </Row>
    );
}

export default CircularRounds;