/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crime;

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

@Path("crime")
public class CrimeOperations {
    
    @Context
    private UriInfo context;

    public CrimeOperations() {
 
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrime(@QueryParam("lat") String lat, @QueryParam("lon") String lon ) {
        try {
            CrimeService weatherService=new CrimeService();
            CrimeJava[] crimeInfo = weatherService.getCrimeData(lat, lon);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String crimeData = gson.toJson(crimeInfo);
            return Response.ok(crimeData,MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace(); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error retrieving crime data: " + e.getMessage()).build();
        }
    }
 
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}