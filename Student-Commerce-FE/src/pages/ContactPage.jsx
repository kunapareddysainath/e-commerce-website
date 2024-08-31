import React, { useState } from "react";
import { Footer } from "../components";
import axios from "axios";
import { Modal, Button } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";

const backEndUrl = "http://localhost:8080";

const ContactPage = () => {
  const [userEmail, setUserEmail] = useState("");
  const sendermail = localStorage.getItem("emailAddress");
  const [subject, setSubject] = useState("");
  const [emailBody, setEmailBody] = useState("");
  const [showModal, setShowModal] = useState(false);

  const navigation = useNavigate();

  const handleEmailSend = async (e) => {
    e.preventDefault();

    const senderMailAddress = sendermail ? sendermail : userEmail;

    try {
      const requestBody = {
        senderMailAddress: senderMailAddress,
        subject: subject,
        body: emailBody
      };

      const response = await axios.post(backEndUrl + "/api/email/send", requestBody);

      if (response.data.statusCode === 200) {
        setShowModal(true);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    navigation("/");
  };

  return (
    <>
      <div className="container my-3 py-3">
        <h1 className="text-center">Contact Us</h1>
        <hr />
        <div class="row my-4 h-100">
          <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
            <form onSubmit={handleEmailSend}>
              {!(sendermail !== undefined && sendermail !== null && sendermail !== "") ? (
                <div class="form my-3" style={{ display: "flex", alignItems: "center" }}>
                  <label for="Email" style={{ paddingRight: "10px" }}>
                    Email
                  </label>
                  <input
                    type="email"
                    class="form-control"
                    id="Email"
                    placeholder="Your email address"
                    onChange={(e) => setUserEmail(e.target.value)}
                  />
                </div>
              ) : (
                <p>{sendermail}</p>
              )}
              <div class="form my-3">
                <input
                  type="text"
                  class="form-control"
                  id="Name"
                  placeholder="Subject"
                  onChange={(e) => setSubject(e.target.value)}
                />
              </div>
              <div class="form  my-3">
                <textarea
                  rows={5}
                  class="form-control"
                  id="emailbody"
                  placeholder="Enter your message"
                  onChange={(e) => setEmailBody(e.target.value)}
                />
              </div>
              <div className="text-center">
                <button class="my-2 px-4 mx-auto btn btn-dark" type="submit">
                  Send
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <Footer />
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Email Sent Successfully</Modal.Title>
        </Modal.Header>
        <Modal.Body>Your email has been sent successfully.</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Return
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default ContactPage;
