import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class MapHandler{

  
    private int clientNumber;

    private Map[] maps; 
    private Player pFollow;

    private BufferedImage[] baseTiles;
    private BufferedImage[] decoTiles;

    private int currentMap;
    private Hermes hermes;

    private static boolean demeter_sacrifice=false;

    //BOTH PLAYERS
    public static final int SPAWN = 0;

    //PLAYER 1
    public static final int DEMETER = 1;
    public static final int DIONYSUS = 2;
    public static final int DHOUSE = 3;
    public static final int ASSIST1 = 4;
    public static final int ASSIST2 = 5;
    public static final int SECRET = 11;

    //PLAYER 2
    public static final int POSEIDON = 6;
    public static final int MINES = 7;
    public static final int WORKSHOP = 8;
    public static final int MINOTAUR = 9;
    public static final int LABYRINTH = 10;

    private int bufferSize = GameFrame.WIDTH;

    public MapHandler(GameFrame frame){ //Constructor for a map handler per client
        clientNumber = frame.getClientNumber();

        baseTiles = new BufferedImage[1000];
        decoTiles = new BufferedImage[1000];
        
        maps = new Map[12];
        pFollow = frame.getSelected();

        currentMap = SPAWN;

        setUpMaps();
        setUpTiles();
    }

    public void setUpMaps(){ //the different options for maps and which one it should draw
        maps[SPAWN] = new Map("spawn");
        maps[DEMETER] = new Map("demeter");
        maps[DIONYSUS] = new Map("dionysus");
        maps[DHOUSE] = new Map("d_house");  
        maps[ASSIST1] = new AssistOne();
        maps[SECRET] = new Map("secret");
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

        for (NPC npc : maps[SPAWN].getNPCs()){
            if (Hermes.name.equals(npc.getName())){
                hermes = (Hermes) npc;
                break;
            }
        }
        
    }

    public void setUpTiles(){ //sets up the tile sets for the maps to choose to draw from
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

    public void drawBase(Graphics2D g2d){ //draw function for the base map based on the dimensions and tiles read from the text file
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

    public void drawDeco(Graphics2D g2d){ //draw function for the decorations based on the dimensions and tiles read from the text file
        Map cm = maps[currentMap];
        int[][] currentMap = cm.getDecoMap();

        int worldCol = 0, worldRow = 0;

        while (worldCol < Map.maxColumn && worldRow < Map.maxRow){
            int worldX = worldCol * GameFrame.SCALED, worldY = worldRow * GameFrame.SCALED;
            
            if (currentMap[worldCol][worldRow] != -1 && canDraw(worldX, worldY))
            g2d.drawImage(decoTiles[currentMap[worldCol][worldRow]], worldX, worldY, GameFrame.SCALED, GameFrame.SCALED, null);

            worldCol++;
            if (worldCol == Map.maxColumn){
                worldCol = 0;
                worldRow ++;
            }
        }

    }

    public void drawColAbles(Graphics2D g2d){ //draw function for the collidables based on the dimensions and tiles read from the text file
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

    public void drawInteracts(Graphics2D g2d){ //draw function for the interactables based on the dimensions and tiles read from the text file
        Map cm = maps[currentMap];
        ArrayList<Interactable> interacts = cm.getInteractables();

        for (Interactable interactObj : interacts){
            if (interactObj instanceof Tree){
                if(demeter_sacrifice){
                    interactObj.draw(g2d);
                    ((Tree) interactObj).setInteractionBox(new Rectangle(interactObj.getWorldX() - GameFrame.SCALED/2,interactObj.getWorldY() - GameFrame.SCALED/2, interactObj.getSpriteW() + GameFrame.SCALED, interactObj.getSpriteH()+GameFrame.SCALED) );
                    ((Tree) interactObj).setHitBox(new Rectangle(interactObj.getWorldX()+20,interactObj.getWorldY()+10,interactObj.getSpriteW()-40,interactObj.getSpriteH()-15));
                }
                else{
                    ((Tree) interactObj).setInteractionBox(new Rectangle(0,0,0,0));
                    ((Tree) interactObj).setHitBox(new Rectangle(0,0,0,0));

                }
            }
            else{
                interactObj.draw(g2d);
            }
        }

        ArrayList<Teleporter> teleporters = cm.getTeleporters();
        for (Teleporter tele : teleporters){
            if (tele instanceof SpikeTrap spikes){
                    spikes.draw(g2d);
                }
        }
    }

    public void drawNPCs(Graphics2D g2d){ //draw function for the NPCs based on the dimensions and tiles read from the text file
        Map cm = maps[currentMap];
        ArrayList<NPC> NPCs = cm.getNPCs();
        for (NPC npc : NPCs){
            npc.draw(g2d);
        }
        
    }

    public void update(){ //update method checks if the player is colliding with teleporters or interactables
        Map cm = maps[currentMap];
        ArrayList<Teleporter> teleporters = cm.getTeleporters();
        ArrayList<Interactable> interactables = cm.getInteractables();

        for (Teleporter tele : teleporters){
            if (pFollow.isColliding(tele) && !(tele instanceof Lock) && !(tele instanceof SpikeTrap)){
                if(pFollow.getDirection() == tele.getDirection()){
                    if (currentMap == WORKSHOP){
                        SoundHandler sh = pFollow.getFrame().getSoundHandler();
                        sh.playEffect(SoundHandler.STAIRS);
                    }
                    currentMap = tele.teleportToMap();
                    if (currentMap == WORKSHOP){
                        SoundHandler sh = pFollow.getFrame().getSoundHandler();
                        sh.playEffect(SoundHandler.STAIRS);
                    }
                    pFollow.teleportPlayer(tele.teleportPlayerX(), tele.teleportPlayerY());
                }
            } else if (pFollow.isColliding(tele) && (tele instanceof Lock)){
                if (pFollow.getDirection() == tele.getDirection()){Lock lockObj = (Lock) tele;
                    ArrayList<SuperItem> keyItems = pFollow.getNotStackableItem("KEY");
                    KeyItem keyObj;
                    if (keyItems.size() > 0){
                        for (SuperItem k : keyItems){
                            keyObj = (KeyItem) k;
                            if((keyObj.getLockName()).equals(lockObj.getlockName())){
                                lockObj.setLocked(false);
                                pFollow.discardItem(keyObj);
                                break;
                            }
                        }
                    }

                    if (!lockObj.isLocked()){
                        currentMap = tele.teleportToMap();
                        pFollow.teleportPlayer(tele.teleportPlayerX(), tele.teleportPlayerY());
                    }
                }

            }

            if (tele instanceof SpikeTrap spike){
                if(pFollow.isColliding(spike) && spike.isActivated()){
                    currentMap = tele.teleportToMap();
                    pFollow.teleportPlayer(tele.teleportPlayerX(), tele.teleportPlayerY());
                }
                spike.schedule();
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

            else if (interactionObj instanceof Ore){
                Ore oreObj = (Ore) interactionObj;
                if(oreObj.getHealth() <=0){
                    interactables.remove(interactionObj);
                }
            }

            else if (interactionObj instanceof ButtonItem){
                interactionObj.interact(pFollow);
            }

            else if (interactionObj instanceof SuperItem){
                if (pFollow.isColliding(interactionObj)){
                    interactionObj.interact(pFollow);
                }
                
            }
        }
    }

    public void recieveData(String data){ //gets data from the server
        Labyrinth labMap = (Labyrinth) maps[LABYRINTH];

        String[] serverMapData = data.split("\\|");
        String newVersion = "default";
        if (serverMapData.length == 2){
            newVersion = serverMapData[1];
        } 

        if(!newVersion.equals(labMap.getVersion())) labMap.loadNewMap(newVersion);
    }

    public Labyrinth getLabyrinth(){ //getter method for Labyrinth
        return (Labyrinth) maps[LABYRINTH];
    }

    public String getVersion(){ //getter method for version of the labyrinth map
        AssistOne assistOne = (AssistOne) maps[ASSIST1];
        return assistOne.getVersion();
    }
    
    public int[][] getColMap(){ //getter method for ColMap
        return maps[currentMap].getColMap();
    }

    public int getCurrentMap(){ //getter method for the current map
        return currentMap;
    }

    public int getMapWidth(){ //getter method for the map width
        Map cm = maps[currentMap];
        return cm.getWidth();
    }

    public int getMapHeight(){ //getter method for the map height
        Map cm = maps[currentMap];
        return cm.getHeight();
    }

    public boolean canDraw(int X, int Y){ //checks the dimensions of the map returns whether it's withing parameters
        return (X < pFollow.getWorldX() + bufferSize && X > pFollow.getWorldX() -  bufferSize &&
            Y < pFollow.getWorldY() + bufferSize && Y > pFollow.getWorldY() - bufferSize);
    }

    public ArrayList<Interactable> getInteractables() { //getter method for the interactables array list
        return maps[currentMap].getInteractables();
    }

    public ArrayList<NPC> getNPCs() { //getter method for the NPCs array list
        return maps[currentMap].getNPCs();
    }

    public NPC getNPC(String name){ //getter for a particlar NPC
        for (NPC npc : maps[currentMap].getNPCs()){
            if (name.equals(npc.getName())){
                return npc;
            }
        }

        return null;
    }

    public Hermes getHermes(){ //getter for hermes
        return hermes;
    }

    public BufferedImage[] getBaseTileset(){ //getter for BaseTileset
        return baseTiles;
    }
     public static void setDemeter_sacrifice(boolean demeter_sacrifice) { //setter to change whether demeter quest is done
        MapHandler.demeter_sacrifice = demeter_sacrifice;
    }

 }
