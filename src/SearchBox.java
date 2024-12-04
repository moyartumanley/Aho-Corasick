import edu.macalester.graphics.*;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchBox extends GraphicsGroup {

    private String text = "";
    private GraphicsText searchBoxText;
    private Rectangle background;
    private AhoCorasick words;
    private Set<String> prioritySearches = new HashSet<>();

    //TODO: Create a hash map from hastag to view count. Then in updateResults() sort the list by comparing the hashtag's values

    public final int HEIGHT = 30;
    public final int WIDTH;
    public final Color COLOR = new Color(241,241,241);

    /**
     * Creates a search box.
     * @param width
     * @param wordList
     */
    public SearchBox (int width){
        super();
        WIDTH = width;

        background = new Rectangle(0,0,width,HEIGHT);
        background.setFillColor(COLOR);
        background.setStroked(false);
        add(background);

        searchBoxText = new GraphicsText("|",5,HEIGHT / 3 * 2);
        add(searchBoxText);
    }

    /**
     * Creates a search box with a list of words as its data.
     * @param width
     * @param wordList
     */
    public SearchBox(int width, List<String> wordList){
        super();
        WIDTH = width;

        background = new Rectangle(0,0,width,HEIGHT);
        background.setFillColor(COLOR);
        background.setStroked(false);
        add(background);

        searchBoxText = new GraphicsText("|",5,HEIGHT / 3 * 2);
        add(searchBoxText);

        words = new AhoCorasick(wordList);
    }  

    /**
     * Creates a search box with a tree of words as its data.
     * @param width
     * @param wordList
     */
    public SearchBox(int width, AhoCorasick wordTree){
        super();
        WIDTH = width;

        background = new Rectangle(0,0,width,HEIGHT);
        background.setFillColor(Color.WHITE);
        add(background);

        searchBoxText = new GraphicsText("|",5,HEIGHT / 3 * 2);
        add(searchBoxText);

        words = wordTree;
    }

    /**
     * Set the words stored within the search box.
     * @param wordList
     */
    public void setWords(List<String> wordList){
        words = new AhoCorasick(wordList);
    }
     /**
     * Set the words stored within the search box.
     * @param wordList
     */
    public void setWords(AhoCorasick wordList){
        words = wordList;
    }

    public void setBackground(Rectangle background){
        this.background = background;
    }

    public void setSearchBoxText(GraphicsText text){
        this.searchBoxText = text;
    }

    /**
     * Adds the character to the end of the search box
     * @param c
     */
    public void addCharacter(Character c){
        text += c;
        searchBoxText.setText(text + "|");
        updateResults();
    }

    /**
     * Deletes the last character in the search box.
     */
    public void deleteLastCharacter(){
        if (text.length() > 0){
            text = text.substring(0, text.length() - 1);
            searchBoxText.setText(text + "|");
            updateResults();
        }
        
    }

    /**
     * Updates the result boxes based on what has been typed in the search box so far.
     */
    public void updateResults(){
        clearResults();
        if (!text.equals("")){
            int y = HEIGHT + 10;

            List<String> results = words.getWordsForPrefix(text);
            
            List<String> similarResults = words.searchNotPrefixSimilarWords(text).stream().filter(r -> !results.contains(r)).limit(10 - results.size()).collect(Collectors.toList());

            results.addAll(similarResults);

            List<String> priority = results.stream().filter(result -> prioritySearches.contains(result)).collect(Collectors.toList());
            
            results.removeAll(priority);
            priority.addAll(results);
        
            for(int i = 0; i < priority.size(); i++){
                makeResultBox(priority.get(i), y);
                y += HEIGHT;
            }
        }
    }

    /**
     * Creates a text box for a result
     * @param result text
     * @param y coordinate of the box
     */
    private void makeResultBox(String result, int y){
        Rectangle background = new Rectangle(0,y,WIDTH,HEIGHT); 
        background.setFillColor(Color.WHITE);
        add(background);

        GraphicsText text = new GraphicsText(result,5,HEIGHT / 3 * 2 + y);
        add(text);

        Image sideImage;
        if (prioritySearches.contains(result)){
            sideImage = new Image("UI elements" + File.separator + "UI icon_pastsearched.png");
        }
        else{
            sideImage = new Image("UI elements" + File.separator + "UI icon_search.png");
        }

        sideImage.setMaxHeight(HEIGHT - 10); 
        sideImage.setMaxWidth(HEIGHT - 10);
        sideImage.setPosition(WIDTH - sideImage.getWidth() - 10, y + 5);
        add(sideImage);
    }

    /**
     * Clears all results.
     */
    private void clearResults(){
        removeAll();
        add(background);
        add(searchBoxText);

    }

    /**
     * Adds a text to the priority, making it appear first if matched.
     * @param text
     */
    public void addPrioritySearch(String text){
        if(words.contains(text)){
            prioritySearches.add(text);
        }
        
    }
    


}
