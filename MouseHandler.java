/**
    MouseHandler Class implements MouseListener and MouseMotionListener. It ties the mouse movements and actions with the UI.
 
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

import java.awt.event.*;

public class MouseHandler implements MouseListener, MouseMotionListener{
    private UIHandler UI;
    /**
        constructor based on frame
    **/
    public MouseHandler(GameFrame frame){ 
        UI = frame.getUi();
    }
    
    /**
        what happens when mouse is clicked 
    **/
    @Override
    public void mouseClicked(MouseEvent e) { 
    }

    /**
        when mouse is pressed it calls the UI function
    **/
    @Override
    public void mousePressed(MouseEvent e) { 
        UI.mousePressed(e);

    }

    /**
        when mouse is released it calls the UI function
    **/
    @Override
    public void mouseReleased(MouseEvent e) {
        UI.mouseReleased(e);
    }

    /**
        what happens when mouse enters
    **/
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
        what happens when mouse exits
    **/
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
       what happens when mouse is dragged
    **/
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
        when mouse is moved it calls the UI function
    **/
    @Override
    public void mouseMoved(MouseEvent e) { 
        UI.mouseMoved(e);
    }

} 