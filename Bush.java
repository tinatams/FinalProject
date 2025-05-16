/**
    This class creates an Bush item that the player can interact with.
    It implements interactable interface. Bush gives player berry item
    when interacted with if bush has berries. 

    Bush item will regrow berries after 4 minutes. The bush has 2 sprites
    one for when it has berries and when it doesn't. Bush plays a sound 
    when it is interacted with

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
import java.util.*;
import javax.imageio.*;

public class Bush implements Interactable{
    public static String ITEMNAME = "BUSH";
    
    private int worldX, worldY, spriteW, spriteH;
    private String name;
    private BufferedImage berries, noBerries;
    private int health;

    private boolean hasBerries;
    private Rectangle hitBox, interactionBox;

    /**
        Constructor that initiates the x and y position of the Bush object. 
        It also instansiates/ sets the default values of each field. 

     	@param x = x position to be assigned to worldX
     	@param y = y position to be assigned to worldY
     	spriteW = 3 tiles wide
        spriteH = 2 tiles tall
        health = 5 
        hasBerries = true
        hitBox = a little bit smaller than the size of the sprite
        interactionBox = a little bit bigger than the size of the sprite
    **/
    public Bush(int x, int y){
        spriteW = GameFrame.SCALED * 3;
        spriteH = GameFrame.SCALED * 2;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        health = 5;

        hasBerries = true;

        hitBox = new Rectangle(worldX + 15 ,worldY + 15 ,spriteW -30, spriteH-15);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);

        loadImage();
    }

    /**
        Method loads the sprites of the object. 
        Gets the tilesheet from the filepath ./res/tileSets/TileSetDeco.png.

        From this tileset it gets the subimage that correspond to the sprite that has berries
        and the one that doesnt

        has berries: col 3, row 7
        no berries: col 3, row 9
    **/
    public void loadImage(){
        try {
            BufferedImage treeSprite = ImageIO.read(new File("./res/tileSets/TileSetDeco.png")); 
            int tileSize = 16;

            berries = treeSprite.getSubimage(2 * tileSize, 6 * tileSize, spriteW/GameFrame.SCALED* tileSize, spriteH/GameFrame.SCALED* tileSize);
            noBerries = treeSprite.getSubimage(2 * tileSize, 8 * tileSize, spriteW/GameFrame.SCALED* tileSize, spriteH/GameFrame.SCALED* tileSize);
        } catch (IOException ex) {
            
        }
    }

    /**
        Method draws the bush item on screen 
        Determines which sprite to draw depending on the hasBerries boolean variable.
    **/
    public void draw(Graphics2D g2d){
        BufferedImage toDraw = hasBerries ? berries : noBerries;
        g2d.drawImage(toDraw, worldX, worldY, spriteW, spriteH, null);
    }

    /**
        Method allows BushItem to interact with Player class
        if the Bush has berries, then it gives the player a grape item
        then sets a timer to replenish the grapes after 4 minutes

        plays a shake sound
    **/
    @Override
    public void interact(Player player) {
        if (hasBerries){
            player.collect(new GrapeItem(0,0));
            hasBerries = false;

            Timer berryTimer = new Timer();
            TimerTask berryBack = new TimerTask(){
                @Override 
                public void run(){
                    hasBerries = true;
                }
            };
            berryTimer.schedule(berryBack, 240000);
        }
        
        SoundHandler sh = player.getFrame().getSoundHandler();
        sh.playEffect(SoundHandler.SHAKE_BUSH);
    }

    /**
        Gets the bushes X coordinate
        @return worldX
    **/
    @Override
    public int getWorldX() {
        return worldX;
    }

    /**
        Gets the bushes Y coordinate
        @return worldY
    **/
    @Override
    public int getWorldY() {
        return worldY;
    }

    /**
        Gets the width of the Bush Sprite
        @return spriteW
    **/
    @Override
    public int getSpriteW() {
        return spriteW;
    }  

    /**
        Gets the height of the Bush Sprite
        @return spriteH
    **/
    @Override
    public int getSpriteH() {
        return spriteH;
    }

    /**
        Gets the hitbox of the bush
        @return hitBox
    **/
    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
        Gets the health points of the bush
        @return health
    **/
    public int getHealth(){
        return health;
    }

    /**
        Gets if the bush has berries
        @return hasBerries
    **/
    public boolean hasBerries(){
        return hasBerries;
    }

    /**
        Gets the InteractionBox of the bush
        @return interactionBox
    **/
    @Override
    public Rectangle getInteractionBox() {
        return interactionBox;
    }
}