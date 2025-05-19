/**
    The GameMenu class sets up and manages the opening menu of the 
    game. Sets up the frame of the menu. Contains inner classes that
    handles Key and Mouse events. 

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
import java.awt.event.*;
import javax.swing.*;

public class GameMenu{
    private GameStarter gameStarter;
    private JFrame menuFrame;
    private JPanel cp;
    private MenuHandler mh;
    private SoundHandler sh;
    
    public static int STATE;
    public static final int MENU = 0;
    public static final int CHOOSING = 1;

    /**
        Constructor that instantiates the default values of the GameCanvas

     	@param gs connects GameStarter to the Menu (Allows the menu to start the GameFrame)
        Creates instances of JFrame, Sound Handler and MenuHandler. 
    **/
    public GameMenu(GameStarter gs){
        menuFrame = new JFrame();
        cp = (JPanel) menuFrame.getContentPane();

        sh = new SoundHandler();
        mh = new MenuHandler(gs, sh);
    }

    /**
       Sets up the JFrame and connects mouse and key listenrs to the content Pane. 
    **/
    public void setUpGUI(){
        cp.setPreferredSize(new Dimension(GameFrame.WIDTH, GameFrame.HEIGHT));
        menuFrame.setTitle("Axios: Path of the Worthy");

        cp.setFocusable(true);
        ML ml = new ML();
        cp.addMouseListener(ml);
        cp.addMouseMotionListener(ml);
        cp.addKeyListener(new KL());

        cp.setBackground(new Color(20, 28, 22));
        cp.add(mh);
        mh.startAnimation();

        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
        menuFrame.setResizable(false);
        menuFrame.pack();
    }

    /**
       Closes/ disposes of the Frame
    **/
    void closeMenu() {
        menuFrame.dispose();
    }  

    /**
       Inner class that handles Mouse events. Passes mouse event to MenuHandler
       so that it can be handled by its methods.
    **/
    private class ML implements MouseListener, MouseMotionListener{
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mh.mousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mh.mouseReleased(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseDragged(MouseEvent e) {}

        @Override
        public void mouseMoved(MouseEvent e) {
            mh.mouseMoved(e);
        }
    }

    /**
       Inner class that handles Mouse events. Passes KeyEvents to MenuHandler to be handled
       by its methods. 
    **/
    private class KL implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            mh.keyTyped(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {}

    }
}