import java.io.*;
import javax.imageio.*;

public class PickaxeItem extends SuperItem {
    public static final String ITEMNAME = "PICKAXE";

    public PickaxeItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(false);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/Pickaxe.png"));
        } catch (IOException e){
            
        }
    }
}