package room;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javax.ws.rs.core.Response;
import org.bson.Document;

public class DatabaseConnection {
    private static String uri = "";
    private static MongoClient mongoClient;
    

    public static MongoCollection<Document> getCollection(String collectionName) throws Exception{
        /*Accessed and modified from https://www.mongodb.com/docs/drivers/java/sync/current/usage-examples/find/*/
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(uri);
                
            } catch (Exception e) {
                /*throw exception database exception*/
                throw e;
            }  
        }
        String databaseName = "dormitory";
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        return collection;
    }  
}
