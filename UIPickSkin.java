/**
    UIPickSkin Class handles the picking skin mechanic for players. It draws and shifts accordingly.

	@author Martina Amale M. Llamas (242648); Zoe Angeli G. Uy (246707)
	@version May 19, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.

    

**/


import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class UIPickSkin{
    private BufferedImage[] skins;
    private String[] skinName;
    private int indexNumber;

    private int x, y;

    public UIPickSkin(int xPos, int yPos){ //constructor with all available skins
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

    public void draw(Graphics2D g2d){ //draw the skin
        g2d.drawImage(skins[indexNumber], x, y, GameFrame.SCALED*5, GameFrame.SCALED*5, null);
    }

    public void up(){ //changes to the next skin to be drawn on the UI
        indexNumber++;
        if (indexNumber > skinName.length -1){
            indexNumber = 0;
        }
    }

    public void down(){ //changes to the previous skin to be drawn on the UI
        indexNumber--;
        if (indexNumber < 0){
            indexNumber = skinName.length -1;
        }
    }

    public String getCurrentSkin() { //getter for cuurent skin
        return skinName[indexNumber];
    }
}