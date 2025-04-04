import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class GameCanvas extends JComponent{
    private ArrayList<Player> players;
    private String dataFromServer;
    private Player selectedPlayer;
    private int clientNumber;

    private BkgTileMan background;
    private String map;

    public GameCanvas(String data, Player me){
        dataFromServer = data;
        selectedPlayer = me;
        background = new BkgTileMan(selectedPlayer);
        map = "spawn";
    }

    public void setClientNumber(int num){
        clientNumber = num;
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();

        background.loadMap(map);

        if (!dataFromServer.equals("nothing yet")){
            players = new ArrayList<Player>();
            String[] squares = dataFromServer.split("\\|");
            for (int i = 0; i < squares.length; i++){
                String sqData = squares[i];
                
                String[] sData = sqData.split(",");
                if (sData.length == 6){
                    if (Integer.parseInt(sData[0]) != clientNumber){
                        int x = Integer.parseInt(sData[1]);
                        int y = Integer.parseInt(sData[2]);
                        String s = sData[3];
                        int direc = Integer.parseInt(sData[4]);
                        int version = Integer.parseInt(sData[5]);
                        Player temp = new Player(s,x,y);
                        temp.setOther(direc, version);
                        players.add(temp);
                    }
                }
            }
        }
        g2d.translate(-selectedPlayer.getX() + selectedPlayer.getScreenX(), -selectedPlayer.getY()+selectedPlayer.getScreenY());
        background.draw(g2d);
        if (players != null && players.size() > 0){
            for(Player player : players){
                player.draw(g2d);
            }
        }

        g2d.setTransform(reset);
        selectedPlayer.drawSelected(g2d);
    }

    public void update(){
        selectedPlayer.update();
    }

    public void setMap(String m){
        map = m;
        background.setUpMap();
    }

    public void recieveData(String data){
        dataFromServer = data;
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