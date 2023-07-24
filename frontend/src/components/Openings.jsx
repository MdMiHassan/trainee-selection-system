import React, { useState } from 'react';
import { Card, Typography, Tag, Button, Col, Row, } from 'antd';
import { FileTextOutlined } from '@ant-design/icons';
import '../styles/openingcard.css'
import BookmarkButton from './BookmarkButton';
import ApplyModal from './apply/ApplyModal';
import CircularViewModal from './circular/CircularVIewModal';

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
        <Card>
            <Row>
                <Col style={{ marginRight: "10px", paddingTop: "5px" }}>
                    <FileTextOutlined style={{ fontSize: '24px' }} />
                </Col>
                <Col>
                    <Row>
                        <Typography.Title level={3} style={{ marginTop: 0, padding: 0 }} onClick={onclickCircularView}>
                            {title} <Tag color="blue">{type}</Tag>
                        </Typography.Title>
                        <div style={{ paddingTop: "5px" }}>
                            <BookmarkButton circularId={circularId} />
                        </div>
                    </Row>
                    <Typography.Text>
                        Closing - <span>{closing}</span> | Vacancy - {vacancy}
                    </Typography.Text>
                </Col>
            </Row>
            <Row justify={"end"}>
                <Col span={4}>
                    <Button type="primary" block className='action' onClick={onclickApply}>Apply</Button>
                </Col>
            </Row>
            <ApplyModal isApplyModalOpen={isApplyModalOpen} setIsApplyModalOpen={setIsApplyModalOpen} circularId={circularId} />
            <CircularViewModal isCircularModalOpen={isCircularModalOpen} setIsCircularModalOpen={setIsCircularModalOpen} circularId={circularId} />
        </Card >
    );
};

export default Openings;
