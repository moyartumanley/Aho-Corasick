import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// setup get data for object put that object into a list/data structure then alter algo
// dont worry about unicode errors rn
public class DataParser {

    private ArrayList<Tiktok> listOfTiktoks;

    /**
     * Reads data and creates Tiktok objects containing relevant stats.
     * 
     * @param filePath
     */
    private void readData(String filePath) {
        this.listOfTiktoks = new ArrayList<>();
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject data = new JSONObject(fileContent);
            JSONArray videos = data.getJSONArray("itemList");
            for (int i = 0; i < videos.length(); i++) {
                ArrayList<String> listOfHashtags = new ArrayList<>();
                JSONObject video = videos.getJSONObject(i);
                JSONObject stats = video.getJSONObject("statsV2");
                // String description = video.getString("desc");
                String playCount = stats.getString("playCount");

                if (video.has("textExtra")) {
                    JSONArray extraText = video.getJSONArray("textExtra");
                    for (int j = 0; j < extraText.length(); j++) {
                        JSONObject text = extraText.getJSONObject(j);
                        String hashtagName = text.getString("hashtagName");
                        listOfHashtags.add(hashtagName);
                    }
                }

                Tiktok tiktok = new Tiktok(listOfHashtags, Integer.parseInt(playCount));
                listOfTiktoks.add(tiktok);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            System.err.println("Error reading or parsing JSON file: " + e.getMessage());
        }
    }

    /**
     * Returns a list of Tiktoks collected from API data.
     * 
     * @throws InterruptedException
     * @throws IOException
     */
    public ArrayList<Tiktok> processData() throws InterruptedException, IOException {

        /*
         * Writes JSON contents to a file. Only have to uncomment if you want to collect updated data from
         * the API.
         */
        // DataDownloader downloader = new DataDownloader();
        // downloader.writeToFile();

        /* File address of JSON file. */
        String filePath = "./res/data.json";

        try {
            readData(filePath);
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("Error reading or parsing JSON file: " + e.getMessage());

        }

        // System.out.println(listOfTiktoks.toString());

        return this.listOfTiktoks;
    }

    public HashMap<String, Integer> getHashtagMap() throws InterruptedException, IOException {
        HashMap<String, Integer> hashtagMap = new HashMap<>();
        processData();
        for (Tiktok tiktok : listOfTiktoks) {
            ArrayList<String> hashtags = tiktok.getHashtags();
            int playCount = tiktok.getPlayCount();
            for (String hashtag : hashtags) {
                if (!hashtagMap.containsKey(hashtag)) {
                    hashtagMap.put(hashtag, playCount);
                } else {
                    int newViews = hashtagMap.get(hashtag) + playCount;
                    hashtagMap.put(hashtag, newViews);
                }
            }
        }
        return hashtagMap;
    }
}
