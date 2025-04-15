import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class GameCanvas extends JComponent{
    private ArrayList<Player> players;
    private String dataFromServer;
    private Player selectedPlayer;

    private int clientNumber;
    private int gameState;

    private int cameraX, cameraY, cameraW, cameraH;  

    private MapHandler mapH;

    public GameCanvas(String data, Player me, int CN, MapHandler mh){
        dataFromServer = data;
        selectedPlayer = me;

        clientNumber = CN;

        mapH = mh;
        selectedPlayer.setMapHandler(this.mapH);

        cameraW = GameFrame.WIDTH;
        cameraH = GameFrame.HEIGHT;

        checkBounds();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();

        players = new ArrayList<Player>();
        String[] serverPlayersData = dataFromServer.split("\\|");
        for (int i = 0; i < serverPlayersData.length; i++){
            String playerData = serverPlayersData[i];
            
            String[] data = playerData.split(",");
            if (data.length == 7){
                if (Integer.parseInt(data[0]) != clientNumber){
                    int otherMap = Integer.parseInt(data[6]);
                    //.out.println(otherMap == mapH.getCurrentMap());
                    if(otherMap == mapH.getCurrentMap()){
                        int x = Integer.parseInt(data[1]);
                        int y = Integer.parseInt(data[2]);
                        String s = data[3];
                        int direc = Integer.parseInt(data[4]);
                        int version = Integer.parseInt(data[5]);
                        Player temp = new Player(s,x,y);
                        temp.setOther(direc, version);
                        players.add(temp);
                        
                    }
                }
            }  
        }

        if(gameState==GameFrame.PLAYING_STATE){
            checkBounds(); 
            g2d.translate(-cameraX, -cameraY);
            mapH.drawBase(g2d);
            mapH.drawDeco(g2d);
            mapH.drawInteracts(g2d);
            
            if (players != null && players.size()> 0){
                for(Player player : players){
                    player.draw(g2d);
                }
            }
            selectedPlayer.draw(g2d);
            mapH.drawColAbles(g2d);
            g2d.setTransform(reset);
        }
    }

    public void setGameState(int gs){
        gameState = gs;
    }
    public void update(){
        selectedPlayer.update();
        mapH.update();
        
    }

    public void checkBounds(){
        
        cameraX = (selectedPlayer.getWorldX() - cameraW/2) + GameFrame.SCALED;
        cameraY = (selectedPlayer.getWorldY() - cameraH/2) + GameFrame.SCALED;

        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        if(cameraX + cameraW > mapH.getMapWidth()*GameFrame.SCALED) cameraX = mapH.getMapWidth()*GameFrame.SCALED - cameraW;
        if(cameraY + cameraH > mapH.getMapHeight()*GameFrame.SCALED) cameraY = mapH.getMapHeight()*GameFrame.SCALED - cameraH;
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

    public int getCurrentMap(){
        return mapH.getCurrentMap();
    }

}