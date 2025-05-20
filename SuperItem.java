/**
    SuperItem Class is extended by other classes for inventory items. It draws the item needed.
 
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

public abstract class SuperItem{
    protected int worldX, worldY, spriteW, spriteH;
    private String name;
    protected BufferedImage sprite;
    private Player owner;
    protected boolean owned, stackable;
    protected Rectangle hitBox;
    private int amount; 

    public SuperItem(String n, int x, int y, int w, int h){ //Constructor to where the itsm is drawn in the inventory and its dimensions
        name = n;
        spriteW = w * GameFrame.SCALER;
        spriteH = h * GameFrame.SCALER;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        owner = null;
        owned = false;
        stackable = true;
        
        amount = 1;

        loadImage();
    }

    public abstract void loadImage();//loads necessary image (overwritten)

    public void draw(Graphics2D g2d){ //draws image
        g2d.drawImage(sprite, worldX, worldY, spriteW, spriteH, null);
    }

    public void drawSpecific(Graphics2D g2d, int x, int y, int w, int h){ //for drawing Inventory
        g2d.drawImage(sprite, x, y, w, h, null);
    }

    protected void setOwner(Player owner){//setter for which player has the item
        this.owner = owner;
        if (owner != null) owned = true;
    }

    public String getName(){//getter for item name
        return name;
    }

    public int getAmount(){//getter for amount
        return amount;
    }

    public void setAmount(int newAmount){//setter for amount
        amount = newAmount;
    }

    public void setStackable(boolean stacks){//setter for stackable
        stackable = stacks;
    }

    public boolean isStackable(){//getter for stackable
        return stackable;
    }
}