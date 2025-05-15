import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class StringItem extends SuperItem implements Interactable{
    public static final String ITEMNAME = "STRING";
    private Rectangle hitBox, interactionBox;
    private static boolean interactable=false;

    public StringItem(int x,int y){
        super(ITEMNAME,x, y, 16, 16);
        setStackable(true);
          hitBox = new Rectangle(0 ,0 ,0, 0);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);

    }

    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/String.png"));
        } catch (IOException e){
            
        }
    }
    @Override
    public void interact(Player player) {
        if(interactable){
         player.collect(this);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if(interactable){
          super.draw(g2d);
        }
    }

    @Override
    public int getWorldX() {
        return worldX;
    }

    @Override
    public int getWorldY() {
        return worldY;
    }

    @Override
    public int getSpriteW() {
        return spriteW;
    }

    @Override
    public int getSpriteH() {
        return spriteH;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }


    @Override
    public Rectangle getInteractionBox() {
        return interactionBox;
    }

    public static void setInteractable(boolean interactable) {
        StringItem.interactable = interactable;
    }
}