/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crime;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import locationConverter.LocationJava;
import room.DatabaseConnection;
import room.RoomJava;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.lang.reflect.Type;
import locationConverter.LocationCache;
/**
 *
 * @author vladafursa
 */
public class CrimeCache {
    Gson gson = new GsonBuilder().setPrettyPrinting()
            .create();
    CrimeJava[] crime;
    LocationJava convLocation;
    LocationCache locationService = new LocationCache();
    CrimeService crimeService = new CrimeService();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    Calendar calendar = Calendar.getInstance();
    public CrimeJava[] getCachedData(RoomJava room) throws Exception{
    /*get a month that is 3 month earlier than current as API returns 2(?no data in documentation, but it is not fresh?) months old crime
        Accessed and modified from https://www.tutorialspoint.com/java/util/pdf/calendar_add.pdf
        */
    calendar.add(Calendar.MONTH, -3);
    String threeMonthsAgo = sdf.format(calendar.getTime());
    try {
        /*searching for document with room's id*/
        MongoCollection<Document> crimeCollection = DatabaseConnection.getCollection("crime");
        Document roomsCrimeDocument = crimeCollection.find(new Document("_id", room.getId())).first();
        /*if this document exists in a database(cache), retrieve directly from database*/
        if(roomsCrimeDocument!=null){
            
            Object bsonCrimeData = roomsCrimeDocument.get("crime");
            String jsonCrimeResponse = gson.toJson(bsonCrimeData);
            crime = gson.fromJson(jsonCrimeResponse, CrimeJava[].class);
            /*if month is older than API can retrieve*/
            if(crime[0].getMonth().compareTo(threeMonthsAgo) < 0){
                convLocation = locationService.getCachedLocationData(room.getLocation().getPostcode());
                String latitude=String.valueOf( convLocation.getData().getLatitude());
                String longitude=String.valueOf( convLocation.getData().getLongitude());
                try{
                crime = crimeService.getCrimeData(latitude, longitude);  
                    try{
                        /*replace data in database with fresh one*/
                        String crimeJson = gson.toJson(crime);
                        Type crimeListType = new TypeToken<List<Document>>() {}.getType();
                        List<Document> crimeList = gson.fromJson(crimeJson, crimeListType);

                        Document newCrime= new Document("_id", room.getId())
                              .append("crime", crimeList);
                          crimeCollection.replaceOne(
                            new Document("_id", room.getId()),  
                            newCrime);
                    }
                        /*if there are issues with writing to database, ignore as weather was already retrieved*/
                        catch(Exception UpdatingDatabaseData){
                            UpdatingDatabaseData.printStackTrace();
                        }
                }
                /*delete the whole collection as it is outdated and cannot be used*/
                catch(Exception NotRespondingService){
                    crimeCollection.deleteOne(new Document("_id", room.getId()));
                    throw new Exception("Service is not responding and the data in database is not actual anymore "+ NotRespondingService.getMessage());
                }
                
            }
            
            
            return crime;
        }
        
        /*if the document doesn't exist, call CrimeService and return crime from there, saving it into database*/
        else{
            try {
                convLocation = locationService.getCachedLocationData(room.getLocation().getPostcode());
                String latitude=String.valueOf( convLocation.getData().getLatitude());
                String longitude=String.valueOf( convLocation.getData().getLongitude());

                crime = crimeService.getCrimeData(latitude, longitude);
                
                try{
                String crimeJson = gson.toJson(crime);
                Type crimeListType = new TypeToken<List<Document>>() {}.getType();
                List<Document> crimeList = gson.fromJson(crimeJson, crimeListType);

                Document newCrime= new Document("_id", room.getId())
                    .append("crime", crimeList);
                crimeCollection.insertOne(newCrime);
                }
                /*if there are issues with writing to database, ignore as weather was already retrieved*/
                catch(Exception addDataToDatabase){
                addDataToDatabase.printStackTrace();
                }

                return crime;
            } /*something wrong with weatherService*/
            catch (Exception e) {
                 throw e;
            }
        
        }
        
    }
    
    /*database exceptions
    if couldn't connect to the database, try directly calling crimeService
    */
    catch (Exception dataBaseConnectionAndOtherExceptions) {
        try{
            convLocation = locationService.getCachedLocationData(room.getLocation().getPostcode());
            String latitude=String.valueOf( convLocation.getData().getLatitude());
            String longitude=String.valueOf( convLocation.getData().getLongitude());
            crime = crimeService.getCrimeData(latitude, longitude);
            return crime;
        }
        catch(Exception remainingException){
            remainingException.printStackTrace();
            throw new Exception("couldn't connect to the database and retrieve any data from a service"+remainingException.getMessage());
        }
        }
    }
}
