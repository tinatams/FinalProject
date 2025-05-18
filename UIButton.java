/**
    UIButton inteface with all methods for buttons. Draws, updates, and reacts to mouse actions.

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

public interface UIButton{
    public abstract void draw(Graphics2D g2d);
    public abstract void update();
    public abstract boolean  isMousePressed();
    public abstract void setMousePressed(boolean mousePressed);
    public abstract boolean isMouseOver();
    public abstract void setMouseOver(boolean mouseOver);
    public abstract Rectangle getBounds();
    public abstract void clicked();
    public abstract void resetBools();
}