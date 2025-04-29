import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class UIPickSkin{
    private BufferedImage[] skins;
    private String[] skinName;
    private int indexNumber;

    private int x, y;

    public UIPickSkin(int xPos, int yPos){
        x = xPos;
        y = yPos;

        indexNumber = 0;
        skinName = new String[] {"Boy", "Hunter", "MaskFrog", "MaskGoldRacoon", "MaskRacoon", "Princess", "Villager", "Villager3", "Villager4", "Villager5", "Woman"};
        skins = new BufferedImage[skinName.length];

        int tileSize = 16;
        for (int i = 0; i < skinName.length; i++){
            try {
                BufferedImage temp = ImageIO.read(new File(String.format("./res/playerSkins/%s.png",skinName[i])));
                skins[i] = temp.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            } catch (IOException ex) {
                System.out.println(skinName[i]);
            }
        } 
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(skins[indexNumber], x, y, GameFrame.SCALED*5, GameFrame.SCALED*5, null);
    }

    public void up(){
        indexNumber++;
        if (indexNumber > skinName.length -1){
            indexNumber = 0;
        }
    }

    public void down(){
        indexNumber--;
        if (indexNumber < 0){
            indexNumber = skinName.length -1;
        }
    }

    public String getCurrentSkin() {
        return skinName[indexNumber];
    }
}