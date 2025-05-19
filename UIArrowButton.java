/**
    UIArrowButton Class implements UIButton. Draws the arrow button using assets.

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

public class UIArrowButton implements UIButton{
    private int x, y;
    private String direction;

    private int indexNum;

    private BufferedImage[] sprites;
    private boolean mousePressed,mouseOver;

    private UIPickSkin skinPicker;
    private Rectangle bounds;

    public UIArrowButton(int xPos, int yPos, String d, UIPickSkin ups){//constructor
        x = xPos;
        y = yPos;

        skinPicker = ups;

        direction = d.toUpperCase();
        
        mousePressed = false;
        mouseOver = false;
        
        sprites = new BufferedImage[2];
        indexNum = 0; // 0 = default, 1 = hover;

        try {
            int tileSize = GameFrame.PIXELRATIO;
            BufferedImage temp = ImageIO.read(new File(String.format("./res/uiAssets/ButtonAtlas.png"))); //loads the assests
            if (direction.equals("LEFT")){
                sprites[0] = temp.getSubimage(1*tileSize, 4*tileSize, 1*tileSize, 1*tileSize);
                sprites[1] = temp.getSubimage(1*tileSize, 5*tileSize, 1*tileSize, 1*tileSize);
            } else if (direction.equals("RIGHT")){
                sprites[0] = temp.getSubimage(0*tileSize, 4*tileSize, 1*tileSize, 1*tileSize);
                sprites[1] = temp.getSubimage(0*tileSize, 5*tileSize, 1*tileSize, 1*tileSize);
            }
            
        } catch (IOException ex) {
        }

        bounds = new Rectangle(x, y, 1 * GameFrame.SCALED, 1*GameFrame.SCALED);
    }

    @Override
    public void draw(Graphics2D g2d) { //draws the chosen assets
        g2d.drawImage(sprites[indexNum], x, y, 1 * GameFrame.SCALED, 1*GameFrame.SCALED, null);
    }

    @Override
    public void update() { //updates the assets based on mouse action
        indexNum = 0;

        if (mouseOver || mousePressed){
            indexNum = 1;
        }
    }

    @Override
    public boolean isMousePressed() { //getter for mousePressed
        return mousePressed;
    }

    @Override
    public void setMousePressed(boolean mousePressed) { //setter for mousePressed
        this.mousePressed = mousePressed;
    }

    @Override
    public boolean isMouseOver() { //getter for mouseOver
        return mouseOver;
    }

    @Override
    public void setMouseOver(boolean mouseOver) { //setter for mouseOver
        this.mouseOver = mouseOver;
    }

    @Override
    public Rectangle getBounds() { //getter for bounds
        return bounds;
    }

    @Override
    public void clicked() { //changes the skinPicker based on which button is clicked
        if (direction.equals("RIGHT")){
            skinPicker.up();
        } else if (direction.equals("LEFT")){
            skinPicker.down();
        }
    }

    @Override
    public void resetBools() { //reset for the mouse actions
        mouseOver = false;
        mousePressed = false;
    }

}