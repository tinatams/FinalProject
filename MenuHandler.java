/**
    MenuHandler Class extends JComponent and handles the starting menu. It also handles the buttons and animations according to
    mouse movements and gets IP address and port number from the player.
 
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
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class MenuHandler extends JComponent{
    public GameStarter gameStarter;
    private BufferedImage fullPanel, fullPanelInterior, gameTitle, connectHeader, skinHeader, portText, ipText, invalidMessage, backgroundImage, playerBkg;

    private boolean validInputs;

    private UIStartButton startButton, startGameButton;
    private UITextBox portBox, ipBox;
    private UIArrowButton arrowRight, arrowLeft;
    private UIPickSkin skinPicker;

    private UIButton[] chooseStateButtons;
    private SoundHandler soundHandler;

    /**
    Constructor per client
    **/
    public MenuHandler(GameStarter gs, SoundHandler s){
        gameStarter = gs;
        soundHandler = s;
        setUpAssets();

        validInputs = true;
    }
    
    /**
    paint fuction that draws the needed grpahics for the start menu
    **/
    @Override 
    protected void paintComponent(Graphics g){ 
        Graphics2D g2d = (Graphics2D) g;

        if (GameMenu.STATE == GameMenu.CHOOSING){
            g2d.drawImage(fullPanel, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
            g2d.drawImage(fullPanelInterior, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);

            //IP SIDE
            g2d.drawImage(connectHeader, 11*GameFrame.SCALED + 6*GameFrame.SCALER, 2*GameFrame.SCALED - 3*GameFrame.SCALER, 9 * GameFrame.SCALED, 3 * GameFrame.SCALED, null);
            g2d.drawImage(ipText, 12*GameFrame.SCALED, 5*GameFrame.SCALED, 1*GameFrame.SCALED, 1*GameFrame.SCALED, null);
            g2d.drawImage(portText, 12*GameFrame.SCALED, 8*GameFrame.SCALED, 2*GameFrame.SCALED, 1*GameFrame.SCALED, null);

            //CHOOSE SKIN SIDE
            g2d.drawImage(skinHeader, 1*GameFrame.SCALED + 10*GameFrame.SCALER, 2*GameFrame.SCALED - 3*GameFrame.SCALER, 8 * GameFrame.SCALED, 3 * GameFrame.SCALED, null);
            g2d.drawImage(playerBkg, 2*GameFrame.SCALED, 6*GameFrame.SCALED, 7 * GameFrame.SCALED, 7 * GameFrame.SCALED, null);
            skinPicker.draw(g2d);

            for(UIButton button : chooseStateButtons){
                button.draw(g2d);
            }

            if (!validInputs){
                g2d.drawImage(invalidMessage, 12*GameFrame.SCALED, 11*GameFrame.SCALED, 8*GameFrame.SCALED, 1*GameFrame.SCALED, null);
            }

            startGameButton.draw(g2d);
        } else {
            g2d.drawImage(backgroundImage, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
            g2d.setColor(new Color(0,0,0,90));
            g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);

            g2d.drawImage(gameTitle, 5 * GameFrame.SCALED, 3*GameFrame.SCALED, 12 * GameFrame.SCALED, 5 * GameFrame.SCALED, null);
            startButton.draw(g2d);
        }

    }

    /**
    update method based on what buttons are pressed
    **/
    public void update(){ 
        startButton.update();
        for(UIButton button :chooseStateButtons){
            button.update();
        }
    }

    /**
    sets up all the needed aseets(images) into their variables
    **/
    public void setUpAssets(){ 
        chooseStateButtons = new UIButton[5];
        try {
            backgroundImage = ImageIO.read(new File("./res/uiAssets/Background.png"));
            fullPanel = ImageIO.read(new File("./res/uiAssets/BasicComponents/FullPanel.png"));
            fullPanelInterior = ImageIO.read(new File("./res/uiAssets/BasicComponents/FullPanelInterior.png"));
            playerBkg = ImageIO.read(new File("./res/uiAssets/SelectSkinBkg.png"));

            int tileSize = GameFrame.PIXELRATIO;
            BufferedImage temp = ImageIO.read(new File("./res/uiAssets/UITextAtlas.png"));

            gameTitle = temp.getSubimage(0, 0, 12*tileSize, 5*tileSize);
            connectHeader = temp.getSubimage(0*tileSize, 9*tileSize, 9*tileSize, 3*tileSize);
            skinHeader = temp.getSubimage(0*tileSize, 12*tileSize, 8*tileSize, 3*tileSize);
            portText = temp.getSubimage(12*tileSize, 0*tileSize, 2*tileSize, 1*tileSize);
            ipText = temp.getSubimage(12*tileSize, 1*tileSize, 1*tileSize, 1*tileSize);
            invalidMessage = temp.getSubimage(12*tileSize, 2*tileSize, 8*tileSize, 1*tileSize);
        } catch (IOException ex) {

        }

        startButton = new UIStartButton(8*GameFrame.SCALED, 9*GameFrame.SCALED, gameStarter, this);

        startGameButton = new UIStartButton(13*GameFrame.SCALED, 12*GameFrame.SCALED, gameStarter, this);
        portBox = new UITextBox(12*GameFrame.SCALED, 9*GameFrame.SCALED);
        ipBox = new UITextBox(12*GameFrame.SCALED, 6*GameFrame.SCALED);
        skinPicker = new UIPickSkin(3*GameFrame.SCALED, 7*GameFrame.SCALED);
        arrowRight = new UIArrowButton(1*GameFrame.SCALED, 9*GameFrame.SCALED, "RIGHT", skinPicker);
        arrowLeft = new UIArrowButton(9*GameFrame.SCALED, 9*GameFrame.SCALED, "LEFT", skinPicker);

        chooseStateButtons[0] = startGameButton;
        chooseStateButtons[1] = portBox;
        chooseStateButtons[2] = ipBox;
        chooseStateButtons[3] = arrowRight;
        chooseStateButtons[4] = arrowLeft;
    }

    
    /**
    listener when a mouse is pressed for the buttons
    **/
    public void mousePressed(MouseEvent e){ 
        if (GameMenu.STATE == GameMenu.CHOOSING){
            for (UIButton button : chooseStateButtons){
                if (isIn(e, button)){
                    button.setMousePressed(true);
                    break;
                }
            }
        
        } else {
            if (isIn(e, startButton)){
                startButton.setMousePressed(true);
            }
        }
    }

    /**
    listener when a mouse is released to check which box is selected
    **/
    public void mouseReleased(MouseEvent e){
        portBox.setSelected(false);
        ipBox.setSelected(false);
        if (GameMenu.STATE == GameMenu.CHOOSING){
            for (UIButton button : chooseStateButtons){
                if (isIn(e, button) && button.isMousePressed()){
                    button.clicked();
                    if (!(button instanceof UIStartButton)){
                        soundHandler.playEffect(SoundHandler.BUTTON);
                    } else{
                        if (gameStarter.isConnected())soundHandler.playEffect(SoundHandler.START_GAME);
                        else soundHandler.playEffect(SoundHandler.BUTTON);
                    }
                    break;
                }
            }
        } else {
            if (isIn(e, startButton) && startButton.isMousePressed()){
                startButton.clicked();
                soundHandler.playEffect(SoundHandler.BUTTON);
            }

        }

        resetButtons();
    }

    /**
    checks if the mouse is over a button
    **/
    public void mouseMoved(MouseEvent e){ 
        resetButtons();
        if (GameMenu.STATE == GameMenu.CHOOSING){
            for (UIButton button : chooseStateButtons){
                if (isIn(e, button)){
                    button.setMouseOver(true);
                    break;
                }
            }

        } else {
            if (isIn(e, startButton)){
                startButton.setMouseOver(true);
            }
        }
    }

    /**
    when a key is typed it appears in the text box
    **/
    public void keyTyped(KeyEvent e){ 
        if (GameMenu.STATE == GameMenu.CHOOSING){
            if (ipBox.isSelected()){
                ipBox.type(e);
            } else if (portBox.isSelected()){
                portBox.type(e);
            }
        } else {
            
        }
    }

    /**
    checks if the mouse is in the area to trigger the button
    **/
    private boolean isIn(MouseEvent e, UIButton button){ //
        return button.getBounds().contains(e.getX(), e.getY());
    }

    /**
    resets buttons to not be pressed
    **/
    private void resetButtons(){
        startButton.resetBools();

        for(UIButton button : chooseStateButtons){
            button.resetBools();
        }
    }

    /**
    getter for port number
    **/
    public String getPortNumber(){ 
        return portBox.getContents();
    }

    /**
    getter for IP address
    **/
    public String getIpAddress(){ 
        return ipBox.getContents();
    }

    /**
    starts animation thread
    **/
    public void startAnimation(){ 
        Animation a = new Animation();
        a.start();
    }

    /**
    setter for validinputs
    **/
    public void setValidInputs(boolean validInputs) {
        this.validInputs = validInputs;
    }

    /**
    getter for skin
    **/
    public String getSkin() { 
        return skinPicker.getCurrentSkin();
    }

    /**
    inner class for thread
    **/
    private class Animation extends Thread { //

        public void Animation(){

        }
        /**
        class to update and repaint the frame 
        **/
        public void run(){
            while(true){
                update();
                repaint();
                
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e){}
            }
        }
    }
}