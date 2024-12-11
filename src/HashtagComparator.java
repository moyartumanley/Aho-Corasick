import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;

public class HashtagComparator implements Comparator<String>{

    @Override
    public int compare(String hashtag1, String hashtag2) {
        DataParser dataParser = new DataParser();
        
        HashMap<String, Integer> hashtagMap = new HashMap<>();
        try {
            hashtagMap = dataParser.getHashtagMap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int hashtag1Views = hashtagMap.get(hashtag1);
        int hashtag2Views = hashtagMap.get(hashtag2);

        if (hashtag1Views < hashtag2Views){
            return 1; //We want views to be sorted from most to least
        }
        else if(hashtag1Views > hashtag2Views){
            return -1;
        }
        else{
            return hashtag1.compareTo(hashtag2); 
        }

    }
    
}
