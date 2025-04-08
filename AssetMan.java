import java.io.*;
import java.util.Scanner;


public class AssetMan{
    private int[][] mapTile;
    private final int maxColumn = 50;
    private final int maxRow = 50;
    private final int tileSize = 48;

    public AssetMan(){
        mapTile = new int[maxRow][maxColumn];
        setUpMap();
    }

    public void loadMap(String level){
        int row = 0, col = 0;
        try {
            File map = new File(String.format("%s.txt",level));
            Scanner mapReader = new Scanner(map);
            while (mapReader.hasNextLine()) {
                String mapLine = mapReader.nextLine();
                String[] mapLineSplit = mapLine.split("\t");
                for (String num : mapLineSplit){
                    mapTile[col][row] = Integer.parseInt(num) ;
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
            mapTile[originalCol][originalRow] = -1;

            originalCol++;
            if (originalCol == maxColumn){
                originalCol = 0;
                originalRow++;
            }
        }
    }

    // public void draw(Graphics2D g2d){
    //     int worldCol = 0, worldRow = 0;
    //     int bufferSize = 500;

    //     while (worldCol < maxColumn && worldRow < maxRow){
    //         int worldX = worldCol * tileSize, worldY = worldRow * tileSize;
            
            
    //         if (mapTile[worldCol][worldRow] != -1)
    //         g2d.drawImage(tiles[mapTile[worldCol][worldRow]], worldX, worldY, tileSize, tileSize, null);

    //         worldCol ++;
    //         if (worldCol == maxColumn){
    //             worldCol = 0;
    //             worldRow ++;
    //         }
    //     }
    // }
}