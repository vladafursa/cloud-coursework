import React, { useState } from "react";
import axios from "axios";

function CancelButton({ applicationId }) {
  const handleUpdateStatus = async () => {
    try {
      const response = await axios.put(
        `/cloud_backend/webresources/applications/${applicationId}/status`,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (response.status === 200) {
        alert("cancelled application");
        window.location.reload();
      }
    } catch (error) {
      //response from orchestrator
      if (error.response) {
        alert(`Error: ${error.response.data}`);
      } //if couldn't connect to orchestator or received undefined response
      else {
        alert(`Couldn't connect to the orchestrator`);
      }
    }
  };

  return (
    <div>
      <button className="cancelButton" onClick={handleUpdateStatus}>
        Cancel application
      </button>
    </div>
  );
}

export default CancelButton;
