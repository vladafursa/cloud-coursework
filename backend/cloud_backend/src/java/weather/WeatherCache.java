/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weather;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import locationConverter.LocationJava;
import locationConverter.LocationService;
import room.DatabaseConnection;
import room.RoomJava;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.mongodb.client.model.Filters;
import locationConverter.LocationCache;
/**
 *
 * @author vladafursa
 */
public class WeatherCache {
    Gson gson = new GsonBuilder().setPrettyPrinting()
            .create();
    WeatherJava weather;
    LocationCache locationService=new LocationCache();
    LocationJava convLocation;
   // LocationService locationService = new LocationService();
    WeatherService weatherService = new WeatherService();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String todayDate = sdf.format(Calendar.getInstance().getTime());
    
    
    
    public WeatherJava getCachedData(RoomJava room) throws Exception{
    try {
        /*searching for document with room's id*/
        MongoCollection<Document> weatherCollection = DatabaseConnection.getCollection("weather");
        Document roomsWeatherDocument = weatherCollection.find(new Document("_id", room.getId())).first();
        /*if this document exists in a database(cache), retrieve directly from database*/
        if(roomsWeatherDocument!=null){
            Document bsonWeatherData = roomsWeatherDocument.get("weather", Document.class);
            String jsonWeatherResponse = gson.toJson(bsonWeatherData);
            weather = gson.fromJson(jsonWeatherResponse, WeatherJava.class);

            /*update the weather daily*/
            String firstDateInCachedMemory = String.valueOf(weather.getDataseries()[0].getDate());
            if(!todayDate.equals(firstDateInCachedMemory)){
                    convLocation = locationService.getCachedLocationData(room.getLocation().getPostcode());
                    String latitude=String.valueOf( convLocation.getData().getLatitude());
                    String longitude=String.valueOf( convLocation.getData().getLongitude());
                    
                    
                    try{
                    weather = weatherService.getWeatherData(latitude, longitude);
                    
                        try{
                            String weatherJson = gson.toJson(weather);
                            Document weatherDocument = Document.parse(weatherJson);

                            Document newWeather = new Document("_id", room.getId())
                                .append("weather", weatherDocument);
                            weatherCollection.replaceOne(Filters.eq("_id", roomsWeatherDocument.get("_id")), newWeather);
                        }
                        /*if there are issues with writing to database, ignore as weather was already retrieved*/
                        catch(Exception UpdatingDatabaseData){
                            UpdatingDatabaseData.printStackTrace();
                        }
                    
                    }
                    /*if weather service not responding, clear old data and retrieve what is still relevant*/
                    catch(Exception notRespondingService){
                        /*delete dataseries that are older than today*/
                        List<WeatherJava.Datasery> filteredList = new ArrayList<>();
                        for (WeatherJava.Datasery data : weather.getDataseries()) {
                            if (String.valueOf(data.getDate()).compareTo(todayDate) >= 0) {
                                filteredList.add(data); 
                            }
                        }
                        
                        weather.setDataseries(filteredList.toArray(new WeatherJava.Datasery[0]));
                        /*if nothing is left*/    
                        if (weather.getDataseries().length == 0){
                            throw new Exception("service is unavailable and there is no cache data");
                        }
                        else{
                            /*update cach with remaining dataseries that can still be used*/
                            try{
                            String weatherJson = gson.toJson(weather);
                            Document weatherDocument = Document.parse(weatherJson);
                            
                            Document newWeather = new Document("_id", room.getId())
                                .append("weather", weatherDocument);
                            weatherCollection.replaceOne(Filters.eq("_id", roomsWeatherDocument.get("_id")), newWeather);
                            }
                            /*if there are issues with writing to database, ignore as weather was already retrieved*/
                            catch(Exception updateDatabaseData){
                                updateDatabaseData.printStackTrace();
                            }
                            
                        }
                    }
                    
                    
            }
        return weather;
        }
        
        /*if the document doesn't exist, call WeatherService and return weather from there, saving it into database*/
        else{
            try {
                convLocation = locationService.getCachedLocationData(room.getLocation().getPostcode());
                String latitude=String.valueOf( convLocation.getData().getLatitude());
                String longitude=String.valueOf( convLocation.getData().getLongitude());
                weather = weatherService.getWeatherData(latitude, longitude);
                
                try{
                String weatherJson = gson.toJson(weather);
                Document weatherDocument = Document.parse(weatherJson);

                Document newWeather = new Document("_id", room.getId())
                    .append("weather", weatherDocument);
                weatherCollection.insertOne(newWeather);
                }
                /*if there are issues with writing to database, ignore as weather was already retrieved*/
                catch(Exception addDataToDatabase){
                addDataToDatabase.printStackTrace();
                }

                return weather;
            } /*something wrong with weatherService*/
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
            String latitude=String.valueOf( convLocation.getData().getLatitude());
            String longitude=String.valueOf( convLocation.getData().getLongitude());
            weather = weatherService.getWeatherData(latitude, longitude);
            return weather;
        }
        catch(Exception remainingException){
            remainingException.printStackTrace();
            throw new Exception("couldn't connect to the database and retrieve any data from a service"+remainingException.getMessage());
        }
        }
    }
}
