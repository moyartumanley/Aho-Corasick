import edu.macalester.graphics.*;
import java.awt.Color;
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
    private final int WIDTH;
    private final Color COLOR = new Color(241,241,241);

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

        searchBox = new GraphicsText("|",5,HEIGHT / 3 * 2);
        add(searchBox);

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

        searchBox = new GraphicsText("|",5,HEIGHT / 3 * 2);
        add(searchBox);

        words = wordTree;
    }

    /**
     * Adds the character to the end of the search box
     * @param c
     */
    public void addCharacter(Character c){
        text += c;
        searchBox.setText(text + "|");
        updateResults();
    }

    /**
     * Deletes the last character in the search box.
     */
    public void deleteLastCharacter(){
        if (text.length() > 0){
            text = text.substring(0, text.length() - 1);
            searchBox.setText(text + "|");
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

    /**
     * Creates a text box for a result
     * @param result text
     * @param y coordinate of the box
     */
    private void makeResultBox(String result, int y){
        Rectangle background = new Rectangle(0,y,WIDTH,HEIGHT); 
        background.setFillColor(COLOR);
        add(background);

        GraphicsText text = new GraphicsText(result,5,HEIGHT / 3 * 2 + y);
        add(text);
    }

    /**
     * Clears all results.
     */
    private void clearResults(){
        removeAll();
        add(background);
        add(searchBox);

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
