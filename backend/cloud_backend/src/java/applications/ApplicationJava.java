/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package applications;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;

/**
 *
 * @author vladafursa
 */
public class ApplicationJava {
    private String _id;
    private String user_id;
    private String room_id;
    private String status;
    private LocalDate date;

    public String getID() { return _id; }
    public void setID(String value) { this._id = value; }

    public String getUserID() { return user_id; }
    public void setUserID(String value) { this.user_id = value; }

    public String getRoomID() { return room_id; }
    public void setRoomID(String value) { this.room_id = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate value) { this.date = value; }

}