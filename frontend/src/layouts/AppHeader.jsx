import React, { useEffect, useState } from "react";
import "../styles/AppHeader.css";
import { Button, Row, Col } from "antd";
import AvatarDropdown from "../components/AvatarMenu";
import { useNavigate } from "react-router-dom";

function AppHeader() {
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
          backgroundColor: "#ffffff",
          boxShadow: "rgba(99, 99, 99, 0.2) 0px 2px 8px 0px"
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
              TSS
            </a>
          </div>
        </div>
        <div className="header-right">
          <Row gutter={[16, 16]} justify="end">
            <Col>
              <Button type="text" onClick={handleSignIn}>
                Sign in
              </Button>
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
