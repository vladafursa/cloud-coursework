import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../pages/AuthContext";

const ApplyButton = ({ user_id, room_id }) => {
  const navigate = useNavigate();
  //retrieve data about current state of loging
  const { userId, isLoggedIn } = useAuth();
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isLoggedIn && userId) {
      try {
        const response = await axios.post(
          "/cloud_backend/webresources/applications/apply",
          {
            user_id: user_id,
            room_id: room_id,
          }
        );
        if (response.status === 200) {
          alert("application is submitted!");
        }
      } catch (error) {
        //response from orchestrator
        if (error.response) {
          alert(`Error: ${error.response.data}`);
        } //if couldn't connect to orchestator or received undefined response
        else {
          alert("Couldn't connect to the orchestrator");
        }
      } //push the user to login before applying
    } else {
      navigate(`/login`);
    }
  };

  return (
    <div>
      <button className="externalButtons" onClick={handleSubmit}>
        Apply
      </button>
    </div>
  );
};

export default ApplyButton;
