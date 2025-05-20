/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weather;

import java.util.Set;

/**
 *
 * @author vladafursa
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(applications.ApplicationOperations.class);
        resources.add(crime.CrimeOperations.class);
        resources.add(distance.DistanceOperations.class);
        resources.add(locationConverter.LocationOperations.class);
        resources.add(room.RoomListing.class);
        resources.add(users.UserOperations.class);
        resources.add(weather.WeatherOperations.class);
    }
    
}
