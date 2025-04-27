import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class GrapeItem extends SuperItem{
    public static final String ITEMNAME = "GRAPE";

    public GrapeItem (int x, int y){
        super(ITEMNAME,x, y, 16, 16);
        super.hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/grape.png"));
        } catch (IOException e){
            
        }
    }
}