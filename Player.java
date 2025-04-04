import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
//import java.util.ArrayList;

public class Player{
    private int worldX, worldY, screenX, screenY, speed;
    private int spriteW, spriteH, counter, version, direction;
    private String skin;

    public static final int IDLE = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int DOWN = 3;
    public static final int RIGHT = 4;

    private BufferedImage spriteSheet;
    private BufferedImage[][] sprites;
    private Rectangle hitBox; 

    public Player(String s, int x, int y){
        worldX = x;
        worldY = y;
        speed = 3;


        skin = s;
        spriteW = GameFrame.SCALED; 
        spriteH = GameFrame.SCALED;

        screenX = GameFrame.WIDTH/2 - spriteW/2; 
        screenY = GameFrame.HEIGHT/2 - spriteH;

        direction = IDLE;
        counter = 0;
        version = 0;

        sprites = new BufferedImage[5][2];
        setUpSprites();
    }

    public void setUpSprites(){
        try{
            spriteSheet = ImageIO.read(new File(String.format("./res/%s.png",skin)));
            int tileSize = 16;
            //idle
            sprites[0][0] = spriteSheet.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            sprites[0][1] = spriteSheet.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);

            //walking animations
            //down
            sprites[DOWN][0] = spriteSheet.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            sprites[DOWN][1] = spriteSheet.getSubimage(0 * tileSize, 3 * tileSize, tileSize, tileSize);

            //up
            sprites[UP][0] = spriteSheet.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
            sprites[UP][1] = spriteSheet.getSubimage(1 * tileSize, 3 * tileSize, tileSize, tileSize);

            //left
            sprites[LEFT][0] = spriteSheet.getSubimage(2 * tileSize, 1 * tileSize, tileSize, tileSize);
            sprites[LEFT][1] = spriteSheet.getSubimage(2 * tileSize, 3 * tileSize, tileSize, tileSize);

            //right
            sprites[RIGHT][0] = spriteSheet.getSubimage(3 * tileSize, 1 * tileSize, tileSize, tileSize);
            sprites[RIGHT][1] = spriteSheet.getSubimage(3 * tileSize, 3 * tileSize, tileSize, tileSize);
        } catch (IOException e){
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(sprites[direction][version], worldX, worldY, spriteW, spriteH, null);

        counter++; 
        if (counter > 15){
            if (version == 0) version = 1;
            else if (version ==1) version = 0;

            counter = 0;
        }
    }

    public void drawSelected(Graphics2D g){
        g.drawImage(sprites[direction][version], screenX, screenY, spriteW, spriteH, null);

        counter++; 
        if (counter > 15){
            if (version == 0) version = 1;
            else if (version ==1) version = 0;

            counter = 0;
        }
    }

    public void update(){
        switch (direction){
            case UP:
                worldY -= speed;
                break;
            case DOWN:
                worldY += speed;
                break;
            case RIGHT:
                worldX += speed;
                break;
            case LEFT:
                worldX -= speed;
                break;
        }
    }

    

    public void setDirection(int dir){
        direction = dir;
    }

    public void setOther(int dir, int v){
        direction = dir;
        version = v;
    }

    public int getX() {
        return worldX;
    }

    public int getY() {
        return worldY;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getScreenX() {
        return screenX;
    }
    
    public int getDirection() {
        return direction;
    }

    public String getSkin(){
        return skin;
    }

    public int getVer(){
        return  version;
    }

}