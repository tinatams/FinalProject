import java.io.*;
import javax.imageio.*;

public class FishItem extends SuperItem {
    public static final String ITEMNAME = "FISH";

    public FishItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(true);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/Fish.png"));
        } catch (IOException e){
            
        }
    }
}