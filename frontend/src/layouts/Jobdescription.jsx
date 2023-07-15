import { Card, Typography, Divider, Tag } from 'antd';
import { CalendarOutlined, EnvironmentOutlined, DollarOutlined, FlagOutlined, UsergroupAddOutlined, BookOutlined, FileSearchOutlined, TeamOutlined } from '@ant-design/icons';
import '../styles/Jobdescription.css'
const { Title, Paragraph } = Typography;

const JobDescriptionCard = () => {
  return (
    <Card title="Training Description">

      <Title level={3}>Software Engineer Trainee (J2EE)<Tag color="blue">Full Time</Tag></Title>
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
            <Paragraph>Baridhara, Dhaka</Paragraph>
          </div>
        </div>

        <div className="job-insights-card">
          <div className="job-insights-icon">
            <DollarOutlined />
          </div>
          <div className="job-insights-content">
            <Title level={5} style={{ margin: 0 }}>Offered Salary</Title>
            <Paragraph>$60,000 per annum</Paragraph>
          </div>
        </div>

        <div className="job-insights-card">
          <div className="job-insights-icon">
            <FlagOutlined />
          </div>
          <div className="job-insights-content">
            <Title level={5} style={{ margin: 0 }}>Career Level</Title>
            <Paragraph>Entry Level</Paragraph>
          </div>
        </div>

        <div className="job-insights-card">
          <div className="job-insights-icon">
            <UsergroupAddOutlined />
          </div>
          <div className="job-insights-content">
            <Title level={5} style={{ margin: 0 }}>Vacency</Title>
            <Paragraph>5 positions</Paragraph>
          </div>
        </div>

        <div className="job-insights-card">
          <div className="job-insights-icon">
            <BookOutlined />
          </div>
          <div className="job-insights-content">
            <Title level={5} style={{ margin: 0 }}>Qualification</Title>
            <Paragraph>Bachelor's Degree</Paragraph>
          </div>
        </div>

        <div className="job-insights-card">
          <div className="job-insights-icon">
            <FileSearchOutlined />
          </div>
          <div className="job-insights-content">
            <Title level={5} style={{ margin: 0 }}>Experience</Title>
            <Paragraph>1-3 years</Paragraph>
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
            We are seeking a talented and motivated individual to join our team as a Software Engineer. In this role, you will be responsible for developing and maintaining high-quality software solutions using J2EE technologies.
          </Paragraph>
        </div>

        <div>
          <Title level={5}>Requirements</Title>
          <ul className='requirments-skill-list'>
            <li>Bachelor's degree in Computer Science or a related field</li>
            <li>Strong knowledge of J2EE technologies and frameworks</li>
            <li>Experience with database management systems</li>
            <li>Excellent problem-solving and analytical skills</li>
            <li>Good communication and teamwork abilities</li>
          </ul>
        </div>

        <div>
          <Title level={5}>Skills & Experience</Title>
          <ul className='requirments-skill-list'>
            <li>Proficiency in Java and J2EE</li>
            <li>Experience with Spring Framework</li>
            <li>Knowledge of SQL and database design</li>
            <li>Familiarity with front-end technologies such as HTML, CSS, and JavaScript</li>
            <li>Experience working in an Agile development environment</li>
          </ul>
        </div>
      </div>
    </Card>
  );
};

export default JobDescriptionCard;
