import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class MapHandler{
    int number = 1;

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
        maps[LABYRINTH] = new Labyrinth();

        for (Map mapObj : maps){
            if (mapObj != null){
                mapObj.setUpMaps();
                mapObj.loadMap();
            }
        }
        
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

    public void drawInteracts(Graphics2D g2d){
        Map cm = maps[currentMap];
        ArrayList<Interactable> interacts = cm.getInteractables();

        for (Interactable interactObj : interacts){
            interactObj.draw(g2d);
        }
    }

    public void update(){
        Map cm = maps[currentMap];
        ArrayList<Teleporter> teleporters = cm.getTeleporters();
        ArrayList<Interactable> interactables = cm.getInteractables();

        for (Teleporter tele : teleporters){
            if (pFollow.isColliding(tele)){
                currentMap = tele.teleportToMap();
                pFollow.teleportPlayer(tele.teleportPlayerX(), tele.teleportPlayerY());
            }
        }

        for (int i = 0; i < interactables.size(); i++){
            Interactable interactionObj = interactables.get(i);
            if (interactionObj instanceof Tree){
                Tree treeObj = (Tree) interactionObj;
                if(treeObj.getHealth() <=0){
                    Timer treeTimer = new Timer();
                    TimerTask newTree = new TimerTask(){
                        @Override 
                        public void run(){
                            interactables.add(new Tree(treeObj.getWorldX()/48,treeObj.getWorldY()/48));
                        }
                    };
                    interactables.remove(interactionObj);
                    treeTimer.schedule(newTree, 240000);
                }
            }

            if (interactionObj instanceof Ore){
                Ore oreObj = (Ore) interactionObj;
                if(oreObj.getHealth() <=0){
                    interactables.remove(interactionObj);
                }
            }
        }
    }

    public void changeVersion(){
        number++;
        if (number > 3) number = 1;
        String newVersion = "default";
        switch (number){
            case 1:
                newVersion = "default";
                break;
            case 2:
                newVersion = "button_one";
                break;
            case 3: 
                newVersion = "button_two";
                break;
        }
        if (maps[currentMap] instanceof Labyrinth){
            Labyrinth cm = (Labyrinth) maps[currentMap];
            cm.loadNewMap(newVersion);
        }
    }
    
    public int[][] getColMap(){
        return maps[currentMap].getColMap();
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
            Y < pFollow.getWorldY() + bufferSize && Y > pFollow.getWorldY() - bufferSize);
    }

    public ArrayList<Interactable> getInteractables() {
        return maps[currentMap].getInteractables();
    }

 }