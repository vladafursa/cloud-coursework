import React, { useState } from "react";
import { useParams } from "react-router-dom";
import Distance from "../components/Distance";

function DistanceForm() {
  const { _id } = useParams(); // Retrieve id of room from the link
  const [postcode, setPostcode] = useState("");
  const [submittedPostcode, setSubmittedPostcode] = useState(null); // to prevent getting data while typing

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmittedPostcode(postcode);
  };

  return (
    <>
      <div className="DistanceDiv">
        <form className="distanceForm" onSubmit={handleSubmit}>
          <div>
            <label htmlFor="postcode">Postcode:</label>
            <input
              type="text"
              value={postcode}
              onChange={(e) => setPostcode(e.target.value)}
              required
            />
          </div>
          <button className="distanceButton" type="submit">
            Find Distance
          </button>
        </form>

        {/* show distance component */}
        <div className="DistanceResult">
          {submittedPostcode && (
            <Distance _id={_id} postcode={submittedPostcode} />
          )}
        </div>
      </div>
    </>
  );
}

export default DistanceForm;
