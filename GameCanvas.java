/**
    This class handles the drawing and updating of the game components. The 
    class also handles what objects are being shown in the frame through the 
    camera coordinates. 

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
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class GameCanvas extends JComponent{
    private ArrayList<Player> players;
    private String dataFromServer;
    private Player selectedPlayer;

    private int clientNumber;

    private int cameraX, cameraY, cameraW, cameraH;  

    private MapHandler mapH;
    private UIHandler ui;

    /**
        Constructor that instantiates the default values of the GameCanvas

     	@param data is the Server data, specidically the info of the players from the Server
        @param frame is an instance of the game frame

        selected player, client number, map handler, ui objects are passed from the frame. 

        the camera Width and Height are set to the width and height of the frame. 
    **/
    public GameCanvas(String data, GameFrame frame){
        dataFromServer = data;
        selectedPlayer = frame.getSelected();

        clientNumber = frame.getClientNumber();

        mapH = frame.getMapHandler();
        ui = frame.getUi();
        selectedPlayer.setMapHandler(mapH);

        cameraW = GameFrame.WIDTH;
        cameraH = GameFrame.HEIGHT;
    }
    
    /**
        Method draws the different components of the games

        players (from the server) are instantiated from the server data

        draws the components in the order
        1. Background tiles/ base tiles
        2. Decorative tiles (under the player)
        3. The interactable items (items/entities)
        4. Players
        5. NPCs
        6. THE player [the user's player]
        7. Decorative items (on top of the player)
        8. UI elements
    **/
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
                    if(otherMap == mapH.getCurrentMap()){
                        int x = Integer.parseInt(data[1]);
                        int y = Integer.parseInt(data[2]);
                        String s = data[3];
                        int direc = Integer.parseInt(data[4]);
                        int version = Integer.parseInt(data[5]);
                        int cliNum = Integer.parseInt(data[0]);
                        Player temp = new Player(s,x,y,cliNum);
                        temp.setOther(direc, version);
                        players.add(temp);          
                    }
                }
            }  
        }

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

        mapH.drawNPCs(g2d);

        selectedPlayer.draw(g2d);
        mapH.drawColAbles(g2d);
        g2d.setTransform(reset);

        ui.draw(g2d);
    }

    /**
        Method updates the components of the game 
        --> Player, MapHandler, UI     
    **/
    public void update(){
        selectedPlayer.update();
        mapH.update();
        ui.update();
    }

    /**
        Sets the location of the camera so that the client player is centered in the middle
        of the Frame.

        If the camera, goes outside of the map it adjusts the positioning so that it stays 
        within the boundaries.
    **/
    public void checkBounds(){
        
        cameraX = (selectedPlayer.getWorldX() - cameraW/2) + GameFrame.SCALED;
        cameraY = (selectedPlayer.getWorldY() - cameraH/2) + GameFrame.SCALED;

        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        if(cameraX + cameraW > mapH.getMapWidth()*GameFrame.SCALED) cameraX = mapH.getMapWidth()*GameFrame.SCALED - cameraW;
        if(cameraY + cameraH > mapH.getMapHeight()*GameFrame.SCALED) cameraY = mapH.getMapHeight()*GameFrame.SCALED - cameraH;
    }

    /**
        Recieves data
        @param data is assigned to dataFromServer     
    **/
    public void recieveData(String data){
        dataFromServer = data;
    }

    /**
       Starts the animation of the canvas. Makes it so that it runs in a new thread.    
    **/
    public void startAnimation(){
        Animation a = new Animation();
        a.start();
    }

    /**
        Gets MapHandler
        @return mapH
    **/
    public MapHandler getMapHandler() {
        return mapH;
    }

    /**
        Inner class that handles the movement/ animation of the canvas. Updates and redraws the 
        component. Extends thread so that it will run in a new thread that can be paused. Basically
        handles the game loop.
    **/
    private class Animation extends Thread {

        public void Animation(){}

        /**
            Run method updates and repaints the component, to create movement within the canvs
            (game loop).
            Thread sleeps for 10 miliseconds between updates/repaints to control update rate. 
        **/
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

    /**
        Gets server from data
        @return dataFromServer
    **/
    public String getData(){
        return dataFromServer;
    }
}