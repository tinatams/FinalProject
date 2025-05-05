import java.io.*;
import javax.imageio.*;

public class BoneItem extends SuperItem {
    public static final String ITEMNAME = "BONE";

    public BoneItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(false);
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/Bone.png"));
        } catch (IOException e){
            
        }
    }
}