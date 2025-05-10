import java.io.*;
import javax.imageio.*;

public class WineItem extends SuperItem {
    public static final String ITEMNAME = "WINE";

    public WineItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(false);
    }

    @Override
    public void loadImage() {
        try{
            sprite = (ImageIO.read(new File("./res/tileSets/TileSetDeco.png"))).getSubimage(5*16, 9*16, 16, 16);
        } catch (IOException e){
            
        }
    }
}