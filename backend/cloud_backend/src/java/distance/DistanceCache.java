/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distance;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import locationConverter.LocationJava;
import locationConverter.LocationService;
import room.DatabaseConnection;
import room.RoomJava;
import java.util.UUID;
import locationConverter.LocationCache;
/**
 *
 * @author vladafursa
 */
public class DistanceCache {
    
    
    
    Gson gson = new GsonBuilder().setPrettyPrinting()
            .create();
    distanceJava distance;
    LocationJava convLocation;
    LocationCache locationService=new LocationCache();
    DistanceService distanceService = new DistanceService();
  
    
    
    public distanceJava getCachedDistanceData(RoomJava room, String providedPostcode) throws Exception{
    try {
        /*searching for document with room's id and searched postcode*/
        MongoCollection<Document> distanceCollection = DatabaseConnection.getCollection("distance");
        
        Document query = new Document();
        query.append("room_id", room.getId());
        query.append("searched_postcode", providedPostcode);
        Document retrievedDocument = distanceCollection.find(query).first();
        if(retrievedDocument!=null){
            System.out.println("found");
            System.out.println("found");
            Document bsonDistanceData = retrievedDocument.get("distance", Document.class);
            String jsonDistanceResponse = gson.toJson(bsonDistanceData);
            distance = gson.fromJson(jsonDistanceResponse, distanceJava.class);
            return distance;
        }
        
        /*if the document doesn't exist, call distanceService and return distance from there, saving it into database*/
        else{
            try {
                convLocation = locationService.getCachedLocationData(room.getLocation().getPostcode());
                String latitudeOfRoom=String.valueOf( convLocation.getData().getLatitude());
                String longitudeOfRoom=String.valueOf( convLocation.getData().getLongitude());
                convLocation = locationService.getCachedLocationData(providedPostcode);
                String aimedLatitude=String.valueOf( convLocation.getData().getLatitude());
                String aimedLongitude=String.valueOf( convLocation.getData().getLongitude());
                distance = distanceService.getDistanceData(longitudeOfRoom, latitudeOfRoom, aimedLongitude, aimedLatitude);

                try{
                String distanceJson = gson.toJson(distance);
                Document distanceDocument = Document.parse(distanceJson);
                String randomStringForId = UUID.randomUUID().toString().replace("-", "");

                Document newDistance = new Document("_id", randomStringForId)
                    .append("room_id", room.getId())
                    .append("searched_postcode", providedPostcode)    
                    .append("distance", distanceDocument); 
                    
                distanceCollection.insertOne(newDistance);
                }
                
                /*if there are issues with writing to database, ignore as distance was already retrieved*/
                catch(Exception addDataToDatabase){
                addDataToDatabase.printStackTrace();
                }

            return distance;
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
            convLocation = locationService.getCachedLocationData(room.getLocation().getPostcode());
            String latitudeOfRoom=String.valueOf( convLocation.getData().getLatitude());
            String longitudeOfRoom=String.valueOf( convLocation.getData().getLongitude());
            convLocation = locationService.getCachedLocationData(providedPostcode);
            String aimedLatitude=String.valueOf( convLocation.getData().getLatitude());
            String aimedLongitude=String.valueOf( convLocation.getData().getLongitude());
            distance = distanceService.getDistanceData(longitudeOfRoom, latitudeOfRoom, aimedLongitude, aimedLatitude);
            return distance;
        }
        catch(Exception remainingException){
            remainingException.printStackTrace();
            throw new Exception("couldn't connect to the database and retrieve any data from a service"+remainingException.getMessage());
        }
        }
    }
    
}
