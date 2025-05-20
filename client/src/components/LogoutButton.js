import React from "react";
import { useAuth } from "../pages/AuthContext";
import { useNavigate } from "react-router-dom";

const LogoutButton = () => {
  const { logout } = useAuth(); //retrieve method
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/rooms");
  };

  return (
    <button className="logoutButton" onClick={handleLogout}>
      Logout
    </button>
  );
};

export default LogoutButton;
