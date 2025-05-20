/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package locationConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import distance.DistanceService;
import distance.distanceJava;
import java.util.UUID;
import org.bson.Document;
import room.DatabaseConnection;
import room.RoomJava;

/**
 *
 * @author vladafursa
 */
public class LocationCache {
    
    
    
    Gson gson = new GsonBuilder().setPrettyPrinting()
            .create();
    LocationJava convLocation;
    LocationService locationService = new LocationService();
    
  
    
    
    public LocationJava getCachedLocationData(String providedPostcode) throws Exception{
    try {
        /*searching for document with room's id and searched postcode*/
        MongoCollection<Document> locationCollection = DatabaseConnection.getCollection("converter");

        Document retrievedDocument = locationCollection.find(new Document("_id", providedPostcode)).first();
        if(retrievedDocument!=null){
            System.out.println("found");
            System.out.println("found");
            Document bsonLocationData = retrievedDocument.get("convertion", Document.class);
            String jsonLocationResponse = gson.toJson(bsonLocationData);
            convLocation = gson.fromJson(jsonLocationResponse, LocationJava.class);
            return convLocation;
        }
        
        /*if the document doesn't exist, call distanceService and return distance from there, saving it into database*/
        else{
            try {
                convLocation = locationService.getLocationData(providedPostcode);
                
                try{
                String convertedLocationJson = gson.toJson(convLocation);
                Document convertedLocationDocument = Document.parse(convertedLocationJson);
                

                Document newConvLocation = new Document("_id", providedPostcode)
                    .append("convertion", convertedLocationDocument); 
                    
                locationCollection.insertOne(newConvLocation);
                }
                
                /*if there are issues with writing to database, ignore as distance was already retrieved*/
                catch(Exception addDataToDatabase){
                addDataToDatabase.printStackTrace();
                }

            return convLocation;
            } /*something wrong with distanceService*/
            catch (Exception e) {
                 throw e;
            }
        
        }
            
    }
    
    /*database exceptions
    if couldn't connect to the database, try directly calling WeatherService
    */
    catch (Exception dataBaseConnectionAndOtherExceptions) {
        try{
            convLocation = locationService.getLocationData(providedPostcode);
            return convLocation;
        }
        catch(Exception remainingException){
            remainingException.printStackTrace();
            throw new Exception("couldn't connect to the database and retrieve any data from a service"+remainingException.getMessage());
        }
        }
    }
    
}

