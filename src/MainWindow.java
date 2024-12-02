import java.util.ArrayList;
import java.util.List;
import edu.macalester.graphics.*;

import edu.macalester.graphics.events.Key;

public class MainWindow {
    private SearchBox searchBox;

    public MainWindow(CanvasWindow canvas){

        UI ui = new UI(canvas);

        List<String> wordList = new ArrayList<String>();
        wordList.add("red");
        wordList.add("read");
        wordList.add("dread");
        wordList.add("bead");

        searchBox = new SearchBox(370, wordList);
        searchBox.setCenter(canvas.getWidth() / 2,90);
        canvas.add(searchBox);

        canvas.add(searchBox);
        canvas.onCharacterTyped(event -> searchBox.addCharacter(event.charValue()));
        canvas.onKeyDown(event -> {
            if (event.getKey().equals(Key.DELETE_OR_BACKSPACE)){
                searchBox.deleteLastCharacter();
            };});
    }
    

    public static void main(String[] args){
        CanvasWindow canvas = new CanvasWindow("Trendmaxxer",600 , 900);
        MainWindow window = new MainWindow(canvas);
    }
}