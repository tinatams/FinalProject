import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class BranchItem extends SuperItem implements Collidable{

    public BranchItem(int x, int y){
        super(x, y, 16, 16);
        super.hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);
    }

    @Override 
    public void loadImage(){
        try{
            sprite = ImageIO.read(new File("./res/Branch.png"));
        } catch (IOException e){
            
        }
    }

    @Override
    public boolean isColliding(Collidable c) {
        Rectangle itemHitBox = c.getHitBox();

        return hitBox.intersects(itemHitBox);
    }

    @Override
    public Collidable getCollidingWith(ArrayList<Collidable> items) {
        for (Collidable other: items){
            if (isColliding(other))
                return other;
        }

        return null;
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