import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class KeyItem extends SuperItem implements Interactable{
    private Rectangle interactionBox;
    private String lockName;

    public KeyItem(int x, int y, String n){
        super("KEY",x, y, 16, 16);
        super.hitBox = new Rectangle(worldX + 5 ,worldY +5,spriteH - 10, spriteW - 10);
        interactionBox = new Rectangle(worldX,worldY, spriteW, spriteH);
        setStackable(false);
        lockName = n;
    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/key.png"));
        } catch (IOException e){
            
        }
    }


    @Override
    public void interact(Player player) {
        player.collect(this);
        System.out.println("i own u");
    }

    @Override
    public Rectangle getInteractionBox() {
        return interactionBox;
    }

    @Override
    public int getWorldX() {
        return super.worldX;
    }

    @Override
    public int getWorldY() {
        return super.worldY;
    }

    @Override
    public int getSpriteW() {
        return super.spriteW;
    }

    @Override
    public int getSpriteH() {
        return super.spriteH;
    }

    @Override
    public Rectangle getHitBox() {
        return super.hitBox;
    }

    public String getLockName() {
        return lockName;
    }
}