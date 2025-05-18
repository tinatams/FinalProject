/**
    This class creates a Bone item that the player can use in the game.
    It extends SuperItem, since it is an item. Item is non-stackable.

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

public class BoneItem extends SuperItem {
    public static final String ITEMNAME = "BONE";

    /**
        Passes default item values to superItem
        Makes it so that Axe Items do not Stack. 
    **/
    public BoneItem(){
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(false);
    }

    /**
        loads the image of the class, sets the sprite variable to an image with the file path
        "./res/items/Bone.png"
    **/
    @Override
    public void loadImage() {
        try{
            sprite = ImageIO.read(new File("./res/items/Bone.png"));
        } catch (IOException e){
            
        }
    }
}