import React from 'react';

const ShareButton = (props) => {
  const handleShare = () => {
    // Construct the WhatsApp share link
    const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent('Check out this amazing product: [Product Name] [Product Link]')}`;
    // Redirect the user to WhatsApp
    window.location.href = whatsappUrl;
  };

  return (
    <button onClick={handleShare}>
      Share on WhatsApp
    </button>
  );
};

export default ShareButton;
