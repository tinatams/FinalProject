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
    

    public GameStarter(){
        connected = false;
        startMenu();
        serverData = "nothing yet";
        clientData = "nothing yet";
    }

    public void closeSocketOnShutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread( ()-> {
            try {
                theSocket.close();
            } catch (Exception e) {
                System.out.println("IOException from closeSocketOnShutdown() method");
            }
        }));
    }

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

    public void connectToServer2(){
        try {
            theSocket = new Socket("localhost", 60003);
            System.out.println("CONNECTION SUCCESSFUL");

            dataIn = new DataInputStream(theSocket.getInputStream());
            dataOut = new DataOutputStream(theSocket.getOutputStream());

            //GETS CLIENT NUMBER
            //FIRST THING THAT THE SERVER SENDS
            clientNumber = Integer.parseInt(dataIn.readUTF()); 
            setUpFrame();

            WriteToServer wts = new WriteToServer();
            ReadFromServer rfs = new ReadFromServer();
            
            wts.start();
            rfs.start();
        } catch (IOException e) {
            System.out.println("IOException from connectToServer() method");
        }
    }

    public void setUpFrame(){
        System.out.println("starting game up");
        menuFrame.closeMenu();
        frame = new GameFrame(serverData, clientNumber);
        frame.setUpGUI();

        WriteToServer wts = new WriteToServer();
        ReadFromServer rfs = new ReadFromServer();
            
        wts.start();
        rfs.start();
    }

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

    public void startMenu(){
        menuFrame = new GameMenu(this);
        menuFrame.setUpGUI();
    }

    public boolean isConnected() {
        return connected;
    }

    public class WriteToServer extends Thread{
        public WriteToServer(){

        }

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
                } catch (IOException ex) {
                }
            }
        }
    }

    public class ReadFromServer extends Thread{
        public ReadFromServer(){

        }

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

    public static void main(String[] args) {
        GameStarter c = new GameStarter();
    }

}