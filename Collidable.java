/**
    Collidable is an interphase applied to objects that can be collided with. 
    Should be implemented by any object that can collide with another object. 

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

public interface Collidable{
    /**
        Gets the X coordiante
    **/
    public int getWorldX();

    /**
        Gets the Y coordiante
    **/
    public int getWorldY();

    /**
        Gets the Width
    **/
    public int getSpriteW();

    /**
        Gets the Height
    **/
    public int getSpriteH();

    /**
        Gets the hitbox
    **/
    public Rectangle getHitBox();
}