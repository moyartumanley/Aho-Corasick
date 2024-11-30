import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarException;

import org.json.*;

public class DataRetrieval {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        // JSONObject data = dataRetrieval();
        // File dataJSON = createFile();
        // writeToFile(data);
        DataParser parser = new DataParser();
        parser.processData();

    }
}

