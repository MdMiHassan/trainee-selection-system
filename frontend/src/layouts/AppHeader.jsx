import React from "react";
import '../styles/appheader.css';
import { Button, Row, Col } from "antd";
import AvatarDropdown from "../components/AvatarMenu";

function AppHeader() {
    return (
        <header className='header sticky-bar'>
            <div className='main-header flex-row flex-space-between'>
                <div className="header-left">
                    <div className='header-logo'>
                        <a className='flex-row flex-center' href="/">
                            TSS
                        </a>
                    </div>
                </div>
                <div className='header-right'>
                    <Row gutter={[16, 16]} justify="end">
                        <Col>
                            <Button type="text">Sign in</Button>
                        </Col>
                        <Col>
                            <Button type="primary">Sign up</Button>
                        </Col>
                        <AvatarDropdown />
                    </Row>
                </div>
            </div>
        </header>
    );
}

export default AppHeader;
