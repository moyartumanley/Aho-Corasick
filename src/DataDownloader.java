import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class DataDownloader {
    static File dataFile;

    /**
     * Creates JSON file.
     */
    private static void createFile() {
        try {
            dataFile = new File("./res/data.json");
            if (dataFile.createNewFile()) {
                System.out.println("File created: " + dataFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            dataFile = null;
            System.out.println("An error occurred with file creation.");
            e.printStackTrace();
        }
    }

    /***
     * Writes a JSON object HTTP Response to a file.
     * 
     * @param HttpResponse
     * @throws InterruptedException
     * @throws IOException
     */
    public void writeToFile() throws InterruptedException, IOException {
        createFile();
        DataFetcher fetcher = new DataFetcher();
        JSONObject data = fetcher.getJSON();
        try {
            FileWriter writeToFile = new FileWriter(dataFile);
            writeToFile.write(data.toString());
            writeToFile.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
