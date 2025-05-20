/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package locationConverter;

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

@Path("locationConvertion")

public class LocationOperations {
        
    @Context
    private UriInfo context;

    public LocationOperations() {
 
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocationConvertion(@QueryParam("postcode") String postcode ) {
        try {
            LocationCache locationService=new LocationCache();
            LocationJava convertionInfo = locationService.getCachedLocationData(postcode);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String convertedData =  gson.toJson(convertionInfo);
            return Response.ok(convertedData,MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace(); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error retrieving location data: " + e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}