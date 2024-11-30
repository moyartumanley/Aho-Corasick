import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataParser {
    private static void readData(String filePath) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject data = new JSONObject(fileContent);
            JSONArray videos = data.getJSONArray("itemList");
            for (int i = 0; i < videos.length(); i++) {
                JSONObject video = videos.getJSONObject(i);
                JSONObject stats = video.getJSONObject("statsV2");
                String description = video.getString("desc");
                String playCount = stats.getString("playCount");
                System.out.println("Play count: " + playCount);
                System.out.println("Description: " + description);

                if (video.has("textExtra")) {
                    JSONArray extraText = video.getJSONArray("textExtra");
                    for (int j = 0; j < extraText.length(); j++) {
                        JSONObject text = extraText.getJSONObject(j);
                        String hashtagName = text.getString("hashtagName");
                        System.out.println("Hashtag Name:" + hashtagName);
                    }

                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            System.err.println("Error reading or parsing JSON file: " + e.getMessage());
        }
    }

    public void processData() throws InterruptedException, IOException {
        DataDownloader downloader = new DataDownloader();

        /*Writes JSON contents to a file.*/
        // downloader.writeToFile();

        /*File address of JSON file. */
        String filePath = "./res/data.json";

        try {
            readData(filePath);
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("Error reading or parsing JSON file: " + e.getMessage());

        }
    }
}
