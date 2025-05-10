import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class UIMiniMap {
    private int x, y;
    private Player playerToDraw;
    private GameFrame frame;

    private Map mapToDraw;
    private BufferedImage minMapBox;

    private BufferedImage[] baseTiles;

    public UIMiniMap(int x, int y, GameFrame frame){
        this.x = x;
        this.y = y;
        this.frame = frame;

        mapToDraw = frame.getMapHandler().getLabyrinth();
        baseTiles = frame.getMapHandler().getBaseTileset();

        try {
            minMapBox = ImageIO.read(new File("./res/uiAssets/minmapBox.png"));
        } catch (IOException ex) {
        }
    }   

    public void draw(Graphics2D g2d){
        AffineTransform reset = g2d.getTransform();
        g2d.translate(x, y);
        
        ArrayList<Player> players = new ArrayList<Player>();
        String[] serverPlayersData = frame.getCanvas().getData().split("\\|");
        for (int i = 0; i < serverPlayersData.length; i++){
            String playerData = serverPlayersData[i];
            
            String[] data = playerData.split(",");
            if (data.length == 7){
                if (Integer.parseInt(data[0]) != frame.getClientNumber()){
                    int otherMap = Integer.parseInt(data[6]);
                    if(otherMap == MapHandler.LABYRINTH){
                        int x = Integer.parseInt(data[1]);
                        int y = Integer.parseInt(data[2]);
                        String s = data[3];
                        int direc = Integer.parseInt(data[4]);
                        int version = Integer.parseInt(data[5]);
                        int cliNum = Integer.parseInt(data[0]);
                        Player temp = new Player(s,x,y,cliNum);
                        temp.setOther(direc, version);
                        players.add(temp);
                    }
                }
            }  
        }
        g2d.drawImage(minMapBox,0,0,5*(mapToDraw.mapWidth+4), 5*(mapToDraw.mapHeight+4),null);
        g2d.translate(10, 11);
        
        int[][] tilesToDraw = mapToDraw.getBaseMap();
        int worldCol = 0, worldRow = 1;
        int tileSize = 5;
        while (worldCol < Map.maxColumn && worldRow < Map.maxRow){
            int worldX = worldCol * tileSize, worldY = worldRow * tileSize;
            if (tilesToDraw[worldCol][worldRow] != -1)
            g2d.drawImage(baseTiles[tilesToDraw[worldCol][worldRow]], worldX, worldY, tileSize, tileSize, null);

            worldCol ++;
            if (worldCol == mapToDraw.mapWidth ){
                worldCol = 0;
                worldRow ++;
            }

            if (worldRow >= mapToDraw.mapHeight-1){
                break;
            }
        }

        if (players != null && players.size() > 0){
            for(Player player : players){
                int playerX = player.getWorldX()/GameFrame.SCALED * tileSize;
                int playerY = player.getWorldY()/GameFrame.SCALED * tileSize;
                player.drawSpecific(g2d, playerX, playerY, tileSize*2, tileSize*2);
            }
        }

        g2d.setTransform(reset);
    }

}