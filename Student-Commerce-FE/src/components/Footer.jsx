import React from "react";
import whatsappLogo from "../assests/whatsapp-logo.png";
import emailLogo from "../assests/email-logo.png"
import { NavLink } from "react-router-dom";

const Footer = () => {

  const message = "Hi Sai,";
  const encodedMessage = encodeURIComponent(message);
  const whatsAppUrl = `https://wa.me/917981252711?text=${encodedMessage}`;

  return (
    <>
      <footer className="mb-0 text-center">
        <div className="d-flex align-items-center justify-content-center pb-5">
          <div className="col-md-6">
            <p className="mb-3 mb-md-0 font-italic">Developed by {" "}
              <a href="https://www.instagram.com/_sainath_ig" className="text-decoration-none text-dark fs-5" target="_blank" rel="noreferrer"><i> <b>Sai Dev Coder </b></i> <img width={"20px"} src="./assets/dev-logo.png"></img> </a>
            </p>
          </div>
          <div className="whatsapp-interaction">
            <i><b>From any suggestions</b></i>
            <a href={whatsAppUrl} target="_blank" style={{ textDecoration: "none" }}>
              <img src={whatsappLogo} alt="no image" width={50}></img>
            </a>
          </div>
          <div className="email-interaction">
            <NavLink className="nav-link font-weight-bold font-italic" to="/contact"><img src={emailLogo} alt="no image" width={50} style={{ padding: "10px" }}></img></NavLink>
          </div>
        </div>
      </footer>
    </>
  );
};

export default Footer;
