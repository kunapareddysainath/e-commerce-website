import React, { useState } from 'react';
import { Footer } from "../components";
import { Link } from 'react-router-dom';
import axios from "axios";
import {backEndUrl} from "../config.js"

const Register = () => {

    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [passwordMatch, setPasswordMatch] = useState(false);

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
        if (e.target.value === confirmPassword) {
            setPasswordMatch(true);
        } else {
            setPasswordMatch(false);
        }
    };

    const handleConfirmPasswordChange = (e) => {
        setConfirmPassword(e.target.value);
        if (e.target.value === password) {
            setPasswordMatch(true);
        } else {
            setPasswordMatch(false);
        }
    };

    const handleRegister = async (e) => {

        e.preventDefault();

        if ((password === confirmPassword) && name && email && username) {
            const requestBody = {
                name: name,
                username: username,
                emailAddress: email,
                phoneNumber: phoneNumber,
                password: password
            };
            try {
                const response = await axios.post(backEndUrl + "/api/auth/signup", requestBody);
                if (response.data) {
                    alert("Registration is successfully");
                }
            } catch (error) {
                console.error(error);
            }
        } else {
            alert("Please valid details for registration")
        }
    };

    return (
        <>
            <div className="navbar navbar-light bg-light d-flex align-items-center">
                <div className='pl-4'><h1><i>Student Ecommerce</i></h1></div>
                <div className="flex-grow-1" style={{ paddingLeft: "300px" }}><h1><i>Register</i></h1></div>
            </div>
            <div className="container my-3 py-3">
                <div className="row my-4 h-100">
                    <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
                        <form onSubmit={handleRegister}>
                            <div className="form my-3">
                                <label htmlFor="Name">Full Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="Name"
                                    placeholder="Enter Your Name"
                                    onChange={(e) => setName(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label htmlFor="Username">User Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="Username"
                                    placeholder="Enter Your User Name"
                                    onChange={(e) => setUsername(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label htmlFor="Email">Email address</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    id="Email"
                                    placeholder="Enter your email address"
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label htmlFor="phone-number">Phone Number</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="phoneNumber"
                                    placeholder="Enter your phone number"
                                    onChange={(e) => setPhoneNumber(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label htmlFor="Password">Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    id="Password"
                                    placeholder="Enter your Password"
                                    onChange={handlePasswordChange}
                                />
                            </div>
                            <div className="form my-3">
                                <label htmlFor="ConfirmPassword">Confirm Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    id="ConfirmPassword"
                                    placeholder="Enter your confirm Password"
                                    onChange={handleConfirmPasswordChange}
                                />
                            </div>
                            {confirmPassword && !passwordMatch && (
                                <p style={{ color: 'red' }}>Passwords do not match!</p>
                            )}
                            {confirmPassword && passwordMatch && (
                                <p style={{ color: 'green' }}>Passwords match!</p>
                            )}

                            <div className="my-3">
                                <p>Already have an account? <Link to="/login" className="text-decoration-underline text-info">Login</Link> </p>
                            </div>
                            <div className="text-center">
                                <button className="my-2 mx-auto btn btn-dark" type="submit" >
                                    Register
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <Footer />
        </>
    )
}

export default Register;
