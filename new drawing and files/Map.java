import java.io.*;
import java.util.*;

public class Map{
    public static final int maxColumn =  300;
    public static final int maxRow = 300;

    private String mapName;

    private int[][] baseTileMap;
    private int[][] decoTileMap;
    private int[][] collisionMap;
    private int[][] collidablesMap;

    private ArrayList<Teleporter> teleporters;

    public Map(String n){
        baseTileMap = new int[maxRow][maxColumn];
        decoTileMap = new int[maxRow][maxColumn];
        collisionMap = new int[maxRow][maxColumn];
        collidablesMap = new int[maxRow][maxColumn];

        teleporters = new ArrayList<>();

        mapName = n;

        loadTeleporters();

        setUpMap();
        loadMap();
    }

    public void loadMap(){
        loadBaseMap();
        loadDecoMap();
        loadColMap();
        loadColAbleMap();
    }

    private void loadBaseMap(){
        int row = 0, col = 0;
        try {
            File map = new File(String.format("./res/maps/%s/base.txt",mapName));
            Scanner mapReader = new Scanner(map);
            while (mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapLineSplit = mapLine.split("\t");
                for (String num : mapLineSplit){
                    baseTileMap[col][row] = Integer.parseInt(num) ;
                    col ++;
                }
                row++;
                col = 0;
                
            }
            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void loadDecoMap(){
        int row = 0, col = 0;
        try {
            File map = new File(String.format("./res/maps/%s/deco.txt",mapName));
            Scanner mapReader = new Scanner(map);
            while (mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapLineSplit = mapLine.split("\t");
                for (String num : mapLineSplit){
                    decoTileMap[col][row] = Integer.parseInt(num) ;
                    col ++;
                }
                row++;
                col = 0;
                
            }
            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void loadColMap(){
        int row = 0, col = 0;
        try {
            File map = new File(String.format("./res/maps/%s/collisions.txt",mapName));
            Scanner mapReader = new Scanner(map);
            while (mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapLineSplit = mapLine.split("\t");
                for (String num : mapLineSplit){
                    collisionMap[col][row] = Integer.parseInt(num) ;
                    col ++;
                }
                row++;
                col = 0;
                
            }
            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    private void loadColAbleMap(){
        int row = 0, col = 0;
        try {
            File map = new File(String.format("./res/maps/%s/collidables.txt",mapName));
            Scanner mapReader = new Scanner(map);
            while (mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapLineSplit = mapLine.split("\t");
                for (String num : mapLineSplit){
                    collidablesMap[col][row] = Integer.parseInt(num) ;
                    col ++;
                }
                row++;
                col = 0;
                
            }
            mapReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setUpMap(){
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
                    int newX = Integer.parseInt(mapData[5]) * GameFrame.SCALED;
                    int newY = Integer.parseInt(mapData[6]) * GameFrame.SCALED;
                    
                    teleporters.add(new Teleporter(x, y, w, h, mapTo, newX, newY));
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
}