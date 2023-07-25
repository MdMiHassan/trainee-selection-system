import React from "react";
import { Tabs } from "antd";
import "../../styles/applicantactivity.css";
import Openings from "../../components/Openings";
import OpeningsListing from "../../components/OpeningsListing";
import JobDescription from "../Jobdescription";
import Applied from "../../components/Applied";
import NoticeBoard from "../../components/NoticeBoard";
import NoticeDetails from "../../components/NoticeDetails";

const { TabPane } = Tabs;

function ApplicantActivity() {
  return (
    <Tabs defaultActiveKey="openings" centered>
      <TabPane
        tab={
          <span className="tab-icon">
            <i className="fa-regular fa-briefcase"></i>
            Openings
          </span>
        }
        key="openings"
      >
        <OpeningsListing />
      </TabPane>
      <TabPane
        tab={
          <span className="tab-icon">
            <i className="fa-regular fa-clipboard-check"></i>
            Applied
          </span>
        }
        key="applied"
      >
       <Applied />
      </TabPane>
      <TabPane
        tab={
          <span className="tab-icon">
            <i className="fa-regular fa-bell"></i>
            Notice
          </span>
        }
        key="notice"
      >
        <NoticeBoard />
      </TabPane>
    </Tabs>
  );
}

export default ApplicantActivity;
