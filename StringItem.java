import java.io.*;
import javax.imageio.*;

public class StringItem extends SuperItem {
    public static final String ITEMNAME = "STRING";

    public StringItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(true);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/String.png"));
        } catch (IOException e){
            
        }
    }
}