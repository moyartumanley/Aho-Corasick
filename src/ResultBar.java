import java.awt.Color;

import edu.macalester.graphics.*;

public class ResultBar extends GraphicsGroup {
    public ResultBar(String result){
        super();

        Rectangle background = new Rectangle(0,0,450,30); 
        background.setFillColor(Color.WHITE);
        background.setStroked(false);
        add(background);

        GraphicsText results = new GraphicsText(result,5,30 / 3 * 2);
        add(results);
    }
}
