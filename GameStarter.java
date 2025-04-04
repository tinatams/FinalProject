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
        frame = new GameFrame(serverData);
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
                    frame.setClientNumber(clientNumber);
                    clientData = String.format("%d,%d,%d,%s,%d,%d", clientNumber, clientPlayer.getX(), clientPlayer.getY(), clientPlayer.getSkin(), clientPlayer.getDirection(), clientPlayer.getVer());
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
        public ReadFromServer(){

        }

        public void run(){
            while (true) { 
               try {
                    serverData = dataIn.readUTF();
                    frame.recieveData(serverData);
                } catch (IOException ex) {
                } 
            }
        }
    }

    public static void main(String[] args) {
        GameStarter c = new GameStarter();
        c.connectToServer();
        c.closeSocketOnShutdown();
    }

}