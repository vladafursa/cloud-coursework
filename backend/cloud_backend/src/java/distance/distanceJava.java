/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distance;

/**
 *
 * @author vladafursa
 */
public class distanceJava {
    private String code;
    private Route[] routes;
    private Waypoint[] waypoints;

    public String getCode() { return code; }
    public void setCode(String value) { this.code = value; }

    public Route[] getRoutes() { return routes; }
    public void setRoutes(Route[] value) { this.routes = value; }

    public Waypoint[] getWaypoints() { return waypoints; }
    public void setWaypoints(Waypoint[] value) { this.waypoints = value; }
    
    public class Route {
    private Leg[] legs;
    private String weightName;
    private double weight;
    private double duration;
    private double distance;

    public Leg[] getLegs() { return legs; }
    public void setLegs(Leg[] value) { this.legs = value; }

    public String getWeightName() { return weightName; }
    public void setWeightName(String value) { this.weightName = value; }

    public double getWeight() { return weight; }
    public void setWeight(double value) { this.weight = value; }

    public double getDuration() { return duration; }
    public void setDuration(double value) { this.duration = value; }

    public double getDistance() { return distance; }
    public void setDistance(double value) { this.distance = value; }
}

// Leg.java


public class Leg {
    private Object[] steps;
    private String summary;
    private double weight;
    private double duration;
    private double distance;

    public Object[] getSteps() { return steps; }
    public void setSteps(Object[] value) { this.steps = value; }

    public String getSummary() { return summary; }
    public void setSummary(String value) { this.summary = value; }

    public double getWeight() { return weight; }
    public void setWeight(double value) { this.weight = value; }

    public double getDuration() { return duration; }
    public void setDuration(double value) { this.duration = value; }

    public double getDistance() { return distance; }
    public void setDistance(double value) { this.distance = value; }
}

public class Waypoint {
    private String hint;
    private double distance;
    private String name;
    private double[] location;

    public String getHint() { return hint; }
    public void setHint(String value) { this.hint = value; }

    public double getDistance() { return distance; }
    public void setDistance(double value) { this.distance = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public double[] getLocation() { return location; }
    public void setLocation(double[] value) { this.location = value; }
}
}