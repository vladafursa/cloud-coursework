import React, { createContext, useState, useContext, useEffect } from "react";
//accessed and modified from https://www.youtube.com/watch?v=2-6K-TMA-nw

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const savedUserId = localStorage.getItem("userId");
    if (savedUserId) {
      setUserId(savedUserId);
      setIsLoggedIn(true);
    }
  }, []);

  const login = (id) => {
    setIsLoggedIn(true);
    setUserId(id);
    localStorage.setItem("userId", id);
  };

  const logout = () => {
    setIsLoggedIn(false);
    setUserId(null);
    localStorage.removeItem("userId");
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, userId, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
