import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import Navbar from "../components/Navbar";

function Weather() {
  const { _id } = useParams();
  const [weather, setWeather] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchWeatherData = async () => {
      try {
        const response = await axios.get(
          `/cloud_backend/webresources/rooms/${_id}/weather`
        );
        console.log("Weather data:", response.data.dataseries);
        setWeather(response.data.dataseries);
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

    fetchWeatherData();
  }, []);

  if (loading)
    return (
      <div className="loading">
        <div className="loading">Loading weather...</div>
      </div>
    );

  if (!weather) return <p className="loading">No weather data available.</p>; //check if it was rerieved
  if (!Array.isArray(weather)) {
    //check if it is in the right format and not null
    return <div className="loading">Loading...</div>;
  }

  return (
    <>
      <Navbar />
      <table className="table table-striped">
        <thead>
          <tr>
            <th>date</th>
            <th>weather</th>
            <th>max temperature</th>
            <th>min temperature</th>
            <th>max wind</th>
          </tr>
        </thead>

        {weather.map((item, index) => (
          <tbody>
            <tr key={item.date}>
              <td>{item.date}</td>
              <td>{item.weather}</td>
              <td>{item.temp2m.max}°C</td>
              <td>{item.temp2m.min}°C</td>
              <td>{item.wind10m_max} m/s</td>
            </tr>
          </tbody>
        ))}
      </table>
    </>
  );
}

export default Weather;
