import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class MeatItem extends SuperItem implements Collidable{

    public MeatItem(int x, int y){
        super(x, y, 15, 15);
        super.hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);
    }

    @Override 
    public void loadImage(){
        try{
            sprite = ImageIO.read(new File("./res/items/Meat.png"));
        } catch (IOException e){
            
        }
    }

    public int getWorldX(){
        return super.worldX;
    };

    public int getWorldY(){
        return super.worldY;
    };
    public int getSpriteW(){
        return super.spriteW;
    }

    public int getSpriteH(){
        return super.spriteH;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
}