import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Ore implements Interactable{
    private int worldX, worldY, spriteW, spriteH;
    private String name;
    private BufferedImage treeSprite;
    private int health;

    private Rectangle hitBox, interactionBox;

    public Ore(int x, int y){
        spriteW = GameFrame.SCALED;
        spriteH = GameFrame.SCALED;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        health = 7;

        hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);
         interactionBox = new Rectangle(worldX - GameFrame.SCALED/2,worldY - GameFrame.SCALED/2, spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);

        loadImage();
    }

    public void loadImage(){
        try {
            BufferedImage tileSheet = ImageIO.read(new File("./res/tileSets/TileSetDeco.png")); 
            int tileSize = 16;

            treeSprite = tileSheet.getSubimage(16 * tileSize, 1 * tileSize, spriteW/GameFrame.SCALED * tileSize, spriteH/GameFrame.SCALED * tileSize);
        } catch (IOException ex) {
            
        }
    }

    @Override
    public void draw(Graphics2D g2d){
        g2d.drawImage(treeSprite, worldX, worldY, spriteW, spriteH, null);
    }

    @Override
    public void interact(Player player) {
        health--;
        System.out.println(health);
        if (health == 0){
            player.collect(new IronItem(0,0));
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

    public int getHealth(){
        return health;
    }

    @Override
    public Rectangle getInteractionBox() {
        return interactionBox;
    }
}