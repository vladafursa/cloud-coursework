import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "/cloud_backend/webresources/users/register",
        {
          _id: username,
          password: password,
        }
      );

      if (response.status === 201) {
        navigate("/Login"); // press to login
        alert(`Successfull registration`);
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
          <h1 className="authTitle">Register</h1>
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
              Register
            </button>
          </form>
        </div>
      </div>
    </>
  );
};

export default Register;
