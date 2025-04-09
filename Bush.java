import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.*;

public class Bush implements Interactable{
    private int worldX, worldY, spriteW, spriteH;
    private String name;
    private BufferedImage berries, noBerries;
    private int health;

    private boolean hasBerries;
    private Rectangle hitBox;

    public Bush(int x, int y){
        spriteW = GameFrame.SCALED * 3;
        spriteH = GameFrame.SCALED * 2;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        health = 5;

        hasBerries = true;

        hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);

        loadImage();
    }

    public void loadImage(){
        try {
            BufferedImage treeSprite = ImageIO.read(new File("./res/tileSets/TileSetDeco.png")); 
            int tileSize = 16;

            berries = treeSprite.getSubimage(2 * tileSize, 6 * tileSize, spriteW/GameFrame.SCALED, spriteH/GameFrame.SCALED);
            noBerries = treeSprite.getSubimage(2 * tileSize, 6 * tileSize, spriteW/GameFrame.SCALED, spriteH/GameFrame.SCALED);
        } catch (IOException ex) {
            
        }
    }

    public void draw(Graphics2D g2d){
        BufferedImage toDraw = hasBerries ? berries : noBerries;
        g2d.drawImage(toDraw, worldX, worldY, spriteW, spriteH, null);
    }

    @Override
    public void interact() {
        hasBerries = false;
        Timer berryTimer = new Timer();
        TimerTask berryBack = new TimerTask(){
            @Override 
            public void run(){
                hasBerries = true;
            }
        };

        berryTimer.schedule(berryBack, 10000);
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

    public boolean hasBerries(){
        return hasBerries;
    }
}