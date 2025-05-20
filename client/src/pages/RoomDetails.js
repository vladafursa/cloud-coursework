import Navbar from "../components/Navbar";
import RoomData from "../components/RoomData";
import DistanceForm from "../components/DistanceForm";

function RoomDetails() {
  return (
    <>
      <Navbar />
      <RoomData />
      <div className="centerForm">
        <DistanceForm />
      </div>
    </>
  );
}

export default RoomDetails;
