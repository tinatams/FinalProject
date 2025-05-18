/**
    StringItem Class that extends SuperItem and implements Interactable contains the item name and a load method for the image. 
    It also allows Interactions so that the item can be picked up (hitbox and interaction box) 
 
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

public class StringItem extends SuperItem implements Interactable{

    public static final String ITEMNAME = "STRING";
    private Rectangle hitBox, interactionBox;
    private static boolean interactable=false;

    public StringItem(int x,int y){ //Constructor determines the size of the entity drawn in inventory and whether it is stackable and hitbox and interaction box
        super(ITEMNAME,x, y, 16, 16);
        setStackable(true);
          hitBox = new Rectangle(0 ,0 ,0, 0);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);

    }

    @Override
    public void loadImage() { //reads what png needs to be drawn
        try{
            sprite = ImageIO.read(new File("./res/items/String.png"));
        } catch (IOException e){
            
        }
    }
    @Override
    public void interact(Player player) { //calls the collect method whenever they interact. Player gets a string in their inventory.
        if(interactable){
         player.collect(this);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {  //calls the draw method for interactable
        if(interactable){
          super.draw(g2d);
        }
    }

    @Override
    public int getWorldX() { //returns where the item is (x-coordinates)
        return worldX;
    }

    @Override
    public int getWorldY() { //returns where the item is (y-coordinates)
        return worldY;
    }

    @Override
    public int getSpriteW() { //returns the width of the item
        return spriteW;
    }

    @Override
    public int getSpriteH() { //returns the height of the item
        return spriteH;
    }

    @Override
    public Rectangle getHitBox() { //returns the hitbox dimensions
        return hitBox;
    }


    @Override
    public Rectangle getInteractionBox() { //returns the interaction box dimensions
        return interactionBox;
    }

    public static void setInteractable(boolean interactable) { //setter for whether the item is interactable
        StringItem.interactable = interactable;
    }
}