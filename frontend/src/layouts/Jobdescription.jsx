import { Card, Typography, Divider, Tag } from 'antd';
import { CalendarOutlined, EnvironmentOutlined, DollarOutlined, FlagOutlined, UsergroupAddOutlined, BookOutlined, FileSearchOutlined, TeamOutlined } from '@ant-design/icons';
import '../styles/Jobdescription.css'
const { Title, Paragraph } = Typography;

const JobDescriptionCard = ({circular}) => {

  return (
    <Card>
      <Title level={3}>{circular.title}<Tag color="blue">{circular.trainingType}</Tag></Title>
      <div>
        <Title level={4} style={{ marginBottom: 0 }}>Insights</Title>
        <Divider />
        <div className='insights-cards'>
          <div className="job-insights-card">
            <div className="job-insights-icon">
              <CalendarOutlined />
            </div>
            <div className="job-insights-content">
              <Title level={5} style={{ margin: 0 }}>Closing Date</Title>
              <Paragraph>12 July 2023</Paragraph>
            </div>
          </div>

          <div className="job-insights-card">
            <div className="job-insights-icon">
              <EnvironmentOutlined />
            </div>
            <div className="job-insights-content">
              <Title level={5} style={{ margin: 0 }}>Hiring Location</Title>
              <Paragraph>{circular.hiringLocation}</Paragraph>
            </div>
          </div>

          <div className="job-insights-card">
            <div className="job-insights-icon">
              <DollarOutlined />
            </div>
            <div className="job-insights-content">
              <Title level={5} style={{ margin: 0 }}>Offered Salary</Title>
              <Paragraph>{circular.salary}</Paragraph>
            </div>
          </div>

          <div className="job-insights-card">
            <div className="job-insights-icon">
              <FlagOutlined />
            </div>
            <div className="job-insights-content">
              <Title level={5} style={{ margin: 0 }}>Career Level</Title>
              <Paragraph>{circular.careerLevel}</Paragraph>
            </div>
          </div>

          <div className="job-insights-card">
            <div className="job-insights-icon">
              <UsergroupAddOutlined />
            </div>
            <div className="job-insights-content">
              <Title level={5} style={{ margin: 0 }}>Vacency</Title>
              <Paragraph>{circular.vacancy}</Paragraph>
            </div>
          </div>

          <div className="job-insights-card">
            <div className="job-insights-icon">
              <BookOutlined />
            </div>
            <div className="job-insights-content">
              <Title level={5} style={{ margin: 0 }}>Qualification</Title>
              <Paragraph>{circular.requiredEducation}</Paragraph>
            </div>
          </div>

          <div className="job-insights-card">
            <div className="job-insights-icon">
              <FileSearchOutlined />
            </div>
            <div className="job-insights-content">
              <Title level={5} style={{ margin: 0 }}>Experience</Title>
              <Paragraph>{circular.minExp}-{circular.maxExp} years</Paragraph>
            </div>
          </div>

          <div className="job-insights-card">
            <div className="job-insights-icon">
              <TeamOutlined />
            </div>
            <div className="job-insights-content">
              <Title level={5} style={{ margin: 0 }}>Gender</Title>
              <Paragraph>Any</Paragraph>
            </div>
          </div>
        </div>
      </div>

      <div>
        <Title level={4} style={{ marginBottom: 0 }}>Description</Title>
        <Divider />

        <div>
          <Title level={5}>Overview</Title>
          <Paragraph>
            {circular.overview}
          </Paragraph>
        </div>

        <div>
          <Title level={5}>Duties</Title>
          <ul className='requirments-skill-list'>
            {circular.duties}
          </ul>
        </div>

        <div>
          <Title level={5}>Skills & Experience</Title>
          <ul className='requirments-skill-list'>
            <li>{circular.skills}</li>
          </ul>
        </div>
      </div>
    </Card>
  );
};

export default JobDescriptionCard;
