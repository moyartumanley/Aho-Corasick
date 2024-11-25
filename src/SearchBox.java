import edu.macalester.graphics.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchBox extends GraphicsGroup {

    private String text = "";
    private GraphicsText searchBox;
    private Rectangle background;
    private AhoCorasick words;
    private Set<String> prioritySearches = new HashSet<>();

    private final int HEIGHT = 30;
    private final int width;

    public SearchBox(int width){
        super();
        this.width = width;

        background = new Rectangle(0,0,width,HEIGHT); // make width parameter maybe
        background.setFillColor(Color.WHITE);
        add(background);

        searchBox = new GraphicsText("|",5,HEIGHT / 3 * 2);
        add(searchBox);
    }

    public SearchBox(int width, List<String> wordList){
        super();
        this.width = width;

        background = new Rectangle(0,0,width,HEIGHT);
        background.setFillColor(Color.WHITE);
        add(background);

        searchBox = new GraphicsText("|",5,HEIGHT / 3 * 2);
        add(searchBox);

        words = new AhoCorasick(wordList);
    }

    public SearchBox(int width, AhoCorasick wordTree){
        super();
        this.width = width;

        background = new Rectangle(0,0,width,HEIGHT);
        background.setFillColor(Color.WHITE);
        add(background);

        searchBox = new GraphicsText("|",5,HEIGHT / 3 * 2);
        add(searchBox);

        words = wordTree;
    }

    public void addCharacter(Character c){
        text += c;
        searchBox.setText(text + "|");
        updateResults();
    }

    public void deleteLastCharacter(){
        if (text.length() > 0){
            text = text.substring(0, text.length() - 1);
            searchBox.setText(text + "|");
            updateResults();
        }
        
    }

    public void updateResults(){
        clearResults();
        if (!text.equals("")){
            int y = HEIGHT;
            List<String> results = words.searchSimilarWords(text).stream().limit(10).collect(Collectors.toList());

            List<String> priority = results.stream().filter(result -> prioritySearches.contains(result)).collect(Collectors.toList());
            results.removeAll(priority);
            priority.addAll(results);
        
            for(int i = 0; i < priority.size(); i++){
                makeResultBox(priority.get(i), y);
                y += HEIGHT;
            }
        }
    }

    private void makeResultBox(String result, int y){
        Rectangle background = new Rectangle(0,y,width,HEIGHT); 
        background.setFillColor(Color.WHITE);
        background.setStroked(false);
        add(background);

        GraphicsText text = new GraphicsText(result,5,HEIGHT / 3 * 2 + y);
        add(text);
    }

    private void clearResults(){
        removeAll();
        add(background);
        add(searchBox);

    }

    public void addPrioritySearch(String text){
        if(words.contains(text)){
            prioritySearches.add(text);
        }
        
    }
    


}
