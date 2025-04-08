import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class GameCanvas extends JComponent{

    private Player selectedPlayer;
    private int clientNumber;

    private MapHandler mapH;
    public GameCanvas(Player me, int CN){
        selectedPlayer = me;

        clientNumber = CN;
        mapH = new MapHandler(selectedPlayer);
        selectedPlayer.setCollisionMap(mapH.getColMap());
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();

        g2d.translate(-selectedPlayer.getWorldX() + selectedPlayer.getScreenX(), -selectedPlayer.getWorldY()+selectedPlayer.getScreenY());
        mapH.drawBase(g2d);
        mapH.drawDeco(g2d);
        
        g2d.setTransform(reset);
        selectedPlayer.drawSelected(g2d);

        g2d.translate(-selectedPlayer.getWorldX() + selectedPlayer.getScreenX(), -selectedPlayer.getWorldY()+selectedPlayer.getScreenY());
        mapH.drawColAbles(g2d);
        g2d.setTransform(reset);
    }

    public void update(){
        selectedPlayer.update();
        mapH.update();
    }


    public void startAnimation(){
        Animation a = new Animation();
        a.start();
    }

    private class Animation extends Thread {

        public void Animation(){

        }

        public void run(){
            while(true){
                update();
                repaint();
                
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e){}
            }
        }
    }

}