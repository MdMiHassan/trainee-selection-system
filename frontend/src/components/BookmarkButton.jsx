import React, { useContext } from 'react';
import { Button, message } from 'antd';
import { StarOutlined, StarFilled } from '@ant-design/icons';
import { API_BASE_URL } from '../Config';
import { AuthContext } from '../context/AuthContext';
const BookmarkButton = ({circularId}) => {
    const [bookmarked, setBookmarked] = React.useState(false);
    const {token}=useContext(AuthContext);
    const handleBookmarkClick = (event) => {
            event.stopPropagation();
            fetch(API_BASE_URL + '/circulars/' + circularId + '/bookmark/toggle',
            {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer '+token,
                },
            }
            )
            .then((response) => response.json())
            .then((data) => {
                if(bookmarked){
                    setBookmarked(false);
                    message.success("Circular removed from the bookmark");
                }else{
                    setBookmarked(true);
                    message.success("Circular Added to the bookmark");
                }
            })
            .catch((error) => {
                message.error("Failed to toggle bookmark");
                console.error("Error fetching data:", error);
            });
    };
  
    return (
      <Button
        type={bookmarked ? 'primary' : 'text'}
        onClick={handleBookmarkClick}
        icon={bookmarked ? <i className="fa-solid fa-bookmark"></i> : <i className="fa-light fa-bookmark"></i>}
      >
      </Button>
    );
  };
  export default BookmarkButton;