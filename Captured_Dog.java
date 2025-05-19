/**
    Captured Dog Class that extends SuperItem and contains the item name and a load method for the image. Also determines whether an 
    item is stackable to add to the inventory.
 
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

public class Captured_Dog extends SuperItem {
    public static final String ITEMNAME = "CAPTURED_DOG";

    public Captured_Dog(){ //Constructor determines the size of the entity drawn in inventory and whether it is stackable
        super(ITEMNAME,0, 0, 16, 16);
        setStackable(true);
    }

    @Override
    public void loadImage() { //reads what png needs to be drawn
        try{
            sprite = ImageIO.read(new File("./res/NPCs/Dog.png"));
        } catch (IOException e){
            
        }
    }
}