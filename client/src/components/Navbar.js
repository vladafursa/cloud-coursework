import React from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../pages/AuthContext";
function Navbar() {
  const navigate = useNavigate();
  const { userId, isLoggedIn } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isLoggedIn && userId) {
      navigate(`/applications/${userId}`);
    } else {
      navigate(`/login`);
    }
  };
  //retrieved from bootstrap
  return (
    <div>
      <nav className="navbar navbar-expand-lg ">
        <div className="container-fluid">
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <div>
                  <a className="navLink" href="/rooms">
                    Rooms
                  </a>
                </div>
              </li>
            </ul>
          </div>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <div>
                  <button className="ApplicationButton" onClick={handleSubmit}>
                    My Applications
                  </button>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
}

export default Navbar;
