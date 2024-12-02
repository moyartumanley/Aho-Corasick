import java.util.ArrayList;

public class Tiktok implements Comparable<Tiktok>{
    private ArrayList<String> hashtags;
    private int playCount;

    public Tiktok(ArrayList<String> listOfHashtags, int playCount){
        this.hashtags = listOfHashtags;
        this.playCount = playCount;
    }

    /**
     * 
     * @return hashtags associated with a given Tiktok.
     */
    public ArrayList<String> getHashtags(){
        return this.hashtags;
    }

    /**
     * 
     * @return the playcount associated with a given Tiktok.
     */
    public int getPlayCount(){
        return this.playCount;
    }

    
    /**
     * Compares Tiktoks based on play count.
     * @param other Tiktok
     * @return 0 if tiktoks have the same play count, < 0 if this tiktok has a greater play count than the one being compared, > 0 if this tiktok has a less play count than the one being compared.
     */
    @Override
    public int compareTo(Tiktok other) {
        return Integer.compare(this.playCount, other.playCount);
    }
}
