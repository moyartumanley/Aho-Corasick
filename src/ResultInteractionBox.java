
import edu.macalester.graphics.Rectangle;

public class ResultInteractionBox extends Rectangle{

    private String string;

    public ResultInteractionBox(double x, double y, double width, double height, String string){
        super(x,y,width,height);
        this.string = string;
    }

    public String getString(){
        return string;
    }
    
}
