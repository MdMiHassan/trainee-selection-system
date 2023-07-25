import { Button, Modal } from "antd";
import JobDescriptionCard from "../../layouts/Jobdescription";
import { useContext, useEffect, useState } from "react";
import { API_BASE_URL } from "../../Config";
import { AuthContext } from "../../context/AuthContext";

function CircularViewModal({ isCircularModalOpen, setIsCircularModalOpen, circularId }) {
    const handleNewRoundCancel = (event) => {
        event.stopPropagation();
        setIsCircularModalOpen(false);
    };
    const [circular,setCircular]=useState(null);
    const {token}=useContext(AuthContext);
    useEffect(() => {
        if (circularId) {
            fetch(API_BASE_URL + '/circulars/' + circularId,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization':`Bearer ${token}`
            }}
            )
                .then((response) => response.json())
                .then((data) => {
                    setCircular(data);
                })
                .catch((error) => {
                    message.error("Application failed!")
                });
        }
    }, [isCircularModalOpen, circularId]);
    return (
        <Modal title={"Training Description"}
            open={isCircularModalOpen}
            onCancel={handleNewRoundCancel}
            footer={[
                <Button key="cancel" onClick={handleNewRoundCancel}>
                    close
                </Button>
            ]}
            width={1000}
        >
            <JobDescriptionCard circular={circular} />
        </Modal>

    );
}

export default CircularViewModal;