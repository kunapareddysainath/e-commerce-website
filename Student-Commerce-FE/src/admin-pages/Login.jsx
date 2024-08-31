import React,{useState} from 'react'
import { backEndUrl} from "../config.js";
import axios from 'axios';
import { Footer } from "../components/index.js";
import { Link, useNavigate } from 'react-router-dom';

const Login = () => {

    
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const navigation = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
    
        try {
          const requestBody = {
            username: username,
            password: password
          }
          const response = await axios.post(backEndUrl + "/api/auth/adminLogin", requestBody);
    
          const { accessToken, userDetails } = response.data;
    
          localStorage.setItem("authToken", accessToken);
          localStorage.setItem("userId", userDetails.id);
          localStorage.setItem("username", userDetails.username);
          localStorage.setItem("name", userDetails.name);
          localStorage.setItem("phoneNumber", userDetails.phoneNumber);
          localStorage.setItem("emailAddress", userDetails.emailAddress);
          localStorage.setItem("role",userDetails.role.label);
    
          navigation("/admin-home")
    
        }
        catch (error) {
          console.error(error);
        }
      }

      
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  }

  return (
    <>
    <div className="navbar navbar-light bg-light d-flex align-items-center">
      <div className="pl-2"><h1><i>Student Ecommerce</i></h1></div>
      <div className="flex-grow-1" style={{ paddingLeft: "300px" }}><h1><i> Admin Login</i></h1></div>
      <div className="pr-4"><Link to="/"> <button className="my-2 mx-auto btn btn-dark"> User Login</button></Link></div>
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
  </>
  )
}

export default Login
