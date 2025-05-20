/**
    Map Class in charge of the collidables, decorations and borders needed to be drawn. It also handles the 
    interactable items.
 
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
import java.util.*;

public class Map{
    protected static final int maxColumn =  300;
    protected static final int maxRow = 300;

    protected String mapName;

    protected int[][] baseTileMap;
    protected int[][] decoTileMap;
    protected int[][] collisionMap;
    protected int[][] collidablesMap;

    protected int mapWidth;
    protected int mapHeight;

    protected ArrayList<Teleporter> teleporters;
    protected ArrayList<Interactable> interacts;
    private ArrayList<NPC> NPCs;

    private EntityGenerator eg;
    /**
        @param String n Constructor for a map per map name given
    **/
    public Map(String n){
        baseTileMap = new int[maxColumn][maxRow];
        decoTileMap = new int[maxColumn][maxRow];
        collisionMap = new int[maxColumn][maxRow];
        collidablesMap = new int[maxColumn][maxRow];

        teleporters = new ArrayList<>();
        interacts = new ArrayList<>();
        NPCs= new ArrayList<>();

        mapName = n;

        loadTeleporters();
        loadNPCs();

        eg = new EntityGenerator();
    }
    
    /**
        calls the load method for all the assests needed like decoration and collisions.
    **/
    public void loadMap(){
        load(decoTileMap, "deco");
        load(collisionMap, "collisions");
        load(collidablesMap, "collidables");
        load(baseTileMap, "base");

        loadInteract();
    }

    /**
        @param int[][] mapArray, String version. loads the map and reads every line
    **/
    protected void load(int[][] mapArray, String version){
        int row = 0, col = 0;
        try {
            File map = new File(String.format("./res/maps/%s/%s.csv", mapName, version));
            Scanner mapReader = new Scanner(map);
            while (mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapLineSplit = mapLine.split(",");
                for (String num : mapLineSplit){
                    mapArray[col][row] = Integer.parseInt(num) ;
                    col ++;
                }
        
                mapWidth = col;
                row++;
                col = 0;
            }  
            mapHeight = row;
            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
        sets up all the different assets for the map.
    **/
    public void setUpMaps(){
        setUpMap(baseTileMap);
        setUpMap(decoTileMap);
        setUpMap(collisionMap);
        setUpMap(collidablesMap);
    }

    /**
        @param int[][] specificMap sets up the array for the specific map
    **/
    public void setUpMap(int[][] specificMap){
        int originalCol = 0, originalRow = 0;
        while (originalCol < maxColumn && originalRow < maxRow){
            int defaultNum = -1;

            if (specificMap == collisionMap){
                defaultNum = -2;
            }

            specificMap[originalCol][originalRow] = defaultNum;

            originalCol++;
            if (originalCol == maxColumn){
                originalCol = 0;
                originalRow++;
            }
        }
    }

    /**
        loads the Teleporters by reading from the txt file
    **/
    protected void loadTeleporters() {
        try {
            File map = new File(String.format("./res/maps/%s/teleporters.txt",mapName));
            Scanner mapReader = new Scanner(map);
            while (mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapData = mapLine.split(",");
                if (mapData.length > 0){
                    int x = Integer.parseInt(mapData[0]) * GameFrame.SCALED;
                    int y = Integer.parseInt(mapData[1]) * GameFrame.SCALED;
                    int w = Integer.parseInt(mapData[2]) * GameFrame.SCALED;
                    int h = Integer.parseInt(mapData[3]) * GameFrame.SCALED;
                    int mapTo = Integer.parseInt(mapData[4]);
                    int newX = Integer.parseInt(mapData[5]) * GameFrame.SCALED; //player position in new map
                    int newY = Integer.parseInt(mapData[6]) * GameFrame.SCALED; //player position in new map
                    int direction = Integer.parseInt(mapData[7]);

                    if (mapData.length >= 9){
                        if (mapData[8].equals("LOCK")){
                            teleporters.add(new Lock(x, y, w, h, mapTo, newX, newY, direction, mapData[9]));
                        } else if (mapData[8].equals("SPIKE")){
                            teleporters.add(new SpikeTrap(x, y, mapTo, newX, newY));
                        }
                    } else {
                        teleporters.add(new Teleporter(x, y, w, h, mapTo, newX, newY, direction));
                    }  
                }
            }
            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
        loads the Interactables by reading from the txt file
    **/
    protected void loadInteract() {
        try {
            File map = new File(String.format("./res/maps/%s/interact.txt", this.mapName));
            Scanner mapReader = new Scanner(map);

            while(mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapData = mapLine.split(",");
                if (mapData.length > 0) {
                    String type = mapData[0];
                    int x = Integer.parseInt(mapData[1]);
                    int y = Integer.parseInt(mapData[2]);

                    if (!type.equals("KEY") && !type.equals(FishArea.ITEMNAME)){
                        interacts.add(eg.newInteractable(type, x, y));
                    } 
                    else {
                        if (type.equals("KEY")) interacts.add(new KeyItem(x, y, mapData[3]));
                        else if (type.equals(FishArea.ITEMNAME)) interacts.add(new FishArea(x, y, Integer.parseInt(mapData[3]), Integer.parseInt(mapData[4])));
                    }
                }
            }

            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
        loads the NPCs by reading from the txt file
    **/
    private void loadNPCs() {
        try {
            File map = new File(String.format("./res/maps/%s/NPCs.txt",mapName));
            Scanner mapReader = new Scanner(map);
            while (mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapData = mapLine.split(",");
                if (mapData.length > 0){
                    String skin =mapData[0];
                    int x = Integer.parseInt(mapData[1]) * GameFrame.SCALED;
                    int y = Integer.parseInt(mapData[2]) * GameFrame.SCALED;
                    String dialogue=mapData[3];
                    String[] dialogues=dialogue.split("/n");

                    if (skin.equals("Hermes")){
                        NPCs.add(new Hermes(x,y,Integer.parseInt(mapData[3]) * GameFrame.SCALED, Integer.parseInt(mapData[4]) * GameFrame.SCALED));
                    }
                    else if(skin.equals("Athena")){
                        NPCs.add(new Athena(x,y));
                    }
                    else if(skin.equals("Apollo")){
                        NPCs.add(new Apollo(x,y));
                    }
                    else if(skin.equals("Artemis")){
                        NPCs.add(new Artemis(x,y));
                    }
                    else if(skin.equals("Hephaestus")){
                        NPCs.add(new Hephaestus(x,y));
                    }
                    else if(skin.equals("Poseidon")){
                        NPCs.add(new Poseidon(x,y));
                    }
                    else if(skin.equals("Demeter")){
                        NPCs.add(new Demeter(x,y));
                    }
                    else if(skin.equals("Dog")){
                        NPCs.add(new Dog(x,y));
                    }
                    else if(skin.equals("Minotaur")){
                        NPCs.add(new Minotaur(x,y));
                    }
                    else if(skin.equals("Dionysus")){
                        NPCs.add(new Dionysus(x,y));
                    }
                    else{
                        NPCs.add(new Athena(x,y));
                    }

                    
                }
            }
            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
        @return int[][] getter for basemap
    **/
    public int[][] getBaseMap(){
        return baseTileMap;
    }

    /**
        @return int[][] getter for decorations
    **/
    public int[][] getDecoMap(){
        return decoTileMap;
    }

    /**
         @return int[][] getter for collitions
    **/
    public int[][] getColMap(){
        return collisionMap;
    }

    /**
        @return int[][] getter for collidables
    **/
    public int[][] getColAbleMap(){
        return collidablesMap;
    }

    /**
        @param ArrayList<Teleporter> getter for Teleporters
    **/
    public ArrayList<Teleporter> getTeleporters(){
        return teleporters;
    }

    /**
        @return ArrayList<Interactable> getter for Interactables
    **/
    public ArrayList<Interactable> getInteractables() {
        return interacts;
    }

    /**
        @return ArrayList<NPC> getter for NPC 
    **/
    public ArrayList<NPC> getNPCs(){
        return NPCs;
    }

    /**
        @return int getter for height
    **/
    public int getHeight() {
        return mapHeight;
    }

    /**
        @return int getter for width
    **/
    public int getWidth() {
        return mapWidth;
    }
}