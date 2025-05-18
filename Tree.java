/**
    Tree Class that implements Interactable. It allows Interactions so that the wood can be picked up (hitbox and interaction box) based on 
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

public class Tree implements Interactable{
    public static String ITEMNAME = "TREE";
    private int worldX, worldY, spriteW, spriteH;
    private String name;
    private BufferedImage treeSprite;
    private int health;

    private Rectangle hitBox, interactionBox;

    public Tree(int x, int y){ //Constructor where the entity is drawn and it's health bar
        spriteW = GameFrame.SCALED * 2;
        spriteH = GameFrame.SCALED * 2;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        health = 5;

        hitBox = new Rectangle(worldX +20,worldY+10,spriteW - 40, spriteH -15);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2,worldY - GameFrame.SCALED/2, spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);

        loadImage();
    }

    public void loadImage(){ //reads what png needs to be drawn
        try {
            BufferedImage tileSheet = ImageIO.read(new File("./res/tileSets/TileSetDeco.png")); 
            int tileSize = 16;

            treeSprite = tileSheet.getSubimage(0 * tileSize, 6 * tileSize, spriteW/GameFrame.SCALED * tileSize, spriteH/GameFrame.SCALED * tileSize);
        } catch (IOException ex) {
            
        }
    }

    @Override
    public void draw(Graphics2D g2d){ //calls the draw method for interactable
        g2d.drawImage(treeSprite, worldX, worldY, spriteW, spriteH, null);
    }

    @Override
    public void interact(Player player) { //calls the collect method whenever health is zero and sound effects
        if (player.getItem(AxeItem.ITEMNAME) != null){
            health--;
            SoundHandler sh = player.getFrame().getSoundHandler();
            if (health == 0){
                player.collect(new WoodItem(0,0));
                sh.playEffect(SoundHandler.TREE_BREAK);
            } else {
                sh.playEffect(SoundHandler.WOOD_CHOP);
            }
        }
    }

    @Override
    public int getWorldX() { //returns where the item is (x-coordinates)
        return worldX;
    }

    @Override
    public int getWorldY() { //returns where the item is (y-coordinates)
        return worldY;
    }

    @Override
    public int getSpriteW() { //returns the width of the item
        return spriteW;
    }

    @Override
    public int getSpriteH() { //returns the height of the item
        return spriteH;
    }

    @Override
    public Rectangle getHitBox() { //returns the hitbox dimensions
        return hitBox;
    }


    @Override
    public Rectangle getInteractionBox() { //returns the interaction box dimensions
        return interactionBox;
    }


    public void setHitBox(Rectangle hitBox) {//setter for the hitbox
        this.hitBox = hitBox;
    }

    public void setInteractionBox(Rectangle interactionBox) {//setter for the interactionBox
        this.interactionBox = interactionBox;
    }

    public int getHealth() {//gets health
        return health;
    }
}