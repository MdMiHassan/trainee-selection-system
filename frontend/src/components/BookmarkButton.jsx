import React from 'react';
import { Button } from 'antd';
import { StarOutlined, StarFilled } from '@ant-design/icons';
const BookmarkButton = () => {
    const [bookmarked, setBookmarked] = React.useState(false);
  
    const handleBookmarkClick = () => {
      setBookmarked(!bookmarked);
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