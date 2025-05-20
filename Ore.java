/**
    Ore Class that implements Interactable. It allows Interactions so that the iron can be picked up (hitbox and interaction box) based on 
    the interactable item's health.
 
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

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Ore implements Interactable{
    public static String ITEMNAME = "ORE";
    
    private int worldX, worldY, spriteW, spriteH;
    private String name;
    private BufferedImage treeSprite;
    private int health;

    private Rectangle hitBox, interactionBox;

    /**
       @param int x, int y Constructor where the entity is drawn and it's health bar
    **/
    public Ore(int x, int y){ 
        spriteW = GameFrame.SCALED;
        spriteH = GameFrame.SCALED;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        health = 7;

        hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2,worldY - GameFrame.SCALED/2, spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);

        loadImage();
    }

    /**
        reads what png needs to be drawn
    **/
    public void loadImage(){ 
        try {
            BufferedImage tileSheet = ImageIO.read(new File("./res/tileSets/TileSetDeco.png")); 
            int tileSize = 16;

            treeSprite = tileSheet.getSubimage(16 * tileSize, 1 * tileSize, spriteW/GameFrame.SCALED * tileSize, spriteH/GameFrame.SCALED * tileSize);
        } catch (IOException ex) {
            
        }
    }

    /**
        @param Graphics2D g2d calls the draw method for interactable
    **/
    @Override
    public void draw(Graphics2D g2d){ 
        g2d.drawImage(treeSprite, worldX, worldY, spriteW, spriteH, null);
    }

    /**
        @param Player player calls the collect method whenever health is zero and sound effects
    **/
    @Override
    public void interact(Player player) { 
        if (player.getItem(PickaxeItem.ITEMNAME) != null){
            health--;
            SoundHandler sh = player.getFrame().getSoundHandler();
            if (health == 0){
                player.collect(new IronItem(0,0));
                sh.playEffect(SoundHandler.STONE_CRACK);
            } else {
                sh.playEffect(SoundHandler.HIT_ROCK);
            }
        }
        
    }
    
    /**
        @return int returns where the item is (x-coordinates)
    **/
    @Override
    public int getWorldX() { //
        return worldX;
    }

    /**
       @return int  returns where the item is (y-coordinates)
    **/
    @Override
    public int getWorldY() { 
        return worldY;
    }

    /**
        @return int returns the width of the item
    **/
    @Override
    public int getSpriteW() { 
        return spriteW;
    }

    /**
        @return int returns the height of the item
    **/
    @Override
    public int getSpriteH() { 
        return spriteH;
    }

    /**
       @return Rectangle returns the hitbox dimensions
    **/
    @Override
    public Rectangle getHitBox() { 
        return hitBox;
    }


    /**
       @return Rectangle returns the interaction box dimensions
    **/
    @Override
    public Rectangle getInteractionBox() { 
        return interactionBox;
    }

    /**
       @return int gets health
    **/
    public int getHealth() {
        return health;
    }

}