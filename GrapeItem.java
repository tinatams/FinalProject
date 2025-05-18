/**
    This class is a Grape item that the player can use in the game.
    It extends SuperItem, since it is an item. Item is stackable.

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

public class GrapeItem extends SuperItem{
    public static final String ITEMNAME = "GRAPE";

    /**
        Passes default item values and coordinates to superItem. 
        Instantiates the hitbox of the item

        @param x is the x coordinate of the item
        @param y is the y coordinate of the item

        sets the hitbox of the item
    **/
    public GrapeItem (int x, int y){
        super(ITEMNAME,x, y, 16, 16);
        super.hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);
    }

    /**
        loads the image of the class, sets the sprite variable to an image with the file path
        "./res/items/grape.png"
    **/
    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/grape.png"));
        } catch (IOException e){
            
        }
    }
}