import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;
import javax.imageio.*;

public class BkgTileMan{
    private BufferedImage[] tiles; 
    private int[][] mapTile;
    private final int maxColumn = 300;
    private final int maxRow = 300;
    private final int tileSize = 48;

    private Player pFollow;

    public BkgTileMan(Player p){
        pFollow = p;
        tiles = new BufferedImage[1000];
        mapTile = new int[maxRow][maxColumn];
        setUpTiles();
        setUpMap();
    }

    public void setUpTiles(){
        int spriteSize = 16;
        try {
            BufferedImage bkgTileSource = ImageIO.read(new File("./res/CompiledCompact.png"));

            int row = 0, col = 0;

            for (int tileIndex = 0; tileIndex < 155; tileIndex++) {
                BufferedImage temp = bkgTileSource.getSubimage(col * spriteSize, row * spriteSize, spriteSize, spriteSize);
                tiles[tileIndex] = temp;

                col++;
                if (col == 13) {  // Changed from 11 to 13
                    col = 0;
                    row++;
                }
            }

        } catch (IOException ex) {
        }
    }

    public void setUpTiles2(){
        int spriteSize = 16;
        try {
            BufferedImage bkgTileSource = ImageIO.read(new File("./res/TilesetFloor.png"));

            //GRASS VARIANTS
            tiles[0] = bkgTileSource.getSubimage(0 * spriteSize, 12 * spriteSize, spriteSize, spriteSize); //264
            tiles[1] = bkgTileSource.getSubimage(2 * spriteSize, 11 * spriteSize, spriteSize, spriteSize); //244
            tiles[2] = bkgTileSource.getSubimage(3 * spriteSize, 11 * spriteSize, spriteSize, spriteSize); //245

            //GRASS & DIRT BLOCKS SQUARE
            tiles[3] = bkgTileSource.getSubimage(0 * spriteSize, 7 * spriteSize, spriteSize, spriteSize); //154
            tiles[4] = bkgTileSource.getSubimage(1 * spriteSize, 7 * spriteSize, spriteSize, spriteSize); //155
            tiles[5] = bkgTileSource.getSubimage(2 * spriteSize, 7 * spriteSize, spriteSize, spriteSize); //156

            tiles[6] = bkgTileSource.getSubimage(0 * spriteSize, 8 * spriteSize, spriteSize, spriteSize); //176
            tiles[7] = bkgTileSource.getSubimage(1 * spriteSize, 8 * spriteSize, spriteSize, spriteSize); //177
            tiles[8] = bkgTileSource.getSubimage(2 * spriteSize, 8 * spriteSize, spriteSize, spriteSize); //178

            tiles[9] = bkgTileSource.getSubimage(0 * spriteSize, 9 * spriteSize, spriteSize, spriteSize); //198
            tiles[10] = bkgTileSource.getSubimage(1 * spriteSize, 9 * spriteSize, spriteSize, spriteSize); //199
            tiles[11] = bkgTileSource.getSubimage(2 * spriteSize, 9 * spriteSize, spriteSize, spriteSize); //200

            //DIRT VARIANTS
            tiles[12] = bkgTileSource.getSubimage(0 * spriteSize, 11 * spriteSize, spriteSize, spriteSize); //242
            tiles[13] = bkgTileSource.getSubimage(1 * spriteSize, 11 * spriteSize, spriteSize, spriteSize); //243


        } catch (IOException ex) {
        }
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
    public void draw(Graphics2D g2d){
        int worldCol = 0, worldRow = 0;
        int bufferSize = 700;

        while (worldCol < maxColumn && worldRow < maxRow){
            int worldX = worldCol * tileSize, worldY = worldRow * tileSize;
            
            
            if (mapTile[worldCol][worldRow] != -1 &&
            worldX < pFollow.getWorldX() + bufferSize && worldX > pFollow.getWorldX() -  bufferSize &&
            worldY < pFollow.getWorldY() + bufferSize && worldY > pFollow.getScreenY() - bufferSize)
            g2d.drawImage(tiles[mapTile[worldCol][worldRow]], worldX, worldY, tileSize, tileSize, null);

            worldCol ++;
            if (worldCol == maxColumn){
                worldCol = 0;
                worldRow ++;
            }
        }
    }

    
}