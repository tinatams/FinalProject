/**
    UITextBox Class implements UIButton. Draws the textbox and dialogue.

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
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class UITextBox implements UIButton {
    private int x, y;

    private BufferedImage containerIMG, highlightedIMG;
    private String contents;

    private Font regularFont;
    private boolean highlighted,mousePressed,mouseOver, selected;
    private Rectangle bounds;

    /**
        @param xPos xPosition of the textbox
        @param yPos yPosition of the textbox

        initializes other attributes
    **/
    public UITextBox(int xPos, int yPos){ //constructor

        x = xPos;
        y = yPos;

        contents = "";
        selected = false;
        
        highlighted = false;
        mousePressed = false;
        mouseOver = false;

        try {
            int tileSize = GameFrame.PIXELRATIO;
            BufferedImage temp = ImageIO.read(new File(String.format("./res/uiAssets/ButtonAtlas.png")));
            containerIMG = temp.getSubimage(3 * tileSize, 3 * tileSize, 7 * tileSize, tileSize);
            highlightedIMG = temp.getSubimage(3 * tileSize, 4 * tileSize, 7 * tileSize, tileSize);
            
            InputStream is = getClass().getResourceAsStream("./res/Fonts/dogicabold.ttf");
            regularFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException ex) {
        } catch (FontFormatException ex) {
        }

        bounds = new Rectangle(x, y, 7*GameFrame.SCALED, 1*GameFrame.SCALED);
    }

    /**
        @param g2d object used to draw
        draws the textbox and the text inside
    **/
    @Override
    public void draw(Graphics2D g2d) { //draws the text box and the text inside
        BufferedImage toDraw = containerIMG;
        if (highlighted){
            toDraw = highlightedIMG;
        }

        g2d.drawImage(toDraw, x, y, 7*GameFrame.SCALED, GameFrame.SCALED, null);

        g2d.setColor(new Color(255, 208, 158));
        g2d.setFont(regularFont.deriveFont(23f));
        g2d.drawString(contents, x + 4 * GameFrame.SCALER, y + 1 * GameFrame.SCALED - 4 * GameFrame.SCALER);
    }

    /**
      updates the assests based on mouse action
    **/
    @Override
    public void update(){ //updates the assets based on mouse action
        if (mouseOver || mousePressed || selected){
            highlighted = true;
        } else {
            highlighted = false;
        }
    }

    /**
        @return mousePressed
    **/
    @Override
    public boolean isMousePressed() { //getter for mousePressed
        return mousePressed;
    }

    /**
       setter for mousePressed
    **/
    @Override
    public void setMousePressed(boolean mousePressed) { //setter for mousePressed
        this.mousePressed = mousePressed;
    }

    /**
      @return mouseOver
    **/
    @Override
    public boolean isMouseOver() { //getter for mouseOver
        return mouseOver;
    }

    /**
      setter for mouse over
    **/
    @Override
    public void setMouseOver(boolean mouseOver) { //setter for mouseOver
        this.mouseOver = mouseOver;
    }

    /**
      @return the bounds dimensions
    **/
    @Override
    public Rectangle getBounds() { //getter for bounds
        return bounds;
    }

    /**
       action when textbox is filled, changes the status of the selected box. 
    **/
    @Override
    public void clicked() {//changes the status of selected
        selected = !selected;
    }

    /**
     * resets for the mouse actions
    **/
    @Override
    public void resetBools(){ //reset for the mouse actions
        mousePressed = false;
        mouseOver = false;
    }

    /**
      @return selected 
    **/
    public boolean isSelected() { //getter for selected
        return selected;
    }

    /**
      setter for selected
    **/
    public void setSelected(boolean selected) {
        this.selected = selected; //setter for selected
    }

    /**
     * based on the character typed by the player and types it into the textbox
    **/
    public void type(KeyEvent e) { //based on the character typed by the player and types it into the UI
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_BACK_SPACE){
            if (!contents.isBlank() && !contents.isEmpty()){
                contents = contents.substring(0, contents.length()-1);
            }
        } else if (Character.isLetter(e.getKeyChar()) || Character.isDigit(e.getKeyChar()) || Character.isWhitespace(e.getKeyChar()) || code == KeyEvent.VK_PERIOD){
            contents += String.valueOf(e.getKeyChar());
        }
    }

    /**
     * @return contents of the textbox
    **/
    public String getContents() { //getter for contents of text box
        return contents;
    }



}