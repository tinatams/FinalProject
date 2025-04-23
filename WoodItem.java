import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class WoodItem extends SuperItem{

    public WoodItem (int x, int y){
        super("WOOD",x, y, 16, 16);
        super.hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);
        // make this a set hitbox
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/wood.png"));
        } catch (IOException e){
            
        }
    }
}