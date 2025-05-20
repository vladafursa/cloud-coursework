import React, { useEffect, useState } from "react";
import Filter from "./Filter";
import { useLocation } from "react-router-dom";
import axios from "axios";

const RoomCards = () => {
  const [rooms, setRooms] = useState([]);
  const location = useLocation(); //current url
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    const queryParams = location.search; //sending parameters from url
    const fetchRoomData = async () => {
      try {
        //learned from https://www.youtube.com/watch?v=12l6lkW6JhE&t=285s
        const response = await axios.get(
          `/cloud_backend/webresources/rooms${queryParams}`
        );
        console.log("Room data:", response.data);
        setRooms(response.data);
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
    fetchRoomData();
  }, []);
  if (loading)
    return (
      <div className="loading">
        <div className="loading">Loading room details...</div>
      </div>
    );
  //accessed and modified from https://stackoverflow.com/questions/71120546/displaying-and-mapping-data-from-axios-api-call-into-react
  //cards from bootstrap
  return (
    <>
      <div className="card-container">
        {rooms && rooms.length > 0 ? (
          rooms.map((room, index) => (
            <div key={index} className="card" style={{ width: "18rem" }}>
              <div className="card-body">
                <h5 className="card-title">{room.name}</h5>
                <p className="card-text">{room.location?.city}</p>
                <p className="card-text">
                  Price: £{room.price_per_month_gbp}/month
                </p>
                <p className="card-text">
                  bills insluded: {room.details?.bills_included ? "Yes" : "No"}
                </p>
                <p className="card-text">Current weather: {room.weather}</p>
                <p className="card-text">
                  Spoken languages: {room.spoken_languages.join(", ")}
                </p>
                <a href={`/rooms/${room._id}`} className="btn btn-primary">
                  View Details
                </a>
              </div>
            </div>
          ))
        ) : (
          <p>No rooms available.</p>
        )}
      </div>
    </>
  );
};

export default RoomCards;
