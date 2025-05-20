/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;



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
import users.UserJava;
import room.DatabaseConnection;

/**
 *
 * @author vladafursa
 */
@Path("/users")
public class UserOperations {
     Gson gson = new GsonBuilder().setPrettyPrinting()
            .create();
    List<UserJava> users = new ArrayList<>();
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
      
        try {
            MongoCollection<Document> collection = DatabaseConnection.getCollection("users");

            FindIterable<Document> findIterable = collection.find();
            for (Document document : findIterable) {
                String json = document.toJson();
                UserJava user = gson.fromJson(json, UserJava.class);
                users.add(user);
            }

              String jsonResponse = gson.toJson(users);

            return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Database connection error: " + e.getMessage())
                    .build();
        }
    }
    
    
    
@POST
@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response registerUser(String retrievedUserJson) {
    UserJava providedUserData=gson.fromJson(retrievedUserJson, UserJava.class);
    if (providedUserData == null || providedUserData.getID() == null || providedUserData.getPassword() == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Invalid user data: ID or password is missing")
                .build();
    }

    System.out.println("Provided User Data: " + providedUserData.getID() + ", Password: " + providedUserData.getPassword());
   
    
    try {
        MongoCollection<Document> collection = DatabaseConnection.getCollection("users");
        Document foundUserDocument = collection.find(new Document("_id", providedUserData.getID())).first();
        if(foundUserDocument==null){
        Document newUser = new Document("_id", providedUserData.getID())
                .append("password", providedUserData.getPassword());
        collection.insertOne(newUser);
        return Response.status(Response.Status.CREATED)
                .entity("User registered successfully")
                .build();
        }
        else{
        return Response.status(Response.Status.CONFLICT)
                .entity("User already exists")
                .build();}

        
    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error registering user: " + e.getMessage())
                .build();
    }
}


@POST
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response authUser(String retrievedUserJson) {
    UserJava providedUserData=gson.fromJson(retrievedUserJson, UserJava.class);
    if (providedUserData == null || providedUserData.getID() == null || providedUserData.getPassword() == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Invalid user data: ID or password is missing")
                .build();
    }

    
    try {
        MongoCollection<Document> collection = DatabaseConnection.getCollection("users");
        
        Document userDocument = collection.find(new Document("_id", providedUserData.getID())).first();
 
        if (userDocument != null) {
            UserJava foundInDatabaseUser = new UserJava();
            foundInDatabaseUser.setID(userDocument.getString("_id"));
            foundInDatabaseUser.setPassword(userDocument.getString("password"));
            
            if(providedUserData.getPassword().equals(foundInDatabaseUser.getPassword())){
                return Response.ok()
                        .entity("successfully authentificated the user")
                        .build();
            }
            else{
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Incorrect password")
                    .build();
            }
            
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No user with such ID was found")
                    .build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error authentificating the user: " + e.getMessage())
                .build();
    }
}   
}