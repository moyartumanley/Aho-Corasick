import edu.macalester.graphics.*;
import java.awt.Color;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class SearchBox extends GraphicsGroup {

    private String text = "";
    private GraphicsText searchBoxText;
    private Rectangle background;
    private AhoCorasick words;
    private Deque<String> prioritySearches = new ArrayDeque<String>();
    private int totalHeight;

    public final int BOX_HEIGHT = 30;
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

        background = new Rectangle(0,0,width,BOX_HEIGHT);
        background.setFillColor(COLOR);
        background.setStroked(false);
        add(background);

        searchBoxText = new GraphicsText("|",5,BOX_HEIGHT / 3 * 2);
        add(searchBoxText);
        totalHeight = BOX_HEIGHT;
    }

    /**
     * Creates a search box with a list of words as its data.
     * @param width
     * @param wordList
     */
    public SearchBox(int width, List<String> wordList){
        super();
        WIDTH = width;

        background = new Rectangle(0,0,width,BOX_HEIGHT);
        background.setFillColor(COLOR);
        background.setStroked(false);
        add(background);

        searchBoxText = new GraphicsText("|",5,BOX_HEIGHT / 3 * 2);
        add(searchBoxText);

        setWords(new AhoCorasick(wordList));
    }  

    /**
     * Creates a search box with a tree of words as its data.
     * @param width
     * @param wordList
     */
    public SearchBox(int width, AhoCorasick wordTree){
        super();
        WIDTH = width;

        background = new Rectangle(0,0,width,BOX_HEIGHT);
        background.setFillColor(Color.WHITE);
        add(background);

        searchBoxText = new GraphicsText("|",5,BOX_HEIGHT / 3 * 2);
        add(searchBoxText);

        setWords(wordTree);
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
        totalHeight = BOX_HEIGHT;
        if (!text.equals("")){
            int y = BOX_HEIGHT + 10;

            totalHeight += 10;

            List<String> results = words.getWordsForPrefix(text);
            
            List<String> similarResults = words.searchNotPrefixSimilarWords(text).stream().filter(r -> !results.contains(r)).limit(10 - results.size()).collect(Collectors.toList());

            results.addAll(similarResults);

            List<String> priority = results.stream().filter(result -> prioritySearches.contains(result)).collect(Collectors.toList());
            
            results.removeAll(priority);
            priority.addAll(results);
        
            for(int i = 0; i < priority.size(); i++){
                makeResultBox(priority.get(i), y);
                y += BOX_HEIGHT;
            }
        }
    }

    /**
     * Creates a text box for a result
     * @param result text
     * @param y coordinate of the box
     */
    public void makeResultBox(String result, int y){
        Rectangle background = new Rectangle(0,y,WIDTH,BOX_HEIGHT); 
        background.setFillColor(Color.WHITE);
        add(background);

        GraphicsText resultText = new GraphicsText(result,5,BOX_HEIGHT / 3 * 2 + y);
        add(resultText);

        Image sideImage;
        if (prioritySearches.contains(result)){
            sideImage = new Image("UI elements" + File.separator + "UI icon_pastsearched.png");
        }
        else{
            sideImage = new Image("UI elements" + File.separator + "UI icon_search.png");
        }

        sideImage.setMaxHeight(BOX_HEIGHT - 10); 
        sideImage.setMaxWidth(BOX_HEIGHT - 10);
        sideImage.setPosition(WIDTH - sideImage.getWidth() - 10, y + 5);
        add(sideImage);

        totalHeight += BOX_HEIGHT;
    }

    /**
     * Clears all results.
     */
    public void clearResults(){
        removeAll();
        add(background);
        add(searchBoxText);

    }

    /**
     * Adds a text to the priority, making it appear first if matched.
     * @param text
     */
    public void addPrioritySearch(String text){
        if(words.contains(text) && !prioritySearches.contains(text)){
            prioritySearches.add(text);
        }
        if(prioritySearches.size() > 5){
            prioritySearches.remove();
        }
        updateResults();
    }

    public String getText(){
        return text;
    }

    public Deque<String> getPriority(){
        return prioritySearches;
    }

    public AhoCorasick getWords(){
        return words;
    }

    public int getTotalHeight(){
        return totalHeight;
    }
}
