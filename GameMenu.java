import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMenu{
    private GameStarter gameStarter;
    private JFrame menuFrame;
    private JPanel cp;
    private MenuHandler mh;
    
    public static int STATE;
    public static final int MENU = 0;
    public static final int CHOOSING = 1;

    public GameMenu(GameStarter gs){
        menuFrame = new JFrame();
        cp = (JPanel) menuFrame.getContentPane();

        mh = new MenuHandler(gs);
    }


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
        menuFrame.pack();
    }

    void closeMenu() {
        menuFrame.dispose();
    }

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

    private class KL implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            mh.keyTyped(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}

    }
}