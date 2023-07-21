import { Card, Typography } from 'antd';
import Openings from './Openings';
import '../styles/openingslisting.css'
import { Row, Col } from 'antd';
import { useEffect, useState } from 'react';
import { API_BASE_URL } from '../Config';

function OpeningsListing() {
  const [circularData, setCircularData] = useState([]);
  const [loading, setLoading] = useState(false);

  const formatDate = (timestamp) => {
    const date = new Date(timestamp);
    return date.toLocaleString();
  };

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await fetch(API_BASE_URL + '/circulars');
        const data = await response.json();
        const tableRows = data.content;
        setCircularData(tableRows);
        setLoading(false);
        console.log(tableRows);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  return (
      <Row justify='center'>
        <Col xs={22} sm={20} md={20} lg={20} xl={18} xxl={16}>
          {circularData.length ? (
            circularData.map((circular) => (
              <Openings
                key={circular.id} // Make sure to add a unique key for each element in the map function
                title={circular.title}
                type={circular.trainingType}
                closing={formatDate(circular.closingDate)}
                vacancy={circular.vacancy}
              />
            ))
          ) : (
            <Typography.Text>No data Available</Typography.Text>
          )}
        </Col>
      </Row>
  );
}

export default OpeningsListing;
