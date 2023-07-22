import React, { useState } from 'react';
import { Card, Typography, Tag, Button, Space, message } from 'antd';
import '../styles/openingcard.css'
import BookmarkButton from './BookmarkButton';
import ApplyModal from './apply/ApplyModal';
import CircularViewModal from './circular/CircularVIewModal';
import { API_BASE_URL } from '../Config';
const { Title, Text } = Typography;

const Openings = ({ title, type, closing, vacancy, circularId }) => {
    const [isApplyModalOpen, setIsApplyModalOpen] = useState(false);
    const [isCircularModalOpen, setIsCircularModalOpen] = useState(false);
    const onclickApply = (event) => {
        event.stopPropagation();
        setIsApplyModalOpen(true);
    }
    const onclickCircularView = (event) => {
        event.stopPropagation();
        setIsCircularModalOpen(true);
    }
    return (
        <div className="vacency" >
            <div className="vacency-details">
                <div className="flex-row flex-start">
                    <div className="extra-details-icon">
                        <i className="fa-light fa-briefcase"></i>
                    </div>
                    <div className="extra-details-heading">
                        <Title level={3} style={{ marginTop: 0 }} onClick={onclickCircularView}>{title} <Tag color="blue">{type}</Tag></Title>
                        <Text>Closing - <span>{closing}</span> | Vacency - {vacancy} </Text>
                    </div>
                    <BookmarkButton circularId={circularId}/>
                </div>
            </div>
            <Button type="primary" block className='action' onClick={onclickApply}>Apply</Button>
            <ApplyModal isApplyModalOpen={isApplyModalOpen} setIsApplyModalOpen={setIsApplyModalOpen} circularId={circularId} />
            <CircularViewModal isCircularModalOpen={isCircularModalOpen} setIsCircularModalOpen={setIsCircularModalOpen} circularId={circularId} />
        </div>
    );
};

export default Openings;
