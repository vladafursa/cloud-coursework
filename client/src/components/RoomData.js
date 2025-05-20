import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import ApplyButton from "./ApplyButton";
import { useAuth } from "../pages/AuthContext";

function RoomData() {
  const { _id } = useParams(); // get room id from url
  const [room, setRoom] = useState(null);

  const [loading, setLoading] = useState(true);
  const { userId, isLoggedIn } = useAuth(); //to check before apply
  useEffect(() => {
    const fetchRoomData = async () => {
      try {
        const response = await axios.get(
          `/cloud_backend/webresources/rooms/${_id}`
        );
        console.log("Room data:", response.data);
        setRoom(response.data);
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
  }, [_id]);

  if (loading)
    return (
      <div className="loading">
        <div className="loading">Loading room details...</div>
      </div>
    );

  if (!room)
    return (
      <div className="loading">
        <div className="loading">No room data available</div>
      </div>
    );

  return (
    <>
      <div className="RoomData">
        <h1>{room.name}</h1>
        <p>
          Location: {room.location.city}, {room.location.county},{" "}
          {room.location.postcode}
        </p>
        <p>Price: £{room.price_per_month_gbp} per month</p>
        <p>Available since: {room.availability_date} </p>
        <p> bills insluded: {room.details?.bills_included ? "Yes" : "No"} </p>
        <p>Furnished: {room.details?.furnished ? "Yes" : "No"}</p>
        <p>Amenities: {room.details.amenities.join(", ")}</p>
        <p>Roommates: {room.details.shared_with} </p>
        <p>Shared bathroom: {room.details?.bathroom_shared ? "Yes" : "No"}</p>
        <p> Spoken languages: {room.spoken_languages.join(", ")} </p>
        <p>Landlord: {room.details?.live_in_landlord ? "Yes" : "No"}</p>
        <p>Current weather: {room.weather}</p>
        <p>Last crime: {room.lastCrime}</p>

        <a href={`/rooms/${_id}/weather`} className="externalButtons">
          Weather Forecast
        </a>

        <a href={`/rooms/${_id}/crime`} className="externalButtons">
          Crime base
        </a>
        <ApplyButton user_id={userId} room_id={_id}></ApplyButton>
      </div>
    </>
  );
}

export default RoomData;
