import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

public class TiktokSearchBox extends SearchBox{

    public TiktokSearchBox(int width){
        super(width);

        DataParser dataParser = new DataParser();

        List<Tiktok> tiktoks = new ArrayList<Tiktok>();
        try {
            tiktoks = dataParser.processData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



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

    @Override
    public void updateResults(){
        clearResults();
        if (!getText().equals("")){
            int y = HEIGHT + 10;

            List<String> results = getWords().getWordsForPrefix(getText());
            Collections.sort(results, new HashtagComparator());
            
            List<String> similarResults = getWords().searchNotPrefixSimilarWords(getText())
                                                    .stream()
                                                    .filter(r -> !results.contains(r))
                                                    .limit(10 - results.size())
                                                    .collect(Collectors.toList());
            Collections.sort(similarResults, new HashtagComparator());

            results.addAll(similarResults);

            List<String> priority = results.stream().filter(result -> getPriority().contains(result)).collect(Collectors.toList());
            Collections.sort(priority, new HashtagComparator());
            
            results.removeAll(priority);
            priority.addAll(results);
        
            for(int i = 0; i < priority.size(); i++){
                makeResultBox(priority.get(i), y);
                y += HEIGHT;
            }
        }
    }
}
