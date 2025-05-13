import java.io.*;
import javax.imageio.*;

public class WingItem extends SuperItem {
    public static final String ITEMNAME = "WINGS";

    public WingItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(false);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/Wings.png"));
        } catch (IOException e){
            
        }
    }
}