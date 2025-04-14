import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class Player implements Collidable{
    private int worldX, worldY, screenX, screenY, speed;
    private int spriteW, spriteH, counter, version, direction;
    private String skin;

    private MapHandler mapH;

    public static final int IDLE = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int DOWN = 3;
    public static final int RIGHT = 4;

    private BufferedImage spriteSheet;
    private BufferedImage[][] sprites;
    private Rectangle hitBox;

    private ArrayList<SuperItem> inventory;


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
        inventory = new ArrayList<>();

        setUpSprites();
    }

    public void setUpSprites(){
        try{
            spriteSheet = ImageIO.read(new File(String.format("./res/playerSkins/%s.png",skin)));
            int tileSize = 16;
            //idle
            sprites[0][0] = spriteSheet.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            sprites[0][1] = sprites[0][0];

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

    public void update(){
        int origX = worldX, origY = worldY;
        //System.out.println(String.format("%d, %d", worldX, worldY));
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

        //System.out.println(cannotMove());
        if (cannotMove()){
            worldY = origY;
            worldX = origX;
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

    public boolean cannotMove(){
        hitBox = new Rectangle(worldX + 10, worldY + 10, spriteH-20, spriteW-10);
        int worldXPos = hitBox.x/GameFrame.SCALED;
        int worldX2Pos = (hitBox.x + hitBox.width)/GameFrame.SCALED;
        int worldYPos = hitBox.y/GameFrame.SCALED;
        int worldY2Pos = (hitBox.y + hitBox.height)/GameFrame.SCALED;

        if (worldXPos < 0 || worldYPos < 0 || worldX2Pos < 0 || worldX2Pos > Map.maxColumn || worldY2Pos > Map.maxRow){
            return true;
        }

        int[][] collisionMap = mapH.getColMap();
        if (collisionMap[worldXPos][worldYPos] == 0 || collisionMap[worldX2Pos][worldYPos] == 0 || collisionMap[worldXPos][worldYPos] == -2 || collisionMap[worldX2Pos][worldYPos] == -2|| collisionMap[worldXPos][worldY2Pos] == -2){
            return true;
        }
        else 
            return false;
    } 

    public void setDirection(int dir){
        direction = dir;
    }

    public void setOther(int dir, int v){
        direction = dir;
        version = v;
    }

    public void teleportPlayer(int x, int y){
        worldX = x;
        worldY = y;
    }

    public void collect(SuperItem item){
        inventory.add(item);
        item.setOwner(this);
    }

    public boolean isColliding(Collidable c){
        hitBox = new Rectangle(worldX + 10, worldY + 10, spriteW-20, spriteH-10);
        Rectangle itemHitBox = c.getHitBox();

        if (c instanceof Interactable){
            Interactable interactObj = (Interactable) c;
            itemHitBox = interactObj.getInteractionBox();
        }

        return hitBox.intersects(itemHitBox);
    }

    public Interactable getInteractable(ArrayList<Interactable> interactables) {
        for (Interactable other: interactables){
            if (isColliding(other))
                return other;
        }

        return null;
    }

    public void interact(){
        Interactable interactionObj = getInteractable(mapH.getInteractables());
        if (interactionObj != null) interactionObj.interact(this);

        for(SuperItem item : inventory){
            System.out.println(item);
        }
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
    
    public int getDirection() {
        return direction;
    }

    public String getSkin(){
        return skin;
    }

    public int getVer(){
        return  version;
    }

    public void setMapHandler(MapHandler mh){
        mapH = mh;
    }
}   