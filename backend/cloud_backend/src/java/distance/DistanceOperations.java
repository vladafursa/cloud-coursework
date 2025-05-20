/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distance;

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
 *
 * @author vladafursa
 */

@Path("distance")
public class DistanceOperations {
    
    @Context
    private UriInfo context;

    public DistanceOperations() {
 
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDistance(@QueryParam("lon1") String lon1, @QueryParam("lat1") String lat1, @QueryParam("lon2") String lon2, @QueryParam("lat2") String lat2) {
        try {
            DistanceService weatherService=new DistanceService();
            distanceJava distanceInfo = weatherService.getDistanceData(lon1,lat1,lon2,lat2);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String distanceData = gson.toJson(distanceInfo);
            return Response.ok(distanceData,MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace(); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error retrieving distance data: " + e.getMessage()).build();
        }
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}