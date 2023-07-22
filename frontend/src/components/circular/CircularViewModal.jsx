import { Button, Modal } from "antd";
import JobDescriptionCard from "../../layouts/Jobdescription";
import { useEffect, useState } from "react";
import { API_BASE_URL } from "../../Config";

function CircularViewModal({ isCircularModalOpen, setIsCircularModalOpen, circularId }) {
    const handleNewRoundCancel = (event) => {
        event.stopPropagation();
        setIsCircularModalOpen(false);
    };
    const [circular,setCircular]=useState(null);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(API_BASE_URL + '/circulars/' + circularId);
                const data = await response.json();
                    setCircular(data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };
        fetchData();
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
        >
            <JobDescriptionCard circular={circular} />
        </Modal>

    );
}

export default CircularViewModal;