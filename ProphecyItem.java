import java.io.*;
import javax.imageio.*;

public class ProphecyItem extends SuperItem {
    public static final String ITEMNAME = "PROPHECY";

    public ProphecyItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(false);
    }

    @Override
    public void loadImage() {
        try{
            sprite = (ImageIO.read(new File("./res/items/paper.png"))).getSubimage(5, 9, 16, 16);
        } catch (IOException e){
            
        }
    }
}