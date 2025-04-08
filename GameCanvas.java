import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class GameCanvas extends JComponent{
    private ArrayList<Player> players;
    private ArrayList<Collidable> collectItems;

    private String dataFromServer;
    private Player selectedPlayer;
    private int clientNumber;

    private BkgTileMan background;
    private String map;

    public GameCanvas(String data, Player me, int CN){
        dataFromServer = data;
        selectedPlayer = me;
        background = new BkgTileMan(selectedPlayer);
        map = "spawn";

        clientNumber = CN;

        setUpItems();
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
                if (sData.length == 7){
                    if (Integer.parseInt(sData[0]) != clientNumber){
                        String otherMap = sData[6];
                        System.out.println(otherMap.equals(map));
                        if(otherMap.equals(map)){
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
        g2d.translate(-selectedPlayer.getWorldX() + selectedPlayer.getScreenX(), -selectedPlayer.getWorldY()+selectedPlayer.getScreenY());
        background.draw(g2d);
        if (players != null && players.size() > 0){
            for(Player player : players){
                player.draw(g2d);
            }
        }

        for (Collidable item : collectItems){
            if (item instanceof SuperItem){
                SuperItem temp = (SuperItem) item;
                temp.draw(g2d);
            } else if (item instanceof ButtonItem){
                ButtonItem temp = (ButtonItem) item;
                temp.draw(g2d);
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

    public String getMap(){
        return map;
    }

    public void recieveData(String data){
        dataFromServer = data;
    }




    public void setUpItems(){
        collectItems = new ArrayList<>();
        collectItems.add(new ButtonItem(2,2){
            @Override
            public void actionToDo(){
                map = press ? "spawn" : "spawnn";
            }
        });

        if (clientNumber % 2 == 0){
            collectItems.add(new MeatItem(0,0));
            collectItems.add(new RockItem(10,2));
            collectItems.add(new RockItem(2,4));
            collectItems.add(new RockItem(5,20));
        } else {
            collectItems.add(new MeatItem(0,0));
            collectItems.add(new BranchItem(12,10));
            collectItems.add(new BranchItem(11,0));
            collectItems.add(new BranchItem(22,11));
        }
        
    }

    public ArrayList<Collidable> getItems(){
        return collectItems;
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