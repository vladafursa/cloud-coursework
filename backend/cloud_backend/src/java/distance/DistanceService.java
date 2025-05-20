/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distance;
import java.io.BufferedReader;
import java.io.IOException;
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
/**
 *
 * @author vladafursa
 */
public class DistanceService {
    public distanceJava getDistanceData(String longitude1, String latitude1, String longitude2, String latitude2) throws Exception {
        String coordinates=longitude1 +","+latitude1+";"+longitude2+","+latitude2;
        Map<String, String> parameters = new HashMap<>();
        parameters.put("alternatives", "false");
        parameters.put("steps", "false");
        parameters.put("overview", "false");
        /* accessed and modified from https://stackoverflow.com/questions/2809877/how-to-convert-map-to-url-query-string*/
         String convertedParamsToString = parameters.entrySet().stream()
        .map(entry -> {
        try {
            return URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding a parameter", e);
        }
      })
      .collect(Collectors.joining("&"));
        URL url = new URL("http://router.project-osrm.org/route/v1/driving/" + coordinates + "?" + convertedParamsToString);
        


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
               return gson.fromJson(response.toString(), distanceJava.class);
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



