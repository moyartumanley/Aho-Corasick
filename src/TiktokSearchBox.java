import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

public class TiktokSearchBox extends SearchBox{

    private List<Tiktok> tiktoks;

    public TiktokSearchBox(int width, List<Tiktok> tiktoks){
        super(width);
        this.tiktoks = tiktoks;

        Rectangle background = new Rectangle(0,0,width,HEIGHT);
        background.setFillColor(COLOR);
        background.setStroked(false);
        setBackground(background);

        GraphicsText text = new GraphicsText("|",5,HEIGHT / 3 * 2);
        setSearchBoxText(text);


        Set<String> listOfHashtags = new HashSet<String>();
        for(Tiktok t: tiktoks){
            listOfHashtags.addAll(t.getHashtags());
        }
        setWords(new AhoCorasick(listOfHashtags));
    }
}
