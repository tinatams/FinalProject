import java.io.*;
import java.net.*;

public class GameStarter{
    private GameFrame frame;
    private Socket theSocket;

    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    private int clientNumber;
    private String clientData; // data to send out to the server
    private String serverData; // data recieved from server

    public GameStarter(){
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

    public void connectToServer(){
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
        frame = new GameFrame(serverData, clientNumber);
        frame.setUpGUI();
        frame.addKeyBindings();
    }

    public class WriteToServer extends Thread{
        public WriteToServer(){

        }

        public void run(){
            while (true) { 
                try {
                    Player clientPlayer = frame.getSelectedSq();
                    clientData = String.format("%d,%d,%d,%s,%d,%d,%s", clientNumber, clientPlayer.getWorldX(), clientPlayer.getWorldY(), clientPlayer.getSkin(), clientPlayer.getDirection(), clientPlayer.getVer(), frame.getMap());
                    dataOut.writeUTF(clientData);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                    }
                } catch (IOException ex) {
                }
            }
        }
    }

    public class ReadFromServer extends Thread{
        private String[] dataTypes = {"Players"};

        public ReadFromServer(){

        }

        public void run(){
            while (true) { 
               try {
                    serverData = dataIn.readUTF();

                    String[] sData = serverData.split("\n");
                    for(String dataType : sData){
                        String[] data = dataType.split("\\|");
                        if (data[0].equals("Players")){
                            frame.recieveData(compile(data));
                        }

                    }

                    
                } catch (IOException ex) {
                } 
            }
        }

        private String compile(String[] data){
            String tempString = "";
            for (int i = 1; i < data.length ; i++){
                tempString += data[i] + "|";
            }

            return tempString;
        }
    }

    public static void main(String[] args) {
        GameStarter c = new GameStarter();
        c.connectToServer();
        c.closeSocketOnShutdown();
    }

}