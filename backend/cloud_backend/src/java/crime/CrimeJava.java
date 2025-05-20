/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crime;

/**
 *
 * @author vladafursa
 */
public class CrimeJava {
    private String category;
    private String locationType;
    private Location location;
    private String context;
    private Object outcomeStatus;
    private String persistentID;
    private long id;
    private String locationSubtype;
    private String month;

    public String getCategory() { return category; }
    public void setCategory(String value) { this.category = value; }

    public String getLocationType() { return locationType; }
    public void setLocationType(String value) { this.locationType = value; }

    public Location getLocation() { return location; }
    public void setLocation(Location value) { this.location = value; }

    public String getContext() { return context; }
    public void setContext(String value) { this.context = value; }

    public Object getOutcomeStatus() { return outcomeStatus; }
    public void setOutcomeStatus(Object value) { this.outcomeStatus = value; }

    public String getPersistentID() { return persistentID; }
    public void setPersistentID(String value) { this.persistentID = value; }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getLocationSubtype() { return locationSubtype; }
    public void setLocationSubtype(String value) { this.locationSubtype = value; }

    public String getMonth() { return month; }
    public void setMonth(String value) { this.month = value; }


public class Location {
    private String latitude;
    private Street street;
    private String longitude;

    public String getLatitude() { return latitude; }
    public void setLatitude(String value) { this.latitude = value; }

    public Street getStreet() { return street; }
    public void setStreet(Street value) { this.street = value; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String value) { this.longitude = value; }
}


public class Street {
    private long id;
    private String name;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
}