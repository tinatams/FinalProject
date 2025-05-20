/**
    The GameFrame manages clients and sockets. Connects clients together and facilitates
    sending of information from clients to each other. Collects, processes, and redistributes 
    data for real-time in game updates. 

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
import java.util.*;

public class GameServer{
    private ServerSocket ss;
    private ArrayList<Socket> sockets;
    private ArrayList<ClientRunnable> clients;
    private int clientNum = 0;
    private String serverData,hasHermes, hermesLastInv;
    private String latestQuest="1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
    private boolean canSwitch, newInteraction;
    private ArrayList<Integer> activeQuests;

    /**
        Constructor that instantiates the default values of the GameServer.  
        Sets the socket of the server to 60003
    **/
    public GameServer(){
        serverData = "nothing yet";
        hermesLastInv = "NULL";
        sockets = new ArrayList<Socket>();
        clients = new ArrayList<ClientRunnable>();

        hasHermes = "ODD";
        newInteraction = true;
        activeQuests = new ArrayList<Integer>();

        try {
            ss = new ServerSocket(60003);
        } catch (IOException e) {
            System.out.println("IOException from ChatServer constructor");
        }
        System.out.println("THE GAME SERVER HAS BEEN CREATED");
    }

    /**
        Method that switches hermes from one island to the other
    **/
    public void passHermes(){
        if (canSwitch){
            if (hasHermes.equals("ODD")){
                hasHermes = "EVEN";
            } else if (hasHermes.equals("EVEN")){
                hasHermes = "ODD";
            }
        }
    }

    /**
        Method closes all connected sockets when, program is closed.
    **/
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

    /**
        Waits for client connections.
        When a client connects it creates a new instance of ClientRunnable and passes the socket and clientNumber
        of the client. 

        Starts the Thread of the client. 
    **/
    public void waitForConnections(){
        try {
            System.out.println("NOW ACCEPTING CONNECTIONS...");
            ServerOut so = new ServerOut();
            so.start();
            
            while (true) { 
                Socket sock = ss.accept();
                sockets.add(sock);

                ClientRunnable cr = new ClientRunnable(sock, clientNum);
                System.out.println("CLIENT " + clientNum +" joined");
                clientNum++;
                cr.startThread();
                clients.add(cr);
            }
        } catch (IOException e) {
            System.out.println("IOException from waitForConnections() method");
        }
    }

    /**
        Compiles and unifies client data for distribution. 

        Separates client data by 'type' and processes data appropriately before
        recompiling for redistribution.
    **/
    public void compileServerData(){
        String tempString = "";
        String[] playerData = new String[clients.size()];
        String[] labyrinthData = new String[clients.size()];
        String[] hermesData = new String[clients.size()];
        String[] questData = new String[clients.size()];

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
                            case "Quest":
                            if(sepData[1]!=null){
                                questData[Integer.parseInt(indivPlayerData[0])]=sepData[1];
                                // System.out.println(questData[Integer.parseInt(indivPlayerData[0])]);
                            }
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

        //HERMES DATA
        tempString += "Hermes|";
        boolean isEmpty = true;
        for (String hData : hermesData){
            if (hData != null){
                String[] sepHermData = hData.split(",");
                if ( sepHermData[1].equals("SEND")){
                    //System.out.println(hasHermes);
                    passHermes();
                    canSwitch = false;
                } else {
                    canSwitch = true;
                }

                String finalHermInventory = sepHermData[2];

                if (newInteraction){
                    newInteraction = false;

                    if (!(hermesLastInv).equals(finalHermInventory)){
                        finalHermInventory = hermesLastInv;
                    }
                }
                tempString += String.format("%s,%s,%s", sepHermData[0], hasHermes, finalHermInventory);
                hermesLastInv = finalHermInventory;

                isEmpty = false;
            }
        }

        if (isEmpty){
            tempString += "null,"+ hasHermes;
            newInteraction = true;
        }

        tempString += "\n";


        //Quest Data
        
        tempString += "Quest|null,";
        String result = "";
        for (String qData : questData) {
            if (qData != null) {
                String[] quests = qData.split(",");
                int sumcurrent = 0;
                for (int i = 1; i < quests.length; i++) {
                    sumcurrent += Integer.parseInt(quests[i]);
                }

                String[] pastquests = latestQuest.split(",");
                int sumpast = 0;
                for (int i = 0; i < pastquests.length; i++) {
                    sumpast += Integer.parseInt(pastquests[i]);
                }
                
                
        
                if (sumcurrent > sumpast) {
                    result = "";
                    for (int i = 1; i < quests.length; i++) {
                        result += quests[i];
                        if (i != quests.length - 1) {
                            result += ",";
                        }
                    }
                    latestQuest = result;
                    
                    pastquests = latestQuest.split(",");
                }
                else{
                    result = "";
                    for (int i = 0; i < pastquests.length; i++) {
                        result += pastquests[i];
                        if (i != pastquests.length - 1) {
                            result += ",";
                        }
                    }

                }
            }
    }
        tempString += result;
        serverData = tempString;
        //System.out.println(newInteraction);
        //System.out.println(serverData);
    }

    /**
        Sends out the server data to the clients.
    **/
    public void sendOutData(){
        for (ClientRunnable c : clients){
            c.sendDataToClient(serverData);
        }
    }

    /**
        Inner class that represents the client thread. Handles sending and recieving data from client. 
    **/
    private class ClientRunnable implements Runnable{
        private Socket clientSocket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int cid;
        private String name; 
        private String clientData;
        private String[] clientArray;

        /**
            Constructor that instantiates needed classes and sets defualt values

            @param sck is the socket of the client
            @param n is the client number

            derives the input and output streams from the client socket. 
        **/
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

        /**
            Starts the client thread by passing this thread as the Runnable object.
        **/
        public void startThread(){
            Thread t = new Thread(this);
            t.start();
        }
        
        /**
            Gets data from the client and splits it into the Client data array, which is the data
            separated by 'type'. Sends out client data, before constantly waiting for client data. 
        **/
        @Override
        public void run(){
            try {
                dataOut.writeUTF(Integer.toString(cid));// sending out client number

                // sending out data
                while (true) { 
                    clientData = dataIn.readUTF();
                    clientArray = clientData.split("\n");
                }
            } catch (IOException e) {
                System.out.println("IOException from ClientRunnable's run() method");
                //System.out.println("Client left (booooooo) ");
                //clients.remove(this);
                //sockets.remove(clientSocket);
            }
        }

        /**
            Sends data to client

            @param data is the data to be sent out 
        **/
        public void sendDataToClient(String data){
            try {
                dataOut.writeUTF(data);
                dataOut.flush();
            } catch (IOException ex) {
            }
        }

        /**
            Gets the clientData of the client

            @return clientdata
        **/
        public String getClientData(){
            return clientData;
        }

        /**
            Gets the separated data of the client

            @return clientArray 
        **/
        public String[] getClientDataArray(){
            return clientArray;
        }
    }

    /**
        Inner class that sends out data to clients. Extends thread to run concurrently
        with other tasks.  
    **/
    private class ServerOut extends Thread {
        public ServerOut(){}

        /**
            Sends out data to clients, every 10 miliseconds. Calls compileData and SendOutData.
        **/
        public void run(){
            while (true) { 
                compileServerData();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                }
                sendOutData();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    /**
       Instantiates the GameServer, sets up a shutdown hook to close sockets, and starts waiting on 
       client connections.
    **/
    public static void main(String[] args) {
        GameServer s = new GameServer();
        s.closeSocketsOnShutdown();
        s.waitForConnections();
    }
}