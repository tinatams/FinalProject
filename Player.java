/**
    Player CLass implements Collidable. Handles where the player is and all their interactions with the world

	@author Martina Amale M. Llamas (242648); Zoe Angeli G. Uy (246707)
	@version May 19, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.

    

**/

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class Player implements Collidable{
    private int worldX, worldY, screenX, screenY, speed, clientNumber;
    private int spriteW, spriteH, counter, version, direction;
    private String skin;

    private MapHandler mapH;
    private QuestHandler qh= new QuestHandler();
    private GameFrame frame;
    private boolean walkSound = true;

    public static final int IDLE = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int DOWN = 3;
    public static final int RIGHT = 4;

    private BufferedImage spriteSheet;
    private BufferedImage[][] sprites;
    private Rectangle hitBox;

    private ArrayList<SuperItem> inventory;

    /**
        @param String s, int x, int y, GameFrame f constructer based on frame
    **/
    public Player(String s, int x, int y, GameFrame f){ 
        frame = f;
        clientNumber = frame.getClientNumber();

        worldX = x;
        worldY = y;
        speed = 4;


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
        hitBox = new Rectangle(worldX + 10, worldY + 30, spriteW-20, spriteH-30);
    }
    /**
        @param String s, int x, int y, int cn constructer based on client number
    **/
    public Player(String s, int x, int y, int cn){ 
        clientNumber = cn;

        worldX = x;
        worldY = y;
        speed = 4;


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
        hitBox = new Rectangle(worldX + 10, worldY + 30, spriteW-20, spriteH-30);
    }
    /**
        sets up the player sprites
    **/
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
    /**
        @param Graphics2D g draw for the needed asset based on player direction
    **/
    public void draw(Graphics2D g){ 
        g.drawImage(sprites[direction][version], worldX, worldY, spriteW, spriteH, null);
        
        if (direction != IDLE){counter++; 
            if (counter > 15){
                if (version == 0) version = 1;
                else if (version ==1) version = 0;

                counter = 0;
            }
        }
    }
    /**
        @param Graphics2D g2d, int x, int y, int w, int h draws the player in a specific area
    **/
    public void drawSpecific(Graphics2D g2d, int x, int y, int w, int h){ 
        g2d.drawImage(sprites[direction][version], x, y, w, h, null);
    }
    /**
        updates player position according to collisions and player actions
    **/
    public void update(){ 
        int origX = worldX, origY = worldY;
        // System.out.println(String.format("%d, %d", worldX/48, worldY/48));
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
        // System.out.println(worldX+" "+worldY);

        if (direction != IDLE && version == 0 && counter == 0){
            frame.getSoundHandler().playEffect(SoundHandler.WALK);
            walkSound = false;
        }
        hitBox = new Rectangle(worldX + 10, worldY + 30, spriteW-20, spriteH-30);

        if (cannotMove()){
            worldY = origY;
            worldX = origX;
        }
        qh=frame.getQuestH();
        if(qh.states[15]==1){
            for(SuperItem i:inventory){
            if(i.getName().equals("KEY")){
                    qh.states[15]=2;
                    frame.setQuestH(qh);
                }
            }
        }
    }

    /**
        @param Graphics2D g draw selected sprites
    **/
    public void drawSelected(Graphics2D g){
        g.drawImage(sprites[direction][version], screenX, screenY, spriteW, spriteH, null);

        counter++; 
        if (counter > 15){
            if (version == 0) version = 1;
            else if (version ==1) version = 0;

            counter = 0;
        }
    }

    /**
        @return boolean checks if it's colliding with an object or NPC if so it can't move
    **/
    public boolean cannotMove(){
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
        
        ArrayList<Interactable> interactable = mapH.getInteractables();
        for (Interactable interactObj : interactable){
            Collidable collisionObj = (Collidable) interactObj;
            if(hitBox.intersects(collisionObj.getHitBox()) && !(collisionObj instanceof ButtonItem)){
                return true;
            }
        }

        ArrayList<NPC> NPCs = mapH.getNPCs();
        for (NPC npc : NPCs){
            Collidable collisionObj = (Collidable) npc;
            if(hitBox.intersects(collisionObj.getHitBox())){
                return true;
            }
        }
        
        return false;
    } 

    /**
        @param int dir setter for the direction and where the player is facing
    **/
    public void setDirection(int dir){ 
        direction = dir;
        if (dir == IDLE){
            version = 0;
            counter = 0;
        }
    }

    /**
        @param int x int y setter for the direction and where the player is facing
    **/
    public void setOther(int dir, int v){ 
        direction = dir;
        version = v;
    }
    /**
        @param int x int y changes x and y when it encounters a teleporter
    **/
    public void teleportPlayer(int x, int y){ 
        worldX = x;
        worldY = y;
    }
    /**
        @param item SuperItem adds item to the player inventory if there's space
    **/
    public void collect(SuperItem item){ //
        boolean itemCollected = false;
        SuperItem itemCollect = getItem(item.getName());
        if (itemCollect != null && itemCollect.isStackable()){
            itemCollect.setAmount(itemCollect.getAmount() + 1);
            itemCollected = true;
        } else {
            if (inventory.size() < 70){
                inventory.add(item);
                item.setOwner(this);
                itemCollected = true;
            }
        }

        ArrayList<Interactable> interacts = mapH.getInteractables();
        if (item instanceof Interactable interactItem){
            if (interacts.contains((Interactable) interactItem) && itemCollected){
                interacts.remove((Interactable) interactItem);
            }
        }
        
    }

    /**
        @param item SuperItem removes item from inventory
    **/
    public void discardItem(SuperItem item){ //
        SuperItem discardItem = getItem(item.getName());
        if (discardItem != null){
            if (discardItem.isStackable()){
                discardItem.setAmount(discardItem.getAmount() - 1);
                if (discardItem.getAmount() <= 0){
                    inventory.remove(discardItem);
                }
                //System.out.println("removed stackable item" + item.getName());
            } else {
                inventory.remove(discardItem);
                //System.out.println("removed non-stackable item" + item.getName());
                item.setOwner(null);
            }
        } 
    }

    /**
        @return SuperItem gets item in player inventory
    **/
    public SuperItem getItem(String name){ 
        for(SuperItem item : inventory){
            if ((item.getName()).equals(name)){
                return item;
            }
        }

        return null;
    }

    /**
        @return ArrayList<SuperItem> gets non stackable in player inventory
    **/
    public ArrayList<SuperItem> getNotStackableItem(String name){  
        ArrayList<SuperItem> notStackItems = new ArrayList<SuperItem>();
        for(SuperItem item : inventory){
            if ((item.getName()).equals(name)){
                notStackItems.add(item);
            }
        }

        return notStackItems;
    }

    /**
        @return boolean checks if the player is colliding with anything
    **/
    public boolean isColliding(Collidable c){ 
        Rectangle itemHitBox = c.getHitBox();

        if (c instanceof Interactable){
            Interactable interactObj = (Interactable) c;
            itemHitBox = interactObj.getInteractionBox();
        }

        return hitBox.intersects(itemHitBox);
    }

    /**
        @return Interactable gets what the player is interacting with
    **/
    public Interactable getInteractable(ArrayList<Interactable> interactables) { 
        for (Interactable other: interactables){
            if (isColliding(other))
                return other;
        }

        return null;
    }

    /**
        @return NPC gets what the player is interacting with
    **/
    public NPC getNPC(ArrayList<NPC> interactables) {
        for (NPC other: interactables){
            if (isColliding(other))
                return other;
        }

        return null;
    }

    /**
        based on what it interacts with the player it calls the interactable interact method
    **/
    public void interact(){ 
        Interactable interactionObj = getInteractable(mapH.getInteractables());
        NPC interactionNPC = getNPC(mapH.getNPCs());
        if (interactionObj != null) interactionObj.interact(this);
        if (interactionNPC != null) {
            GameFrame.gameState=2;
            interactionNPC.interact(this);
        }
    }

    /**
        @return ArrayList<SuperItem> gets inventory
    **/
    public ArrayList<SuperItem> getInventory(){ 
        return inventory;
    }

    /**
        @return int gets sprite width
    **/
    @Override
    public int getSpriteW() {
        return spriteW;
    }

    /**
        @return gets sprite height
    **/
    @Override
    public int getSpriteH() { 
        return spriteH;
    }

    /**
        @return int gets sprite x-coordinates
    **/
    @Override
    public int getWorldX() { 
        return worldX;
    }

    /**
        @return int gets sprite y-coordinates
    **/
    @Override
    public int getWorldY() { 
        return worldY;
    }

    /**
       @return Rectangle gets hitbox
    **/
    @Override
    public Rectangle getHitBox() { 
        return hitBox;
    }
    
    /**
        @return gets direction
    **/
    public int getDirection() {
        return direction;
    }

    /**
        @return String gets skin
    **/
    public String getSkin(){
        return skin;
    }

    /**
        @return int gets version
    **/
    public int getVer(){ 
        return  version;
    }

    /**
        @param mh setter for maphandler
    **/
    public void setMapHandler(MapHandler mh){ 
        mapH = mh;
    }

    /**
        @return NPC gets which NPC player is currently interacting with
    **/
    public NPC getNPCinteracting(){ 
        NPC interactionNPC = getNPC(mapH.getNPCs());
        return interactionNPC;
    }

    /**
        @return int gets client number
    **/
    public int getCliNum(){ 
        return clientNumber;
    }

    /**
        @return GameFrame gets frame
    **/
    public GameFrame getFrame(){ 
        return frame;
    }

    /**
        @return MapHandler gets mapHandler
    **/
    public MapHandler getMapH() { 
        return mapH;
    }
}   
