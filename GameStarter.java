/**
    The GameStarter initializes the game client. Starts the game menu, connects to server,
    handles collecting data and proccessing client and server data. 

    Class sets up and controls data through threads, creates and instantiates GameFrame and 
    collects data to update its states and attributes. 

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
import java.io.*;
import java.net.*;

public class GameStarter{
    private GameFrame frame;
    private GameMenu menuFrame;
    private Socket theSocket;


    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    private int clientNumber;
    private String clientData; // data to send out to the server
    private String serverData; // data recieved from server

    private boolean connected;
    

    /**
        Instantiates attributes

        connected to false because not yet connected to a server
        serverdata and client data to nothing yet since there is no information yet
    **/
    public GameStarter(){
        connected = false;
        serverData = "nothing yet";
        clientData = "nothing yet";
    }

    /**
        Method closes the socket when the program is closed
    **/
    public void closeSocketOnShutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread( ()-> {
            try {
                theSocket.close();
            } catch (Exception e) {
                System.out.println("IOException from closeSocketOnShutdown() method");
            }
        }));
    }

    /**
        Method connects the client to server. 

        @param is the ip address of the server
        @param port is the portnumber of the server

        if successfully connected, connected attribute is set to true, and recieves clients number
    **/
    public void connectToServer(String ip, String port){
        try {
            theSocket = new Socket(ip.trim(), Integer.parseInt(port.trim()));
            System.out.println("CONNECTION SUCCESSFUL");
            connected = true;

            dataIn = new DataInputStream(theSocket.getInputStream());
            dataOut = new DataOutputStream(theSocket.getOutputStream());

            //GETS CLIENT NUMBER
            //FIRST THING THAT THE SERVER SENDS
            clientNumber = Integer.parseInt(dataIn.readUTF()); 
            //setUpFrame();
        } catch (IOException e) {
            connected = false;
            System.out.println("IOException from connectToServer() method");
        }

        closeSocketOnShutdown();
    }

    /**
        Method initiates the GameFrame/ the actual game itself. 
        Instantiates GameFrame object and passes the current server data, clientdata and chosen skin of player.

        @param skin is the chosen skin of the player

        read and write threads are instantiated. 
    **/
    public void setUpFrame(String skin){
        System.out.println("starting game up");
        menuFrame.closeMenu();
        frame = new GameFrame(serverData, clientNumber, skin);
        frame.setUpGUI();

        WriteToServer wts = new WriteToServer();
        ReadFromServer rfs = new ReadFromServer();
            
        wts.start();
        rfs.start();
    }

    /**
        Method starts the GameMenu, and starts it's GUI/ frame. 
    **/
    public void startMenu(){
        menuFrame = new GameMenu(this);
        menuFrame.setUpGUI();
    }

    /**
        Gets if the client is connected to the server
        @return connected
    **/
    public boolean isConnected() {
        return connected;
    }

    /**
        Private/ inner classs that is used to send data to the server. 
    **/
    public class WriteToServer extends Thread{
        public WriteToServer(){

        }

        /**
            Gets data and sends it to the server every 10 miliseconds. 

            Sends Player data, Labyrinth data, Hermes inventory data, and Quest Data

            Player Data: coordinates, and sprite data
            Labyrinth: What map version the laybrinth is in
            Hermes: the inventory of hermes, and 'action' <- if hermes needs to be switched to the other island
            Quest: the state of each quest : not assigned, active, complete
        **/
        public void run(){  
            while (true) { 
                try {
                    Player clientPlayer = frame.getSelected();
                    MapHandler mapH = frame.getMapHandler();
                    QuestHandler questH=frame.getQuestH();
                    
                    clientData = String.format("Players|%d,%d,%d,%s,%d,%d,%s\n", clientNumber, clientPlayer.getWorldX(), clientPlayer.getWorldY(), clientPlayer.getSkin(), clientPlayer.getDirection(), clientPlayer.getVer(), frame.getMap());
                    clientData += String.format("Labyrinth|%d,%s\n",clientNumber, mapH.getVersion());
                    if (GameFrame.gameState == GameFrame.HERMES_STATE){
                        Hermes hermes = (Hermes) mapH.getNPC(Hermes.name);
                        clientData += String.format("Hermes|%d,%s,%s\n",clientNumber, hermes.getAction(), hermes.getItemString());
                    }
                        clientData += String.format("Quest|%d,%s\n", clientNumber, frame.getQuestH().gatherData());
                    // System.out.println(clientData);
                    dataOut.writeUTF(clientData);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                    }

                    dataOut.flush();
                } catch (IOException ex) {
                }
            }
        }
    }

    /**
        Private/ inner class that is used to recieve data from the server
    **/
    public class ReadFromServer extends Thread{
        public ReadFromServer(){}

        /**
            Recieves data from the server and distributes it to the respective classes for them
            to be updated accordingly.
        **/
        public void run(){
            while (true) { 
               try {
                    serverData = dataIn.readUTF();
                    String[] sData = serverData.split("\n");
                    if (sData.length > 0){
                        for(String dataType : sData){
                            String[] data = dataType.split("\\|");
                            if (data[0].equals("Players")){
                                frame.recieveData(compile(data));
                            } else if (data[0].equals("Labyrinth")){
                                MapHandler mapH = frame.getMapHandler();
                                mapH.recieveData(dataType);
                            } else if (data[0].equals("Hermes")){
                                Hermes hermes = frame.getMapHandler().getHermes();
                                if (hermes != null) hermes.recieveData(compile(data));
                            }
                            else if(data[0].equals("Quest")){  
                                if (data.length>=2) {
                                    frame.getQuestH().recieveData(data[1]);
                                }
                            
                            }
                        }
                    }
                    
                } catch (IOException ex) {
                } 
            }
        }

        /**
            Compiles that data to remove the 'data type' and send only necessary data. 
        **/
        private String compile(String[] data){
            String tempString = "";
            for (int i = 1; i < data.length ; i++){
                tempString += data[i];
                if (i + 1 < data.length){
                    tempString += "|";
                }
            }
            return tempString;
        }
    }

    /**
        Main method that instantiates GameStarter and starts the game Menu.
    **/
    public static void main(String[] args) {
        GameStarter c = new GameStarter();
        c.startMenu();
    }

}