package API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataRetrieval {
    static HttpRequest request;
    static HttpResponse<String> response;
        public DataRetrieval() throws IOException, InterruptedException {
            this.request = HttpRequest.newBuilder()
                .uri(URI
                    .create("https://tiktok-api23.p.rapidapi.com/api/trending/keyword?page=1&limit=20&period=7&country=US"))
                .header("x-rapidapi-key", "ef84af1d8dmsh972be03b853b284p17b768jsn77872985d9f6")
                .header("x-rapidapi-host", "tiktok-api23.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
           this.response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
    
        public static void main(String[] args) {
            System.out.println(response.body());
    }
}

