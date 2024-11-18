
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataRetrieval {
 HttpRequest request;
 HttpResponse<String> response;
        public DataRetrieval() throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://tiktok-api23.p.rapidapi.com/api/post/trending?count=30"))
            .header("x-rapidapi-key", "a0d4aa079amshc55bf227b756ba9p13d494jsnde4620d6ce19")
            .header("x-rapidapi-host", "tiktok-api23.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body());
        }
    
        public static void main(String[] args) throws IOException, InterruptedException {
            DataRetrieval data = new DataRetrieval();
                
            System.out.println(data.response);
    }
}


