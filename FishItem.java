/**
    This class is a Fish item that the player can use in the game.
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

import java.io.*;
import javax.imageio.*;

public class FishItem extends SuperItem {
    public static final String ITEMNAME = "FISH";

    /**
        Passes default item values to superItem
        Makes it so that Fish Items can stack. 
    **/
    public FishItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(true);
    }

    /**
        loads the image of the class, sets the sprite variable to an image with the file path
        "./res/items/Fish.png"
    **/
    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/Fish.png"));
        } catch (IOException e){
            
        }
    }
}