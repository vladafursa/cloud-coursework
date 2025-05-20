/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package locationConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
/**
 *
 * @author vladafursa
 */
public class LocationService {
    public LocationJava getLocationData(String postcode) throws Exception {
        String encodedPostcode = URLEncoder.encode(postcode, StandardCharsets.UTF_8);
        URL url = new URL("http://api.getthedata.com/postcode/" + encodedPostcode);
          int attempt=0;
        int waitingTime = 500;
        boolean success = false;
        
        while(attempt<3&&success==false){
            
            try{
               /*establishing connection*/
               HttpURLConnection con = (HttpURLConnection) url.openConnection();
               con.setRequestMethod("GET");
               con.connect();

               /*retrieving json*/
               BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
               StringBuilder response = new StringBuilder();
               String retrievedLine;
               while ((retrievedLine = input.readLine()) != null) {
                   response.append(retrievedLine);
               }
               input.close();
               int code =  con.getResponseCode();
               con.disconnect();
               System.out.println("code retrieved: ");
               System.out.println(code);

               /*if successfull deserialize into WeatherJava class*/
               if(code == 200){
               success = true;
               Gson gson = new GsonBuilder().setPrettyPrinting().create();
               return gson.fromJson(response.toString(), LocationJava.class);
               }
               else if(code == 504){
                attempt++;
                try {
                   Thread.sleep(waitingTime);
                   } 
                   catch (InterruptedException e1) {
                       e1.printStackTrace();
                   }
               }
               else{
               success = true;
               throw new Exception("something went wrong with the server, error code: "+code);
               }  

           }
           /*throw other exception, e.g. IOException*/
           catch(Exception e){
           success = true;
           throw new Exception("request failure "+e.getMessage());
           }   
            
        }
        throw new Exception("timeout of server, 3 attempts were tried");
    }
}