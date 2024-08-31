import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Footer } from "../components";
import axios from "axios";
import { backEndUrl } from "../config.js";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Login = () => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const navigation = useNavigate();


  useEffect(() => {
    const authToken = localStorage.getItem("authToken");
    const role = localStorage.getItem('role');
    if (role === 'Admin' && authToken) {
      navigation('/admin-home');
    }
    else if (role === 'Delivery person' && authToken) {
      navigation('/delivery-home');
    }
    else if (role === 'User' && authToken) {
      navigation('/home');
    } else {
      navigation('/login');
    }
  }, [navigation]);

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const requestBody = {
        username: username,
        password: password
      }
      const response = await axios.post(backEndUrl + "/api/auth/login", requestBody);

      console.log(response.data);

      const { accessToken, userDetails } = response.data;

      localStorage.setItem("authToken", accessToken);
      localStorage.setItem("userId", userDetails.id);
      localStorage.setItem("username", userDetails.username);
      localStorage.setItem("name", userDetails.name);
      localStorage.setItem("phoneNumber", userDetails.phoneNumber);
      localStorage.setItem("emailAddress", userDetails.emailAddress);
      localStorage.setItem("role", userDetails.role.label);

      console.log(userDetails);

      if (userDetails.role.label === 'Delivery person') {
        navigation('/delivery-home');
      }
      else if (userDetails.role.label === 'User') {
        navigation('/home');
      }

    }
    catch (error) {
      console.error(error);
      toast.error('Login failed. Please check your credentials')
    }
  }

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  }

  return (
    <>
      <div className="navbar navbar-light bg-light d-flex align-items-center">
        <div className="pl-2"><h1><i>Student Ecommerce</i></h1></div>
        <div className="flex-grow-1" style={{ paddingLeft: "300px" }}><h1><i>Login</i></h1></div>
        <div className="pr-4"><Link to="/admin-signin"> <button className="my-2 mx-auto btn btn-dark"> Admin Login</button></Link></div>
      </div>
      <div className="container my-3 py-3">
        <div className="row my-4 h-100">
          <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
            <form onSubmit={handleLogin}>
              <div className="my-3">
                <label htmlFor="floatingInput">Email address</label>
                <input
                  type="text"
                  className="form-control"
                  id="floatingInput"
                  placeholder="User Name"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </div>
              <div className="my-3">
                <label htmlFor="floatingPassword">Password</label>
                <div className="input-group">
                  <input
                    type={showPassword ? "text" : "password"}
                    className="form-control"
                    id="floatingPassword"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                  {password !== "" && <button
                    className="btn btn-outline-secondary"
                    type="button"
                    onClick={togglePasswordVisibility}
                  >
                    👁
                  </button>}
                </div>
              </div>
              <div className="my-3">
                <p>New Here? <Link to="/register" className="text-decoration-underline text-info">Register</Link> </p>
              </div>
              <div className="text-center">
                <button className="my-2 mx-auto btn btn-dark" type="submit">
                  Login
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <Footer />
      <ToastContainer />
    </>
  );
};

export default Login;
