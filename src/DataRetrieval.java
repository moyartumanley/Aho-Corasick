import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.jar.JarException;

import org.json.JSONObject;
import org.json.*;

public class DataRetrieval {

    /**
     * Retrieves data via an HTTP Request and returns the response as a JSON Object.
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static JSONObject dataRetrieval() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://tiktok-api23.p.rapidapi.com/api/post/trending?count=30"))
            .header("x-rapidapi-key", "a0d4aa079amshc55bf227b756ba9p13d494jsnde4620d6ce19")
            .header("x-rapidapi-host", "tiktok-api23.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        
        //Returns status code of the response. A status code of 200 means the website is working.
        System.out.println(response.statusCode());

        JSONObject dataJSON = getJSON(response);
        return dataJSON;
    }

    /**
     * Converts an HTTP Response String into a JSON Object.
     * @param response
     * @return JSONObject
     * @throws IOException
     * @throws InterruptedException
     */
    private static JSONObject getJSON(HttpResponse<String> response) throws IOException, InterruptedException {
        JSONObject dataObject;
        try {
            dataObject = new JSONObject(response.body());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return dataObject;
    }

    /**
     * Creates JSON file.
     */
    public static void createFile(){
        try {
            File dataJSON = new File("./res/data.json");
            if (dataJSON.createNewFile()) {
                System.out.println("File created: " + dataJSON.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred with file creation.");
            e.printStackTrace();
        }
    }

    /***
     * Writes a JSON object HTTP Response to a file.
     * @param HttpResponse
     * @throws InterruptedException
     */
    private static void writeToFile(JSONObject HttpResponse) throws InterruptedException {
        createFile();
        try {
            File dataJSON = new File("./res/data.json");
            FileWriter writeToFile = new FileWriter(dataJSON);
            writeToFile.write(HttpResponse.toString());
            writeToFile.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        JSONObject data = dataRetrieval();
        writeToFile(data);
    }
}

