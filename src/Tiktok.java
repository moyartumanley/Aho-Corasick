import java.util.ArrayList;

public class Tiktok {
    private ArrayList<String> hashtags;
    private int playCount;

    public Tiktok(ArrayList<String> listOfHashtags, int playCount){
        this.hashtags = listOfHashtags;
        this.playCount = playCount;
    }

    public ArrayList<String> getHashtags(){
        return this.hashtags;
    }

    public int getPlayCount(){
        return this.playCount;
    }
}
