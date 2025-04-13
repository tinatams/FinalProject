import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Tree implements Interactable{
    private int worldX, worldY, spriteW, spriteH;
    private String name;
    private BufferedImage treeSprite;
    private int health;

    private Rectangle hitBox;

    public Tree(int x, int y){
        spriteW = GameFrame.SCALED * 3;
        spriteH = GameFrame.SCALED * 2;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        health = 5;

        hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);

        loadImage();
    }

    public void loadImage(){
        try {
            BufferedImage tileSheet = ImageIO.read(new File("./res/tileSets/TileSetDeco.png")); 
            int tileSize = 16;

            treeSprite = treeSprite.getSubimage(2 * tileSize, 6 * tileSize, spriteW/GameFrame.SCALED, spriteH/GameFrame.SCALED);
        } catch (IOException ex) {
            
        }
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(treeSprite, worldX, worldY, spriteW, spriteH, null);
    }

    @Override
    public void interact() {
        health--;
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
}