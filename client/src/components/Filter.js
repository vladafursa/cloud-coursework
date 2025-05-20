import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Filter = () => {
  const [city, setCity] = useState("");
  const [bills_included, setBills] = useState("");
  const [price_max, setPriceMax] = useState("");
  const navigate = useNavigate();

  const handleChange = () => {
    const queryParams = new URLSearchParams(); //retrieve parameters from url

    // Append only if the value was in url
    if (bills_included) queryParams.append("bills_included", bills_included);
    if (city) queryParams.append("city", city);
    if (price_max) queryParams.append("price_max", price_max);

    const queryString = queryParams.toString();

    // Navigate to the updated URL
    navigate(`/rooms?${queryString}`);
    window.location.reload(); //press to refresh
  };

  return (
    <div className="filterDiv">
      <div className="innerDivForCenter">
        <form
          className="formFilter"
          onSubmit={(e) => {
            e.preventDefault();
            handleChange();
          }}
        >
          <input
            className="filterOption"
            type="text"
            placeholder="City"
            value={city}
            onChange={(e) => setCity(e.target.value)}
          />
          <select
            className="filterOption"
            value={bills_included}
            onChange={(e) => setBills(e.target.value)}
          >
            <option value="">Bills Included?</option>
            <option value="true">Yes</option>
            <option value="false">No</option>
          </select>
          <input
            className="filterOption"
            type="number"
            placeholder="Max Price"
            value={price_max}
            onChange={(e) => setPriceMax(e.target.value)}
          />
          <button className="filterButton" type="submit">
            Apply Filters
          </button>
        </form>
      </div>
    </div>
  );
};

export default Filter;
