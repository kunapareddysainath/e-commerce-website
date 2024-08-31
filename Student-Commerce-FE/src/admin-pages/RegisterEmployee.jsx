import React, { useState } from 'react';
import { Footer } from "../components";
import axios from "axios";
import { backEndUrl } from "../config.js";
import Navbar from '../admin-components/Navbar.jsx';
import SideMenu from '../admin-components/SideMenu.jsx';
import { useNavigate } from 'react-router-dom';

const RegisterEmployee = () => {
  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [role, setRole] = useState("");

  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

    if (name && email && username && role) {
      const requestBody = {
        name: name,
        username: username,
        emailAddress: email,
        phoneNumber: phoneNumber,
        role: role
      };
      try {
        const authToken = localStorage.getItem("authToken");
        const response = await axios.post(`${backEndUrl}/api/employee/register`, requestBody,
          {
            headers: {
              'Authorization': `Bearer ${authToken}` // Include authToken in headers
            }
          });
        if (response.data) {
          alert("Registration is successful");
          navigate("/admin-home");
        }
      } catch (error) {
        console.error(error);
      }
    } else {
      alert("Please provide valid details for registration");
    }
  };

  return (
    <>
      <Navbar />
      <div className='container-fluid'>
        <div className='row'>
          <div className="col-md-2">
            <SideMenu />
          </div>
          <div className="col-md-10">
            <div className="container my-3 py-3">
              <div className="row my-4">
                <div className="col-md-12">
                  <form onSubmit={handleRegister}>
                    <div className="form-group my-3">
                      <label htmlFor="Name">Full Name</label>
                      <input
                        type="text"
                        className="form-control"
                        id="Name"
                        placeholder="Enter Your Name"
                        onChange={(e) => setName(e.target.value)}
                      />
                    </div>
                    <div className="form-group my-3">
                      <label htmlFor="Username">User Name</label>
                      <input
                        type="text"
                        className="form-control"
                        id="Username"
                        placeholder="Enter Your User Name"
                        onChange={(e) => setUsername(e.target.value)}
                      />
                    </div>
                    <div className="form-group my-3">
                      <label htmlFor="Email">Email address</label>
                      <input
                        type="email"
                        className="form-control"
                        id="Email"
                        placeholder="Enter your email address"
                        onChange={(e) => setEmail(e.target.value)}
                      />
                    </div>
                    <div className="form-group my-3">
                      <label htmlFor="phoneNumber">Phone Number</label>
                      <input
                        type="text"
                        className="form-control"
                        id="phoneNumber"
                        placeholder="Enter your phone number"
                        onChange={(e) => setPhoneNumber(e.target.value)}
                      />
                    </div>
                    <div className="form-group my-3">
                      <label htmlFor="role">Role</label>
                      <select
                        className="form-control"
                        id="role"
                        onChange={(e) => setRole(e.target.value)}
                      >
                        <option value="">Select Role</option>
                        <option value="Delivery person">Delivery person</option>
                        <option value="Manager">Manager</option>
                        {/* Add other roles as necessary */}
                      </select>
                    </div>
                    <div className="text-center">
                      <button className="btn btn-dark my-2" type="submit">
                        Hire Employee
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}

export default RegisterEmployee;
