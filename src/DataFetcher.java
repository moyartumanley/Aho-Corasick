import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class DataFetcher {
    
    /**
     * Retrieves data via an HTTP Request and returns the response as a JSON Object.
     * 
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private static HttpResponse<String> dataRetrieval() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://tiktok-api23.p.rapidapi.com/api/post/trending?count=30"))
            .header("x-rapidapi-key", "a0d4aa079amshc55bf227b756ba9p13d494jsnde4620d6ce19")
            .header("x-rapidapi-host", "tiktok-api23.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Returns status code of the response. A status code of 200 means the website is working.
        System.out.println(response.statusCode());
        return response;
    }

    /**
     * Converts an HTTP Response String into a JSON Object.
     * 
     * @param response
     * @return JSONObject
     * @throws IOException
     * @throws InterruptedException
     */
    public JSONObject getJSON() throws IOException, InterruptedException {
        HttpResponse<String> response = dataRetrieval();
        JSONObject dataObject;
        try {
            dataObject = new JSONObject(response.body());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return dataObject;
    }
}
