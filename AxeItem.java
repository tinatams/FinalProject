import java.io.*;
import javax.imageio.*;

public class AxeItem extends SuperItem {
    public static final String ITEMNAME = "AXE";

    public AxeItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(false);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/Axe.png"));
        } catch (IOException e){
            
        }
    }
}