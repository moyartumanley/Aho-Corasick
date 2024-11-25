import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

import java.util.function.Consumer;
import java.awt.Color;
import java.util.Random;
import java.io.File;

public class UI {
    private CanvasWindow canvas;
    private GraphicsGroup uiGroup;
    private GraphicsGroup interactGroup;
    private Button addTemplateButton;
    private TextField templateNameField;
    private GraphicsText searchSuggestions;
    private Image wifi;
    private Image searchBar;
    private Random r = new Random();

    public UI(){
        canvas = new CanvasWindow("Trendmaxxer", 600, 900);
        setupUI();
    }

     /**
     * Create the user interface
     */
    private void setupUI(){
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

// method to get reasonable clock time
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
        UI window = new UI();
    }
}
