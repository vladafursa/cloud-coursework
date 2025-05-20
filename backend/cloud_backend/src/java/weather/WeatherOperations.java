package weather;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import javax.ws.rs.core.Response;


/**
 * REST Web Service
 *
 * @author vladafursa
 */
@Path("weather")
public class WeatherOperations {
    
    @Context
    private UriInfo context;

    public WeatherOperations() {
 
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeather(@QueryParam("lat") String lat, @QueryParam("lon") String lon ) {
        try {
            WeatherService weatherService=new WeatherService();
            WeatherJava weatherInfo = weatherService.getWeatherData(lat, lon);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String weatherData = gson.toJson(weatherInfo);
            return Response.ok(weatherData, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace(); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error retrieving weather data: " + e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}