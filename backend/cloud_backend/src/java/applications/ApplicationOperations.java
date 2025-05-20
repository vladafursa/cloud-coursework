/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package applications;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.UUID;
import com.mongodb.client.result.UpdateResult;



import java.time.LocalDate;
import javax.ws.rs.PathParam;

import room.DatabaseConnection;
import room.LocalDateDeserializer;
import room.RoomJava;

/**
 *
 * @author vladafursa
 */
@Path("/applications")
public class ApplicationOperations {
    
    List<ApplicationJava> applications = new ArrayList<>();
    Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .create();
   
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplications() {
        try {
            MongoCollection<Document> collection = DatabaseConnection.getCollection("applications");            
            FindIterable<Document> findIterable = collection.find();
            for (Document document : findIterable) {
                String json = document.toJson();
                ApplicationJava application = gson.fromJson(json, ApplicationJava.class);
                applications.add(application);
            }
            String jsonResponse = gson.toJson(applications);
            return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace();
            // database issues
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Database connection error: " + e.getMessage())
                    .build();
        }
    }
    
    
@GET
@Path("/myapps")
@Produces(MediaType.APPLICATION_JSON)
public Response getUserApplication(@QueryParam("user_id") String id) {
    try {
        MongoCollection<Document> collection = DatabaseConnection.getCollection("applications");       
        FindIterable<Document> findIterable = collection.find(new Document("user_id", id));
            for (Document document : findIterable) {
                String json = document.toJson();
                System.out.print("retrieved " + json);
                ApplicationJava application = gson.fromJson(json, ApplicationJava.class);
                applications.add(application);
            }

              String jsonResponse = gson.toJson(applications);
              return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
       
    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database connection error: " + e.getMessage())
                .build();
    }
}
  

@GET
@Path("{_id}")
@Produces(MediaType.APPLICATION_JSON)
public Response getApplicationById(@PathParam("_id") String id) {
    try {
        MongoCollection<Document> collection = DatabaseConnection.getCollection("applications");       
        Document roomDocument = collection.find(new Document("_id", id)).first();

        if (roomDocument != null) {
            String jsonResponse = gson.toJson(roomDocument);
            return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Application not found with ID: " + id)
                    .build();
        }

    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database connection error: " + e.getMessage())
                .build();
    }
}
    
@POST
@Path("/apply")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response applyForRoom(String providedInfo) {
    try{
        MongoCollection<Document> collection = DatabaseConnection.getCollection("applications");
        
        ApplicationJava applicationReceived=gson.fromJson(providedInfo, ApplicationJava.class);
        String randomString="";
        boolean found = true;
        /*to avoid generating same id*/
        while(found){
        randomString = UUID.randomUUID().toString().replace("-", "");
        Document appDocument = collection.find(new Document("_id", randomString)).first();
            if(appDocument==null){
            found =false;}
        }
        
            Document newApplication = new Document("_id", randomString)
                    .append("user_id",applicationReceived.getUserID())
                    .append("room_id", applicationReceived.getRoomID())
                    .append("status", "pending")
                    .append("date", LocalDate.now().toString());
            collection.insertOne(newApplication);
            return Response.status(Response.Status.OK)
                    .entity("applied successfully")
                    .build();
        
    }
    catch(Exception e){
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database connection error: " + e.getMessage())
                .build();
    }
        
}




 @PUT
    @Path("/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateApplicationStatus(@PathParam("id") String id) {
        try{
            MongoCollection<Document> collection = DatabaseConnection.getCollection("applications");

            Document modificationDocument = collection.find(new Document("_id", id)).first();
            if(modificationDocument!=null){
                /* accessed and modified from https://www.mongodb.com/docs/manual/tutorial/update-documents/*/
                Document updatedDoc = new Document("$set", new Document("status", "cancelled"));

                UpdateResult result = collection.updateOne(
                    new Document("_id", id), 
                    updatedDoc);
                /*check the number of modified documents*/
                if (result.getModifiedCount() == 0) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Application with ID " + id + " was not modified")
                            .build();
                }

                return Response.status(Response.Status.OK)
                        .entity("Modified successfully")
                        .build();
            }
            else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Application was not found")
                        .build();
            }
        
        }

        catch(Exception e){
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Database connection error: " + e.getMessage())
                .build();
        }

        
    }
}
