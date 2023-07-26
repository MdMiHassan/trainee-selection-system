import React, { useEffect, useState, useContext } from "react";
import { Button, Row, Col, Typography } from "antd";
import AvatarDropdown from "../components/AvatarMenu";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import "../styles/AppHeader.css";
function AppHeader() {
    const { token } = useContext(AuthContext);
    const [headerStyle, setHeaderStyle] = useState({
        backgroundColor: "transparent",
        boxShadow: "none"
    });

    const navigateTo = useNavigate();

    useEffect(() => {
        const handleScroll = () => {
            const scrollPosition =
                window.scrollY || document.documentElement.scrollTop;
            if (scrollPosition > 0) {
                setHeaderStyle({
                    background: "rgba(255, 255, 255, 0.05)",
                    backdropFilter: "blur(1px)",
                });
            } else {
                setHeaderStyle({
                    backgroundColor: "transparent",
                    boxShadow: "none"
                });
            }
        };

        window.addEventListener("scroll", handleScroll);

        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, []);

    const handleSignIn = () => {
        navigateTo("/login");
    };

    return (
        <header className="header sticky-bar" style={headerStyle}>
            <div className="main-header flex-row flex-space-between">
                <div className="header-left">
                    <div className="header-logo">
                        <a className="flex-row flex-center" href="/">
                            <Row>
                                <Col style={{ textAlign: "center" }}>
                                    <Typography.Title level={4} style={{ margin: "0" }}>
                                        TSS
                                    </Typography.Title>
                                </Col>
                            </Row>
                        </a>
                    </div>
                </div>
                <div className="header-right">
                    <Row gutter={[16, 16]} justify="end">
                        {token ? <AvatarDropdown /> : (<>
                            <Col>
                                <Button type="text" onClick={handleSignIn}>
                                    Sign in
                                </Button>
                            </Col>
                            <Col>
                                <Button type="primary">Sign up</Button>
                            </Col>
                        </>)
                        }
                    </Row>
                </div>
            </div>
        </header>
    );
}

export default AppHeader;
