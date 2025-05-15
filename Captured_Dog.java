import java.io.*;
import javax.imageio.*;

public class Captured_Dog extends SuperItem {
    public static final String ITEMNAME = "CAPTURED_DOG";

    public Captured_Dog(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(true);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/NPCs/Dog.png"));
        } catch (IOException e){
            
        }
    }
}