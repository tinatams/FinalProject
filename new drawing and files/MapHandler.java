import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class MapHandler{
    private Map[] maps; 
    private Player pFollow;

    private BufferedImage[] baseTiles;
    private BufferedImage[] decoTiles;

    private int currentMap;
    public static final int SPAWN = 0;
    public static final int DEMETER = 1;
    public static final int ATHENA = 2;
    public static final int ASSIST1 = 3;
    public static final int ASSIST2 = 4;

    public static final int POSIEDON = 5;
    public static final int MINES = 6;
    public static final int LABYRINTH = 7;

    public MapHandler(Player pf){
        baseTiles = new BufferedImage[1000];
        decoTiles = new BufferedImage[1000];
        
        maps = new Map[2];
        pFollow = pf;

        currentMap = SPAWN;

        setUpMaps();
        setUpTiles();
    }

    public void setUpMaps(){
        maps[SPAWN] = new Map("spawn");
        maps[DEMETER] = new Map("demeter");
    }

    public void setUpTiles(){
        int spriteSize = 16;
        try {
            BufferedImage bkgTileSource = ImageIO.read(new File("./res/CompiledCompact.png"));

            int row = 0, col = 0;

            for (int tileIndex = 0; tileIndex < 155; tileIndex++) {
                BufferedImage temp = bkgTileSource.getSubimage(col * spriteSize, row * spriteSize, spriteSize, spriteSize);
                baseTiles[tileIndex] = temp;

                col++;
                if (col == 13) {  // Changed from 11 to 13
                    col = 0;
                    row++;
                }
            }

            BufferedImage decoTileSource = ImageIO.read(new File("./res/TileSetDeco.png"));

            row = 0;
            col = 0;

            for (int tileIndex = 0; tileIndex < 220; tileIndex++) {
                BufferedImage temp = decoTileSource.getSubimage(col * spriteSize, row * spriteSize, spriteSize, spriteSize);
                decoTiles[tileIndex] = temp;

                col++;
                if (col == 17) {  // Changed from 11 to 13
                    col = 0;
                    row++;
                }
            }

        } catch (IOException ex) {
        }

        
    }

    public void drawBase(Graphics2D g2d){
        Map cm = maps[currentMap];
        int[][] currentMap = cm.getBaseMap();

        int worldCol = 0, worldRow = 0;
        int bufferSize = 700;

        while (worldCol < Map.maxColumn && worldRow < Map.maxRow){
            int worldX = worldCol * GameFrame.SCALED, worldY = worldRow * GameFrame.SCALED;
            
            
            if (currentMap[worldCol][worldRow] != -1 &&
            worldX < pFollow.getWorldX() + bufferSize && worldX > pFollow.getWorldX() -  bufferSize &&
            worldY < pFollow.getWorldY() + bufferSize && worldY > pFollow.getScreenY() - bufferSize)
            g2d.drawImage(baseTiles[currentMap[worldCol][worldRow]], worldX, worldY, GameFrame.SCALED, GameFrame.SCALED, null);

            worldCol ++;
            if (worldCol == Map.maxColumn){
                worldCol = 0;
                worldRow ++;
            }
        }

    }

    public void drawDeco(Graphics2D g2d){
        Map cm = maps[currentMap];
        int[][] currentMap = cm.getDecoMap();

        int worldCol = 0, worldRow = 0;
        int bufferSize = 700;

        while (worldCol < Map.maxColumn && worldRow < Map.maxRow){
            int worldX = worldCol * GameFrame.SCALED, worldY = worldRow * GameFrame.SCALED;
            
            
            if (currentMap[worldCol][worldRow] != -1 &&
            worldX < pFollow.getWorldX() + bufferSize && worldX > pFollow.getWorldX() -  bufferSize &&
            worldY < pFollow.getWorldY() + bufferSize && worldY > pFollow.getScreenY() - bufferSize)
            g2d.drawImage(decoTiles[currentMap[worldCol][worldRow]], worldX, worldY, GameFrame.SCALED, GameFrame.SCALED, null);

            worldCol ++;
            if (worldCol == Map.maxColumn){
                worldCol = 0;
                worldRow ++;
            }
        }

    }

    public void drawColAbles(Graphics2D g2d){
        Map cm = maps[currentMap];
        int[][] currentMap = cm.getColAbleMap();

        int worldCol = 0, worldRow = 0;
        int bufferSize = 700;

        while (worldCol < Map.maxColumn && worldRow < Map.maxRow){
            int worldX = worldCol * GameFrame.SCALED, worldY = worldRow * GameFrame.SCALED;
            
            
            if (currentMap[worldCol][worldRow] != -1 &&
            worldX < pFollow.getWorldX() + bufferSize && worldX > pFollow.getWorldX() -  bufferSize &&
            worldY < pFollow.getWorldY() + bufferSize && worldY > pFollow.getScreenY() - bufferSize)
            g2d.drawImage(decoTiles[currentMap[worldCol][worldRow]], worldX, worldY, GameFrame.SCALED, GameFrame.SCALED, null);

            worldCol ++;
            if (worldCol == Map.maxColumn){
                worldCol = 0;
                worldRow ++;
            }
        }
    }

    public int[][] getColMap(){
        Map cm = maps[currentMap];
        return cm.getColMap();
    }
    
    public void update(){
        Map cm = maps[currentMap];
        ArrayList<Teleporter> teleporters = cm.getTeleporters();

        for (Teleporter tele : teleporters){
            if (tele.isColliding(pFollow)){
                currentMap = tele.teleportToMap();
                pFollow.setCollisionMap(maps[currentMap].getColMap());
                pFollow.teleportPlayer(tele.teleportPlayerX(), tele.teleportPlayerY());
            }
        }
    }
 }