/**
    UISendHermes Class implements UIButton. Draws hermes inventory

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

public class UISendHermes implements UIButton{
    private int x, y;
    private UIHandler ui;
    private int indexNum;

    private BufferedImage[] sprites;
    private SuperItem contents;
    private boolean mousePressed,mouseOver;
    private Rectangle bounds;

    public UISendHermes(int xPos, int yPos, UIHandler u){//constructor
        ui = u;

        x = xPos;
        y = yPos;
        
        mousePressed = false;
        mouseOver = false;
        
        sprites = new BufferedImage[3];
        indexNum = 0; // 0 = default, 1 = hover, 2 = pressed;

        try {
            int tileSize = GameFrame.PIXELRATIO;
            BufferedImage temp = ImageIO.read(new File(String.format("./res/uiAssets/ButtonAtlas.png")));
            sprites[0] = temp.getSubimage(0*tileSize, 0*tileSize, 3*tileSize, tileSize);
            sprites[1] = temp.getSubimage(3*tileSize, 0*tileSize, 3*tileSize, tileSize);
            sprites[2] = temp.getSubimage(6*tileSize, 0*tileSize, 3*tileSize, tileSize);
        } catch (IOException ex) {
        }

        bounds = new Rectangle(x, y, 2 * GameFrame.SCALED, GameFrame.SCALED);
    }

    @Override
    public void draw(Graphics2D g2d) { //draws assets depending on if the cell is selected
        g2d.drawImage(sprites[indexNum], x, y, 3 * GameFrame.SCALED, GameFrame.SCALED, null);
    }

    @Override
    public void update() {//updates based on mouse action
        indexNum = 0;

        if (mouseOver){
            indexNum = 1;
        }
        
        if (mousePressed){
            indexNum = 2;
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
    public void clicked() { //will send hermes to the other island
        Hermes hermes = (Hermes) ui.getMapHandler().getNPC(Hermes.name);
        if (hermes != null){
            hermes.send();
        }
    }

    @Override
    public void resetBools() {//resets booleans
        mouseOver = false;
        mousePressed = false;
    }
    
}