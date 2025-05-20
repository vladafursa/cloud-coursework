import Navbar from "../components/Navbar";
import LogoutButton from "../components/LogoutButton";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { Link } from "react-router-dom";

function Applications() {
  const { _id } = useParams(); // Get the dynamic room ID from the URL
  const [applications, setApplication] = useState(null); // State to store room data

  useEffect(() => {
    // Fetch room data from the backend
    const fetchApplicationData = async () => {
      try {
        console.log("_id from URL:", _id);
        const response = await axios.get(
          `/cloud_backend/webresources/applications/myapps?user_id=${_id}`
        );
        console.log("Application data:", response.data);
        setApplication(response.data);
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

    fetchApplicationData();
  }, [_id]);

  return (
    <>
      <Navbar></Navbar>
      {applications && applications.length > 0 ? (
        applications.map((application, index) => (
          <div key={index} className="ApplicationList">
            <p>Application ID: {application._id}</p>
            <p>Room ID applied for:</p>
            <p>
              <a href={`/rooms/${application.room_id}`}>
                {application.room_id}
              </a>
            </p>

            <Link to={`/application-info/${application._id}`}>
              <button className="applicationButtons">View Application</button>
            </Link>
          </div>
        ))
      ) : (
        <p className="ApplicationList">No applications available.</p>
      )}
      <LogoutButton></LogoutButton>
    </>
  );
}
export default Applications;
