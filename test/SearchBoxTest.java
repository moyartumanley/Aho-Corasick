import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;

public class SearchBoxTest {

    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Search Box Test", 500, 500);
        canvas.setBackground(Color.WHITE);

        List<String> wordList = new ArrayList<>();
        wordList.add("red");
        wordList.add("read");
        wordList.add("dread");
        wordList.add("bead");
        wordList.add("eat");
        wordList.add("ear");
        wordList.add("dear");
        wordList.add("bear");
        wordList.add("fear");
        wordList.add("led");
        wordList.add("lead");
        wordList.add("leaf");
        wordList.add("earth");
        wordList.add("reed");
        wordList.add("priority");
        wordList.add("orbit");
        wordList.add("party");
        wordList.add("top");
        wordList.add("trending");
        wordList.add("tend");
        wordList.add("ending");
        wordList.add("endings");

        SearchBox search = new SearchBox(450, wordList);
        search.setCenter(canvas.getWidth()/2,canvas.getHeight()/4);
        canvas.add(search);

        search.addPrioritySearch("priority");
        search.addPrioritySearch("trending");

        canvas.onCharacterTyped(event -> search.addCharacter(event.charValue()));
        canvas.onKeyDown(event -> {
            if (event.getKey().equals(Key.DELETE_OR_BACKSPACE)){
                search.deleteLastCharacter();
            };});
    }
    
}
