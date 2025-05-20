import { BrowserRouter, Routes, Route } from "react-router-dom";
import Applications from "./pages/Applications";
import ApplicationInfo from "./pages/ApplicationInfo";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Listing from "./pages/Listing";
import RoomDetails from "./pages/RoomDetails";
import Weather from "./pages/Weather";
import Crime from "./pages/Crime";
import { AuthProvider } from "./pages/AuthContext";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/rooms" element={<Listing />} />
          <Route path="/applications/:_id" element={<Applications />} />
          <Route path="/application-info/:_id" element={<ApplicationInfo />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/rooms/:_id" element={<RoomDetails />} />
          <Route path="/rooms/:_id/weather" element={<Weather />} />
          <Route path="/rooms/:_id/crime" element={<Crime />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
