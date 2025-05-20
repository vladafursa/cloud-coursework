import React, { useEffect, useState } from "react";
import axios from "axios";
function Distance({ _id, postcode }) {
  const [distance, setDistance] = useState(null);
  const [loading, setLoading] = useState(true);
  console.log(_id);
  console.log(postcode);

  useEffect(() => {
    const fetchDistanceData = async () => {
      try {
        const response = await axios.get(
          `/cloud_backend/webresources/rooms/${_id}/distance?postcode=${postcode}`
        );
        setDistance(response.data.routes[0]);
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

    fetchDistanceData();
  }, [_id, postcode]);

  if (loading)
    return (
      <div className="loading">
        <div className="loading">Loading distance</div>
      </div>
    );

  if (!distance) return <p className="loading">No distance data available.</p>;

  return (
    <>
      <div>
        <p>Duration: {distance.duration} seconds</p>
        <p>Distance: {distance.distance} meters</p>
      </div>
    </>
  );
}

export default Distance;
