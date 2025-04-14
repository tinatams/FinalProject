import java.awt.*;
import java.awt.image.*;

public abstract class SuperItem{
    protected int worldX, worldY, spriteW, spriteH;
    protected String name;
    protected BufferedImage sprite;
    protected Player owner;
    protected boolean owned;
    protected Rectangle hitBox;

    public SuperItem(int x, int y, int w, int h){
        spriteW = w * GameFrame.SCALER;
        spriteH = h * GameFrame.SCALER;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        owner = null;
        owned = false;

        loadImage();
    }

    public abstract void loadImage();

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprite, worldX, worldY, spriteW, spriteH, null);
    }

    public void drawSpecific(Graphics2D g2d, int x, int y){ //for drawing Inventory
        g2d.drawImage(sprite, x, y, spriteW, spriteH, null);
    }

    protected void setOwner(Player owner){
        this.owner = owner;
        if (owner != null) owned = true;
    }
}