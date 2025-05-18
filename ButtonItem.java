/**
    This class creates a Button item that the player can interact with.
    It implements interactable interface. Has a variable that keeps track 
    of if the button is pressed

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

public class ButtonItem implements Interactable{
    private int worldX, worldY, spriteW, spriteH;
    private String name;
    private BufferedImage pressed, notPressed;

    private boolean press;
    private Rectangle hitBox;

    /**
        Constructor that initiates the x and y position of the ButtonItem. 
        It also instansiates/ sets the default values of each field. 

     	@param x = x position to be assigned to worldX
     	@param y = y position to be assigned to worldY
     	spriteW = 1 tile wide
        spriteH = 1 tile tall
        press = false [button isn't pressed]
    **/
    public ButtonItem(int x, int y){
        spriteW = GameFrame.SCALED;
        spriteH = GameFrame.SCALED;
        
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;

        press = false;

        hitBox = new Rectangle(worldX ,worldY ,spriteH, spriteW);

        loadImage();
    }

    /**
        Method loads the sprites of the object.
        Gets the tilesheet from the filepath ./res/tileSets/TilesetDungeon.png

        From this tileset it gets the subimage that correspond to the sprite that is pressed
        and the one that isnt

        pressed: col 1, row 1
        not pressed: col 2, row 1
    **/
    public void loadImage(){
        try {
            BufferedImage buttonSprite = ImageIO.read(new File("./res/tileSets/TilesetDungeon.png")); 
            int tileSize = 16;

            pressed = buttonSprite.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            notPressed = buttonSprite.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
        } catch (IOException ex) {
            
        }
    }

    /**
        Method draws the button item
        Determines which sprite to draw depending on the pressed boolean variable.
    **/
    public void draw(Graphics2D g2d){
        BufferedImage toDraw = press ? notPressed : pressed;
        g2d.drawImage(toDraw, worldX, worldY, spriteW, spriteH, null);
    }
    
    /**
        Method allows ButtonItem to interact with Player class
        if player is colliding with button item and interacts with the button
    **/
    @Override
    public void interact(Player player){
        if (player.isColliding(this)){
            press = true;
        } else{
            press = false;
        }
    }

    /**
        Gets the button's X coordinate
        @return worldX
    **/
    @Override
    public int getWorldX() {
        return worldX;
    }

    /**
        Gets the button's Y coordinate
        @return worldY
    **/
    @Override
    public int getWorldY() {
        return worldY;
    }

    /**
        Gets the button's width
        @return spriteW
    **/
    @Override
    public int getSpriteW() {
        return spriteW;
    }

    /**
        Gets the button's height
        @return spriteH
    **/
    @Override
    public int getSpriteH() {
        return spriteH;
    }
    
    /**
        Gets the button's hitBox
        @return hitBox
    **/
    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
        Gets the button's interactionBox
        @return hitbox
    **/
    @Override
    public Rectangle getInteractionBox() {
        return hitBox;
    }

    /**
        Gets if the button is pressed
        @return press
    **/
    public boolean isPressed(){
        return press;
    }
}