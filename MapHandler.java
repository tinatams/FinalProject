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

    //BOTH PLAYERS
    public static final int SPAWN = 0;

    //PLAYER 1
    public static final int DEMETER = 1;
    public static final int DIONYSUS = 2;
    public static final int DHOUSE = 3;
    public static final int ASSIST1 = 4;
    public static final int ASSIST2 = 5;

    //PLAYER 2
    public static final int POSEIDON = 6;
    public static final int MINES = 7;
    public static final int WORKSHOP = 8;
    public static final int MINOTAUR = 9;
    public static final int LABYRINTH = 10;

    private int bufferSize = GameFrame.WIDTH;

    public MapHandler(Player pf){
        baseTiles = new BufferedImage[1000];
        decoTiles = new BufferedImage[1000];
        
        maps = new Map[11];
        pFollow = pf;

        currentMap = SPAWN;

        setUpMaps();
        setUpTiles();
    }

    public void setUpMaps(){
        maps[SPAWN] = new Map("spawn");
        maps[DEMETER] = new Map("demeter");
        maps[DIONYSUS] = new Map("dionysus");
        maps[DHOUSE] = new Map("d_house");  
        maps[ASSIST1] = new Map("assist1");
        //insert Assist2
        maps[POSEIDON] = new Map("poseidon");
        maps[MINES] = new Map("mine");
        maps[WORKSHOP] = new Map("workshop");
        maps[MINOTAUR] = new Map("mino");
        //inset Labyrinth

    }

    public void setUpTiles(){
        int spriteSize = 16;
        try {
            BufferedImage bkgTileSource = ImageIO.read(new File("./res/tileSets/CompiledCompact.png"));

            int row = 0, col = 0;

            for (int tileIndex = 0; tileIndex < 13*17; tileIndex++) {
                BufferedImage temp = bkgTileSource.getSubimage(col * spriteSize, row * spriteSize, spriteSize, spriteSize);
                baseTiles[tileIndex] = temp;

                col++;
                if (col == 13) {  // Changed from 11 to 13
                    col = 0;
                    row++;
                }
            }

            BufferedImage decoTileSource = ImageIO.read(new File("./res/tileSets/TileSetDeco.png"));

            row = 0;
            col = 0;

            for (int tileIndex = 0; tileIndex < 17*18; tileIndex++) {
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

        while (worldCol < Map.maxColumn && worldRow < Map.maxRow){
            int worldX = worldCol * GameFrame.SCALED, worldY = worldRow * GameFrame.SCALED;
            
            if (currentMap[worldCol][worldRow] != -1 && canDraw(worldX, worldY))
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

        while (worldCol < Map.maxColumn && worldRow < Map.maxRow){
            int worldX = worldCol * GameFrame.SCALED, worldY = worldRow * GameFrame.SCALED;
            
            if (currentMap[worldCol][worldRow] != -1 && canDraw(worldX, worldY))
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

        while (worldCol < Map.maxColumn && worldRow < Map.maxRow){
            int worldX = worldCol * GameFrame.SCALED, worldY = worldRow * GameFrame.SCALED;
            
            if (currentMap[worldCol][worldRow] != -1 && canDraw(worldX, worldY))
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
            if (pFollow.isColliding(tele)){
                currentMap = tele.teleportToMap();
                pFollow.setCollisionMap(maps[currentMap].getColMap());
                pFollow.teleportPlayer(tele.teleportPlayerX(), tele.teleportPlayerY());
            }
        }
    }

    public int getCurrentMap(){
        return currentMap;
    }

    public int getMapWidth(){
        Map cm = maps[currentMap];
        return cm.getWidth();
    }

    public int getMapHeight(){
        Map cm = maps[currentMap];
        return cm.getHeight();
    }

    public boolean canDraw(int X, int Y){
        return (X < pFollow.getWorldX() + bufferSize && X > pFollow.getWorldX() -  bufferSize &&
            Y < pFollow.getWorldY() + bufferSize && Y > pFollow.getScreenY() - bufferSize);
    }

 }