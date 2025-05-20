import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "../components/Navbar";
import CancelButton from "../components/CancelButton";
function ApplicationInfo() {
  const { _id } = useParams(); // get id from url
  const [application, setApplication] = useState(null);

  useEffect(() => {
    const fetchApplicationData = async () => {
      try {
        console.log("id retrieved:", _id); // Check the value of _id
        const response = await axios.get(
          `/cloud_backend/webresources/applications/${_id}`
        );
        console.log("application data:", response.data);
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

  if (!application) {
    return (
      <div className="loading">
        <div className="loading">Loading application data</div>
      </div>
    );
  }

  return (
    <>
      <Navbar></Navbar>
      <div className="applicationInfo">
        <p>Application ID: {application._id}</p>
        <p>
          Room ID applied for:{" "}
          <a href={`/rooms/${application.room_id}`}>{application.room_id}</a>
        </p>
        <p>Application status: {application.status}</p>
        <p>Application date: {application.date}</p>
      </div>
      <CancelButton applicationId={application._id}></CancelButton>
    </>
  );
}

export default ApplicationInfo;
