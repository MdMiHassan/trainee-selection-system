import React from 'react';
import { Card, Typography, Tag, Button, Space } from 'antd';
import '../styles/openingcard.css'
import BookmarkButton from './BookmarkButton';
const { Title, Text } = Typography;

const Openings = ({title,type,closing,vacency}) => {
    return (
        <div className="vacency">
            <div className="vacency-details">
                <div className="flex-row flex-start">
                    <div className="extra-details-icon">
                        <i className="fa-light fa-briefcase"></i>
                    </div>
                    <div className="extra-details-heading">
                        <Title level={3} style={{ marginTop: 0 }}>{title} <Tag color="blue">{type}</Tag></Title>
                        <Text>Closing - <span>{closing}</span> | Vacency - {vacency} </Text>
                    </div>
                    <BookmarkButton />
                </div>
            </div>
            <Button type="primary" block className='action'>Apply</Button>
        </div>
    );
};

export default Openings;
