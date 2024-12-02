import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

import java.util.function.Consumer;
import java.awt.Canvas;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

// orignal code lifted from Gesture Recognizer

public class UI {
    private GraphicsText searchSuggestions;
    private Image wifi;
    private Image searchBar;
    private Random r = new Random();

    public UI(CanvasWindow canvas){
        setupUI(canvas);
    }

     /**
     * Create the user interface
     */
    public void setupUI(CanvasWindow canvas){
        searchSuggestions = new GraphicsText(clockRandomizer());
        searchSuggestions.setFont(FontStyle.PLAIN, 25);
        canvas.add(searchSuggestions, 20,  40);

        wifi = new Image("UI elements" + File.separator + "UI wifi.png");
        wifi.setMaxHeight(60); 
        wifi.setMaxWidth(120);
        canvas.add(wifi, 480, 0);

        searchBar = new Image("UI elements" + File.separator + "UI searchbar.png");
        searchBar.setMaxHeight(100); 
        searchBar.setMaxWidth(600);
        canvas.add(searchBar, 0, 40);

    }

// method to get reasonable/ realistic clock time
// adds a 0 if needed to make it look like a proper analog clock
    private String clockRandomizer(){
        String result = "";
        int hour = r.nextInt(24); 
        int minute = r.nextInt(60);
        if (hour<10){
            result = "0";
        } 
        result += hour + ":";
        if (minute<10){
            result += "0";
        } 
        result += minute;
        return result;      
    }

    public static void main(String[] args){
        CanvasWindow canvas = new CanvasWindow("Trendmaxxer",600 , 900);
        UI window = new UI(canvas);
    }
}
