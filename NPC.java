import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class NPC implements Collidable{ //should extend interactable
    private int worldX, worldY, screenX, screenY, speed;
    private int spriteW, spriteH;
    private String skin;
    
    private BufferedImage spriteSheet;
    private BufferedImage sprite;
    private Rectangle hitBox; 
    private int tileSize = 16;

    public NPC(String s, int x, int y){
        worldX = x*GameFrame.SCALED;
        worldY = y*GameFrame.SCALED;
       
        skin = s;
        spriteW = GameFrame.SCALED; 
        spriteH = GameFrame.SCALED;

        screenX = GameFrame.WIDTH/2 - spriteW/2; 
        screenY = GameFrame.HEIGHT/2 - spriteH;

        setUpSprites();
    }

    public void setUpSprites(){
        try{
            spriteSheet = ImageIO.read(new File(String.format("./res/NPCs/%s.png",skin)));
            
            sprite = spriteSheet.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
        } catch (IOException e){
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(sprite, worldX, worldY, spriteW, spriteH, null);
    }

    @Override
    public boolean isColliding(Collidable c){
        hitBox = new Rectangle(worldX + 10, worldY + 10, spriteW-20, spriteH-10);
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

    @Override
    public int getSpriteW() {
        return spriteW;
    }

    @Override
    public int getSpriteH() {
        return spriteH;
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
    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getScreenX() {
        return screenX;
    }



}