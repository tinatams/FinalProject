import java.io.*;
import javax.imageio.*;

public class MeatItem extends SuperItem {
    public static final String ITEMNAME = "MEAT";

    public MeatItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(true);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/Meat.png"));
        } catch (IOException e){
            
        }
    }
}