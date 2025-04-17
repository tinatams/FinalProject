import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class GameCanvas extends JComponent{
    private ArrayList<Player> players;
    private String dataFromServer;
    private Player selectedPlayer;
    public static String currentDialog="";

    private int clientNumber;
    private int gameState;

    private int cameraX, cameraY, cameraW, cameraH;  

    private MapHandler mapH;

    public GameCanvas(String data, Player me, int CN){
        dataFromServer = data;
        selectedPlayer = me;

        clientNumber = CN;

        mapH = new MapHandler(selectedPlayer);
        selectedPlayer.setMapHandler(this.mapH);

        cameraW = GameFrame.WIDTH;
        cameraH = GameFrame.HEIGHT;

        checkBounds();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();

        if (!dataFromServer.equals("nothing yet")){
            players = new ArrayList<Player>();
            String[] squares = dataFromServer.split("\\|");
            for (int i = 0; i < squares.length; i++){
                String sqData = squares[i];
                
                String[] sData = sqData.split(",");
                if (sData.length == 7){
                    if (Integer.parseInt(sData[0]) != clientNumber){
                        int otherMap = Integer.parseInt(sData[6]);
                        //.out.println(otherMap == mapH.getCurrentMap());
                        if(otherMap == mapH.getCurrentMap()){
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
        }

        if(gameState==GameFrame.PLAYING_STATE || gameState==GameFrame.DIALOG_STATE){
            checkBounds(); 
            g2d.translate(-cameraX, -cameraY);
            mapH.drawBase(g2d);
            mapH.drawDeco(g2d);
            mapH.drawInteracts(g2d);
            mapH.drawNPCs(g2d);
            
            if (players != null && players.size()> 0){
                for(Player player : players){
                    player.draw(g2d);
                }
            }
            selectedPlayer.draw(g2d);
            mapH.drawColAbles(g2d);
            

            
            g2d.setTransform(reset);
            if(gameState==GameFrame.DIALOG_STATE){
                drawDialogScreen(g2d);
            }
        }

        
    }

    public void drawDialogScreen(Graphics2D g2d){
        int x=16*2;
        int y=30;
        int width= GameFrame.WIDTH-90;
        int height=GameFrame.HEIGHT/3;
        Color c=new Color(0,0,0,200);
        g2d.setColor(c);
        g2d.fillRoundRect(x,y,width,height,35,35);

        c=new Color(250,250,250);
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
        
        x+=GameFrame.SCALED;
        y+=GameFrame.SCALED;

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,24F));
        for (String line: currentDialog.split("#")){
            g2d.drawString(line,x,y);
            y+=40;
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