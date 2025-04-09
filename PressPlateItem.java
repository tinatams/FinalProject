import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public abstract class PressPlateItem implements Interactable{
    protected int worldX, worldY, spriteW, spriteH;
    protected String name;
    protected BufferedImage pressed, notPressed;

    protected boolean press;
    protected Rectangle hitBox;

    public PressPlateItem(int x, int y){
        spriteW = GameFrame.SCALED;
        spriteH = GameFrame.SCALED;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        press = false;

        hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);

        loadImage();
    }

    public void loadImage(){
        try {
            BufferedImage buttonSprite = ImageIO.read(new File("./res/tileSets/TilesetDungeon.png")); 
            int tileSize = 16;

            pressed = buttonSprite.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            notPressed = buttonSprite.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
        } catch (IOException ex) {
            
        }
    }

    public void draw(Graphics2D g2d){
        BufferedImage toDraw = press ? notPressed : pressed;
        g2d.drawImage(toDraw, worldX, worldY, spriteW, spriteH, null);
    }

    public abstract void actionToDo();

    @Override
    public void interact(){
        press = !press;
        actionToDo();
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
}