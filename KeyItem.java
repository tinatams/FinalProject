/**
    This class is a Key item that the player can use in the game.
    Item is used to unlock locks. It extends SuperItem, since it is an item. 
    Item is non-stackable.

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
import java.io.*;
import javax.imageio.*;

public class KeyItem extends SuperItem implements Interactable{
    public static final String ITEMNAME = "KEY";

    private Rectangle interactionBox;
    private String lockName;

    /**
        Passes default item values and coordinates to superItem. 
        Instantiates the hitbox of the item. Makes it so that the item does not stack. 

        @param x is the x coordinate of the item
        @param y is the y coordinate of the item
        @param n is the name of the lock that the key object unlocks

        sets the hitbox and interaction box of the item
    **/
    public KeyItem(int x, int y, String n){
        super(ITEMNAME,x, y, 16, 16);
        super.hitBox = new Rectangle(worldX,worldY,0,0);
        interactionBox = new Rectangle(worldX,worldY, spriteW, spriteH);
        setStackable(false);
        lockName = n;
    }

    /**
        loads the image of the class, sets the sprite variable to an image with the file path
        "./res/items/key.png"
    **/
    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/key.png"));
        } catch (IOException e){
            
        }
    }

    /**
        Allows for the keyItem to interact with the player (if it is placed onto the map).
        When interacted with, the player will pickup the item and a sound will be played. 
    **/
    @Override
    public void interact(Player player) {
        player.collect(this);
        SoundHandler sh = player.getFrame().getSoundHandler();
        sh.playEffect(SoundHandler.QUEST_DONE);
    }

    /**
        Gets the interactionBox of the key item
        @return interactionBox
    **/
    @Override
    public Rectangle getInteractionBox() {
        return interactionBox;
    }

    /**
        Gets the x position of the item
        @return worldX
    **/
    @Override
    public int getWorldX() {
        return super.worldX;
    }

    /**
        Gets the y position of the item
        @return worldY
    **/
    @Override
    public int getWorldY() {
        return super.worldY;
    }

    /**
        Gets the width of the sprite
        @return spriteW
    **/
    @Override
    public int getSpriteW() {
        return super.spriteW;
    }

    /**
        Gets the height of the sprite
        @return spriteH
    **/
    @Override
    public int getSpriteH() {
        return super.spriteH;
    }

    /**
        Gets the hitBox of the component
        @return hitBox
    **/
    @Override
    public Rectangle getHitBox() {
        return super.hitBox;
    }

    /**
        Gets the name of the lock that the key unlocks
        @return lockName
    **/
    public String getLockName() {
        return lockName;
    }
    public void setInteractionBox(Rectangle interactionBox) {
        this.interactionBox = interactionBox;
    }
}