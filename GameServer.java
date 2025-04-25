import java.net.*;
import java.io.*;
import java.util.*;

public class GameServer{
    private ServerSocket ss;
    private ArrayList<Socket> sockets;
    private ArrayList<ClientRunnable> clients;

    private int clientNum = 0;
    private String serverData;

    public GameServer(){
        serverData = "nothing yet";
        sockets = new ArrayList<Socket>();
        clients = new ArrayList<ClientRunnable>();

        try {
            ss = new ServerSocket(60003);
        } catch (IOException e) {
            System.out.println("IOException from ChatServer constructor");
        }
        System.out.println("THE CHAT SERVER HAS BEEN CREATED");
    }

    public void closeSocketsOnShutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            try {
                for (Socket skt : sockets){
                    skt.close();
                }
            } catch (IOException e){
                System.out.println("IOException from closeSocketsOnShutdown() method ");
            }
        }));
    }

    public void waitForConnections(){
        try {
            System.out.println("NOW ACCEPTING CONNECTIONS...");
            while (true) { 
                Socket sock = ss.accept();
                sockets.add(sock);

                ClientRunnable cr = new ClientRunnable(sock, clientNum);
                clientNum++;
                cr.startThread();
                clients.add(cr);

                ServerOut so = new ServerOut();
                so.start();
            }
        } catch (IOException e) {
            System.out.println("IOException from waitForConnections() method");
        }
    }

    public void compileServerData(){
        String tempString = "";
        String[] playerData = new String[clients.size()];
        String[] labyrinthData = new String[clients.size()];
        String[] hermesData = new String[clients.size()];

        //separate the data into their types
        for (ClientRunnable c : clients){
            String[] cliData = c.getClientDataArray();
            if (cliData != null){
                for(String data : cliData){
                    String[] sepData = data.split("\\|");
                    if (sepData != null){
                        String[] indivPlayerData = (sepData[1]).split(",");
                        switch (sepData[0]){
                            case "Players":
                                playerData[Integer.parseInt(indivPlayerData[0])] = sepData[1];
                                break;
                            case "Labyrinth":
                                labyrinthData[Integer.parseInt(indivPlayerData[0])] = sepData[1];
                                break;
                            case "Hermes":
                                hermesData[Integer.parseInt(indivPlayerData[0])] = sepData[1];
                                break;
                        }
                    }
                        
                }
            }
        }

        //compile

        //PLAYER DATA:
        tempString += "Players|";
        for (int i = 0; i < playerData.length; i++){
            tempString += playerData[i] + "|";
        }
        tempString += "\n";

        //LABYRINTH DATA
        int button1 = 0, button2 = 0;
        String finalVersion;
        tempString += "Labyrinth|";
        for (int i = 0; i < labyrinthData.length; i++){
            for(String pData : labyrinthData){
                if (pData != null){
                    String[] separatedData = pData.split(",");
                    System.out.println(separatedData[1]);
                    
                    if (separatedData[1].equals("button_one")){
                        button1++;
                    } else if (separatedData[1].equals("button_two")){
                        button2++;
                    }
                }
            }

            if ( button1 > button2){
                finalVersion = "button_one"; 
            } else if ( button1 < button2){
                finalVersion = "button_two";
            } else {
                finalVersion = "default";
            }
            tempString += finalVersion;
            
            tempString += "\n";
        }
        serverData = tempString;
        System.out.println(tempString);
    }

    public void sendOutData(){
        for (ClientRunnable c : clients){
            c.sendDataToClient(serverData);
        }
    }

    private class ClientRunnable implements Runnable{
        private Socket clientSocket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int cid;
        private String name; 
        private String clientData;
        private String[] clientArray;

        public ClientRunnable(Socket sck, int n){
            clientSocket = sck;
            cid = n; 
            clientData = "nothing yet";

            try {
                dataIn = new DataInputStream(clientSocket.getInputStream());
                dataOut = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                System.out.println("IOException from ClientRunnable constructor");
            }
        }

        public void startThread(){
            Thread t = new Thread(this);
            t.start();
        }
        
        @Override
        public void run(){
            try {
                dataOut.writeUTF(Integer.toString(cid));// sending out client number

                // sending out data
                while (true) { 
                    clientData = dataIn.readUTF();
                    clientArray = clientData.split("\n");
                    //sendToClients(messageFromClient, this);
                }
            } catch (IOException e) {
                System.out.println("IOException from ClientRunnable's run() method");
                //clients.remove(this);
            }
        }

        public void sendDataToClient(String data){
            try {
                dataOut.writeUTF(data);
            } catch (IOException ex) {
            }
        }

        public String getClientData(){
            return clientData;
        }

        public String[] getClientDataArray(){
            return clientArray;
        }
    }

    private class ServerOut extends Thread {
        public ServerOut(){

        }

        public void run(){
            while (true) { 
                compileServerData();
                sendOutData();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    public static void main(String[] args) {
        GameServer s = new GameServer();
        s.closeSocketsOnShutdown();
        s.waitForConnections();
    }
}