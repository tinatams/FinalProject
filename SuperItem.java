import java.awt.*;
import java.awt.image.*;

public abstract class SuperItem{
    protected int worldX, worldY, spriteW, spriteH;
    private String name;
    protected BufferedImage sprite;
    private Player owner;
    protected boolean owned, stackable;
    protected Rectangle hitBox;
    private int amount; 

    public SuperItem(String n, int x, int y, int w, int h){
        name = n;
        spriteW = w * GameFrame.SCALER;
        spriteH = h * GameFrame.SCALER;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        owner = null;
        owned = false;
        stackable = true;
        
        amount = 1;

        loadImage();
    }

    public abstract void loadImage();

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprite, worldX, worldY, spriteW, spriteH, null);
    }

    public void drawSpecific(Graphics2D g2d, int x, int y, int w, int h){ //for drawing Inventory
        g2d.drawImage(sprite, x, y, w, h, null);
    }

    protected void setOwner(Player owner){
        this.owner = owner;
        if (owner != null) owned = true;
    }

    public String getName(){
        return name;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int newAmount){
        amount = newAmount;
    }

    public void setStackable(boolean stacks){
        stackable = stacks;
    }

    public boolean getStackable(){
        return stackable;
    }
}