import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import Navbar from "../components/Navbar";

function Crime() {
  const { _id } = useParams();
  const [crime, setCrime] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCrimeData = async () => {
      try {
        const response = await axios.get(
          `/cloud_backend/webresources/rooms/${_id}/crime`
        );
        console.log("Crime data:", response.data);
        setCrime(response.data);
        setLoading(false);
      } catch (error) {
        //response from orchestrator
        if (error.response) {
          alert(`Error: ${error.response.data}`);
        } //if couldn't connect to orchestator or received undefined response
        else {
          alert("Couldn't connect to the orchestrator");
        }
        setLoading(false);
      }
    };

    fetchCrimeData();
  }, []);

  if (loading)
    return (
      <div className="loading">
        <div className="loading">Loading crime...</div>
      </div>
    );

  if (!crime) return <p className="loading">No crime data available.</p>;

  return (
    <>
      <Navbar />
      {crime.map((item, index) => (
        <div key={index} className="crimeData">
          <h4>category: {item.category}</h4>
          <p>location: {item.location.street.name}</p>
          <p>month: {item.month}</p>
        </div>
      ))}
    </>
  );
}

export default Crime;
