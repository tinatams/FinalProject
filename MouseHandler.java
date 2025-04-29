import java.awt.event.*;

public class MouseHandler implements MouseListener, MouseMotionListener{
    private UIHandler UI;

    public MouseHandler(UIHandler u){
        UI = u;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        UI.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        UI.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        UI.mouseMoved(e);
    }

} 