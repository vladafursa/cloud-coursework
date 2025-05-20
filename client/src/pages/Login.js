import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";
import Navbar from "../components/Navbar";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { login } = useAuth();
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "/cloud_backend/webresources/users/auth",
        {
          _id: username,
          password: password,
        }
      );

      if (response.status === 200) {
        login(username);
        navigate(`/applications/${username}`);
      }
    } catch (error) {
      //response from orchestrator
      if (error.response) {
        alert(`Error: ${error.response.data}`);
      } //if couldn't connect to orchestator or received undefined response
      else {
        alert("Couldn't connect to the orchestrator");
      }
    }
  };

  return (
    <>
      <Navbar></Navbar>
      <div className="loginForm">
        <div className="innerLoginForm">
          <h1 className="authTitle">Login</h1>
          <form onSubmit={handleSubmit}>
            <div className="inputCredentials">
              <input
                className="inputField"
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Username"
              />
            </div>
            <div className="inputCredentials">
              <input
                className="inputField"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
              />
            </div>
            <button className="loginButton" type="submit">
              Login
            </button>
          </form>
          <p>
            Don't have an account yet? <a href="/register">Register</a>
          </p>
        </div>
      </div>
    </>
  );
};

export default Login;
