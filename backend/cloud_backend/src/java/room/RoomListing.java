package room;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import crime.CrimeCache;
import crime.CrimeJava;
import crime.CrimeService;
import distance.DistanceCache;
import java.time.LocalDate;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import weather.WeatherJava;
import weather.WeatherCache;
import distance.distanceJava;
import locationConverter.LocationJava;
import locationConverter.LocationService;

@Path("/rooms")
public class RoomListing {
Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .create();
List<RoomJava> rooms = new ArrayList<>();
WeatherCache cache = new WeatherCache();
DistanceCache distanceCache = new DistanceCache();
CrimeCache crimeCache = new CrimeCache();
WeatherJava weather;
distanceJava distance;
CrimeJava[] crime;


/*get all rooms with possible filtering*/
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response filterRooms(@QueryParam("bills_included") String bills_included, @QueryParam("city") String city,  @QueryParam("price_max") Long priceMax) {
        try {
            
            MongoCollection<Document> collection = DatabaseConnection.getCollection("rooms");
            
            /*constructing Document query for filtering*/
            Document query = new Document();
            
            if(bills_included!=null){
                boolean billsValue = Boolean.parseBoolean(bills_included);
                query.append("details.bills_included", billsValue);
            }
            
            if(city!=null){
                query.append("location.city", city);
            }
            
            if (priceMax != null) {
            Document priceQuery = new Document();
            
            priceQuery.append("$lte", priceMax);
            query.append("price_per_month_gbp", priceQuery);/* { price_per_month_gbp: { $lte: priceMax } }  less than or equal to*/
            }
            
            /*retrieving only filtered options*/
            FindIterable<Document> findIterable = collection.find(query);
            /*check if anything was found*/
            if(findIterable.first() != null ){
                /*constructing an array of Room class objects*/
                for (Document document : findIterable) {
                    /*convert BSON to JSON*/
                    String jsonRoom = document.toJson();
                    /*deserialize json to Java class*/
                    RoomJava room = gson.fromJson(jsonRoom, RoomJava.class);
                    
                    
                    try {
                    weather = cache.getCachedData(room);
                    room.setWeather(weather.getDataseries()[0].getWeather());
                    } catch (Exception e) {
                    return Response.status(Response.Status.BAD_GATEWAY)
                            .entity("Failed to fetch weather data: " + e.getMessage()).build();
                    }
                    
                    try {
                    crime = crimeCache.getCachedData(room);
                    room.setCrime(crime[0].getCategory());
                    } catch (Exception e) {
                    return Response.status(Response.Status.BAD_GATEWAY)
                            .entity("Failed to fetch crime data: " + e.getMessage()).build();
                    }
                    
                    
                    
                    rooms.add(room);
                }

                String jsonResponseRooms = gson.toJson(rooms);

                return Response.ok(jsonResponseRooms, MediaType.APPLICATION_JSON).build();
            }
            else{
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Rooms with such filters not found")
                        .build();
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Database connection error: " + e.getMessage())
                    .build();
        }
 }




/*retrieve specific room with passed id*/
@GET
@Path("{_id}")
@Produces(MediaType.APPLICATION_JSON)
public Response getRoomById(@PathParam("_id") String id) {
    try {
        /*retrieve document with specified id from database*/
        MongoCollection<Document> collection = DatabaseConnection.getCollection("rooms");
        Document roomDocument = collection.find(new Document("_id", id)).first();
        
        if (roomDocument != null) {
            /*convert BSON to JSON*/
            RoomJava room = gson.fromJson(roomDocument.toJson(), RoomJava.class);
            
            try {
            weather = cache.getCachedData(room);
            room.setWeather(weather.getDataseries()[0].getWeather());
            } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity("Failed to fetch weather data: " + e.getMessage()).build();
            }
            try {
            crime = crimeCache.getCachedData(room);
            room.setCrime(crime[0].getCategory());
            } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity("Failed to fetch crime data: " + e.getMessage()).build();
            }
            
            
            
            String jsonResponseRoom = gson.toJson(room);
            return Response.ok(jsonResponseRoom, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found with ID: " + id)
                    .build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database connection error: " + e.getMessage())
                .build();
    }
}




@GET
@Path("{_id}/weather")
@Produces(MediaType.APPLICATION_JSON)
public Response getWeatherForRoom(@PathParam("_id") String id) {
    try {
        MongoCollection<Document> collection = DatabaseConnection.getCollection("rooms");
        Document roomDocument = collection.find(new Document("_id", id)).first();
        RoomJava room = gson.fromJson(roomDocument.toJson(), RoomJava.class);
        try {
             weather = cache.getCachedData(room);
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity("Failed to fetch weather data: " + e.getMessage()).build();
        }
        return Response.ok(gson.toJson(weather), MediaType.APPLICATION_JSON).build();

    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database connection error: " + e.getMessage()).build();
    }       
}




@GET
@Path("{_id}/distance")
@Produces(MediaType.APPLICATION_JSON)
public Response getDistanceBetweenRoomAndCampus(@PathParam("_id") String id, @QueryParam("postcode") String postcode) {
    try {
        MongoCollection<Document> collection = DatabaseConnection.getCollection("rooms");
        Document roomDocument = collection.find(new Document("_id", id)).first();
        RoomJava room = gson.fromJson(roomDocument.toJson(), RoomJava.class);

        
        try {
            distance=distanceCache.getCachedDistanceData(room, postcode);            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity("Failed to fetch distance data: " + e.getMessage()).build();
        }       
        return Response.ok(gson.toJson(distance), MediaType.APPLICATION_JSON).build();

    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database connection error: " + e.getMessage()).build();
    }

       
}




@GET
@Path("{_id}/crime")
@Produces(MediaType.APPLICATION_JSON)
public Response getCrimeForRoom(@PathParam("_id") String id) {
    try {
        MongoCollection<Document> collection = DatabaseConnection.getCollection("rooms");
        Document roomDocument = collection.find(new Document("_id", id)).first();
        RoomJava room = gson.fromJson(roomDocument.toJson(), RoomJava.class);        
          try {        
            crime = crimeCache.getCachedData(room);
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity("Failed to fetch distance data: " + e.getMessage()).build();
        }

       
        return Response.ok(gson.toJson(crime), MediaType.APPLICATION_JSON).build();

    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database connection error: " + e.getMessage()).build();
    }

       
}

}

