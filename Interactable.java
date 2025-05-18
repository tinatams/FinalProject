/**
    Interactable is an interphase applied to objects that can be interacted with. 
    Should be implemented by any object that can interact with another object. 

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
import java.awt.Graphics2D;
import java.awt.Rectangle;

interface Interactable extends Collidable{
    /**
        Method allows Interactable object to interact with Player object
    **/
    public void interact(Player player);

    /**
        Gets the interaction box of the player.
    **/
    public Rectangle getInteractionBox();

    /**
        Lets the interactable object be drawn. 
        @param g2d is the Graphics object that is used to draw the Interactable Object
    **/
    void draw(Graphics2D g2d);
}
