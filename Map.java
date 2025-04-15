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

    public Map(String n){
        baseTileMap = new int[maxRow][maxColumn];
        decoTileMap = new int[maxRow][maxColumn];
        collisionMap = new int[maxRow][maxColumn];
        collidablesMap = new int[maxRow][maxColumn];

        teleporters = new ArrayList<>();
        interacts = new ArrayList<>();

        mapName = n;

        loadTeleporters();

        setUpMaps();
        loadMap();
    }

    public void loadMap(){
        load(baseTileMap, "base");
        load(decoTileMap, "deco");
        load(collisionMap, "collisions");
        load(collidablesMap, "collidables");

        loadInteract();
    }

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

    public void setUpMaps(){
        int originalCol = 0, originalRow = 0;
        while (originalCol < maxColumn && originalRow < maxRow){
            baseTileMap[originalCol][originalRow] = -1;
            decoTileMap[originalCol][originalRow] = -1;
            collisionMap[originalCol][originalRow] = -2;
            collidablesMap[originalCol][originalRow] = -1;

            originalCol++;
            if (originalCol == maxColumn){
                originalCol = 0;
                originalRow++;
            }
        }
    }

    // public void setUpMaps(){
    //     setUpMap(baseTileMap);
    //     setUpMap(decoTileMap);
    //     setUpMap(collisionMap);
    //     setUpMap(collidablesMap);
    // }

    public void setUpMap(int[][] specificMap){
        int originalCol = 0, originalRow = 0;
        while (originalCol < maxColumn && originalRow < maxRow){
            specificMap[originalCol][originalRow] = -1;

            originalCol++;
            if (originalCol == maxColumn){
                originalCol = 0;
                originalRow++;
            }
        }
    }

    private void loadTeleporters() {
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
                    
                    teleporters.add(new Teleporter(x, y, w, h, mapTo, newX, newY));
                }
            }
            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void loadInteract() {
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
                    switch (type) {
                        case "TREE":
                            interacts.add(new Tree(x, y));
                            break;
                        case "BUSH":
                            interacts.add(new Bush(x, y));
                            break;
                        case "ORE":
                            interacts.add(new Ore(x, y));
                    }
                }
            }

            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int[][] getBaseMap(){
        return baseTileMap;
    }

    public int[][] getDecoMap(){
        return decoTileMap;
    }

    public int[][] getColMap(){
        return collisionMap;
    }

    public int[][] getColAbleMap(){
        return collidablesMap;
    }

    public ArrayList<Teleporter> getTeleporters(){
        return teleporters;
    }

    public ArrayList<Interactable> getInteractables() {
        return interacts;
    }

    public int getHeight() {
        return mapHeight;
    }

    public int getWidth() {
        return mapWidth;
    }
}