/**
    UIHandler Class ihandles all the UI elements to be drawn including buttons and assets.

	@author Martina Amale M. Llamas (242648); Zoe Angeli G. Uy (246707)
	@version May 19, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments c
	of my program.

    

**/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class UIHandler{
    private static final int PANEL_LEFT_X = 1 ;
    private static final int PANEL_Y = 1 ;
    private static final int PANEL_RIGHT_X = 11 ;

    public static Font regularFont;
    public static String currentDialog = "";
    private BufferedImage dialogueBox, blankHalfPanel;
    private BufferedImage gameTitle, inventoryHeader, questHeader, hermesHeader;
    private Player selectedPlayer;
    private MapHandler mapHandler;
    private UIMiniMap minMap;
    private FishMiniGame fishy;

    //UI COMPONENTS;
    private InventoryCellUI[] inventoryCellsPlayer, inventoryCellsHermes;
    private UISendHermes hermesSendButton;
    
    public UIHandler(GameFrame frame){ //constructor
        inventoryCellsHermes = new InventoryCellUI[70];
        inventoryCellsPlayer = new InventoryCellUI[70];
        
        selectedPlayer = frame.getSelected();
        mapHandler = frame.getMapHandler();

        minMap = new UIMiniMap(0, 0, frame);
        fishy = frame.getFishy();

        setUpUIComponents();
    }

    public void draw(Graphics2D g2d){ //draws according to current game state
        switch (GameFrame.gameState) {
            case GameFrame.DIALOG_STATE:
                drawDialogScreen(g2d);
                break;
            case GameFrame.INVENTORY_STATE:
                g2d.setColor(new Color(0,0,0,125));
                g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);
                drawInventory(g2d, PANEL_LEFT_X, PANEL_Y, selectedPlayer.getInventory(), inventoryCellsPlayer);
                drawQuestPanel(g2d,PANEL_RIGHT_X, PANEL_Y);
                
                g2d.drawImage(inventoryHeader, 2*GameFrame.SCALED - 6*GameFrame.SCALER, 2*GameFrame.SCALED -3*GameFrame.SCALER, 8*GameFrame.SCALED, 2*GameFrame.SCALED,null);
                g2d.drawImage(questHeader, 13*GameFrame.SCALED - GameFrame.SCALER, 2*GameFrame.SCALED -3*GameFrame.SCALER, 6*GameFrame.SCALED, 2*GameFrame.SCALED, null);   
                
                resetCells();
                break;
            case GameFrame.HERMES_STATE:
                g2d.setColor(new Color(0,0,0,125));
                g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);
                drawInventory(g2d, PANEL_LEFT_X, PANEL_Y, selectedPlayer.getInventory(), inventoryCellsPlayer);

                if (mapHandler.getNPC(Hermes.name) != null){
                    Hermes hermes = (Hermes) mapHandler.getNPC(Hermes.name);
                    
                    drawInventory(g2d, PANEL_RIGHT_X, PANEL_Y, hermes.getInventory(), inventoryCellsHermes);
                }

                g2d.drawImage(inventoryHeader, 2*GameFrame.SCALED - 6*GameFrame.SCALER, 2*GameFrame.SCALED -3*GameFrame.SCALER, 8*GameFrame.SCALED, 2*GameFrame.SCALED,null);
                //g2d.drawImage(hermesHeader, 13*GameFrame.SCALED - GameFrame.SCALER, 2*GameFrame.SCALED -3*GameFrame.SCALER, 6*GameFrame.SCALED, 2*GameFrame.SCALED, null);   

                hermesSendButton.draw(g2d);
                break;
            case GameFrame.FISHING_STATE:
                fishy.draw(g2d);
                break;
            default:
                if (selectedPlayer.getFrame().getMapHandler().getCurrentMap() == MapHandler.ASSIST1) minMap.draw(g2d);
                break;
        }
    }

    public void setUpUIComponents(){ //sets up assets (i.e. inventory and quest page)
        try {
            //Basic Components
            dialogueBox = ImageIO.read(new File("./res/uiAssets/BasicComponents/DialogueBoxSimple.png"));
            blankHalfPanel = ImageIO.read(new File("./res/uiAssets/BasicComponents/BlankTemplate.png"));

            //Text Components
            int tileSize = GameFrame.PIXELRATIO;
            BufferedImage temp = ImageIO.read(new File("./res/uiAssets/UITextAtlas.png"));
            gameTitle = temp.getSubimage(0, 0, 12*tileSize, 5*tileSize);
            inventoryHeader = temp.getSubimage(0, 5*tileSize, 8*tileSize, 2*tileSize); 
            questHeader = temp.getSubimage(0, 7*tileSize, 6*tileSize, 2*tileSize); 
            hermesHeader = temp.getSubimage(6*tileSize, 7*tileSize, 6*tileSize, 2*tileSize); 


            InputStream is = getClass().getResourceAsStream("./res/Fonts/dogicabold.ttf");
            regularFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException ex) {
        }catch (FontFormatException ex) {
        }

        setUpCells();
        hermesSendButton = new UISendHermes((PANEL_RIGHT_X+5)* GameFrame.SCALED, (PANEL_Y+1)*GameFrame.SCALED, this);
    }
    
    public void drawQuestPanel(Graphics2D g2d,int panelX, int panelY){ //draws quest panel
        try{
        BufferedImage temp = ImageIO.read(new File("./res/uiAssets/QuestBox.png"));
        g2d.drawImage(blankHalfPanel, panelX * GameFrame.SCALED, panelY * GameFrame.SCALED, 9 * GameFrame.SCALED, 14 * GameFrame.SCALED, null);
        }
        catch(IOException ex){
            
        }
    }
    
    public void setUpCells(){ //sets up needed inventory cells
        int x = 1;
        int y = 3;
        for (int i = 0; i < 70; i++){
            inventoryCellsPlayer[i] = new InventoryCellUI(x*GameFrame.SCALED+PANEL_LEFT_X* GameFrame.SCALED, y*GameFrame.SCALED + PANEL_Y* GameFrame.SCALED, this, "Player");
            inventoryCellsHermes[i] = new InventoryCellUI(x*GameFrame.SCALED+PANEL_RIGHT_X* GameFrame.SCALED, y*GameFrame.SCALED + PANEL_Y* GameFrame.SCALED, this, "Hermes");
            
            x++;
            if (x >= 8){
                x = 1;
                y++;
            }
        }
    }
    
    public void drawInventory(Graphics2D g2d, int panelX, int panelY, ArrayList<SuperItem> inventory, InventoryCellUI[] cells){ //draws inventory cells and GUI design
        g2d.drawImage(blankHalfPanel, panelX * GameFrame.SCALED, panelY * GameFrame.SCALED, 9 * GameFrame.SCALED, 14 * GameFrame.SCALED, null);

        int x = 1;
        int y = 3;
        for (int i = 0; i < 70; i++){
            InventoryCellUI ice = cells[i];
            if(!inventory.isEmpty() && inventory.size() > i){
                SuperItem item = inventory.get(i);
                ice.setContents(item);
            }
            
            ice.draw(g2d);
            x++;
            if (x >= 8){
                x = 1;
                y++;
            }
        }
    }

    public void drawDialogScreen(Graphics2D g2d){ //draws dialog screen
        int x=16*2;
        int y=30;
        int width= GameFrame.WIDTH-90;
        int height=GameFrame.HEIGHT/3;
        

        g2d.drawImage(dialogueBox, x, y, width, height, null);
        
        x+=GameFrame.SCALED;
        y+=GameFrame.SCALED;

        g2d.setFont(regularFont.deriveFont(20f));
        //g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,24F));

        for (String line: currentDialog.split("~")){
            g2d.drawString(line,x,y);
            y+=40;
        }
    }

    public void update(){ //updates inventory cells accordingly when hermes is used
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            for (int i = 0; i < inventoryCellsHermes.length ; i++){
                inventoryCellsHermes[i].update();
                inventoryCellsPlayer[i].update();
            }
            hermesSendButton.update();
        }
    }

    public Player getSelectedPlayer() { //getter for selected player
        return selectedPlayer;
    }

    public MapHandler getMapHandler() { //getter for map handler
        return mapHandler;
    }

    public void mousePressed(MouseEvent e){ //adjusts hermes based on what item or button is clicked
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            for (int i = 0; i < 70; i++){
                if ( isIn(e, inventoryCellsHermes[i])){
                    inventoryCellsHermes[i].setMousePressed(true);
                    break;
                } 

                if ( isIn(e, inventoryCellsPlayer[i])){
                    inventoryCellsPlayer[i].setMousePressed(true);
                    break;
                }
            }

            if (isIn(e, hermesSendButton)){
                hermesSendButton.setMousePressed(true);
            }
        }

    }

    public void mouseReleased(MouseEvent e){ //adjusts hermes based on what item or button is clicked and plays sound effects
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            for (int i = 0; i < 70; i++){
                if ( isIn(e, inventoryCellsHermes[i])){
                    if(inventoryCellsHermes[i].isMousePressed()){
                        inventoryCellsHermes[i].clicked();
                        selectedPlayer.getFrame().getSoundHandler().playEffect(SoundHandler.BUTTON);
                    }
                    break;
                } 

                if ( isIn(e, inventoryCellsPlayer[i])){
                    if(inventoryCellsPlayer[i].isMousePressed()){
                        inventoryCellsPlayer[i].clicked();
                        selectedPlayer.getFrame().getSoundHandler().playEffect(SoundHandler.BUTTON);
                    }
                    break;
                }
            }

            if (isIn(e, hermesSendButton)){
                if(hermesSendButton.isMousePressed()){
                    hermesSendButton.clicked();
                    selectedPlayer.getFrame().getSoundHandler().playEffect(SoundHandler.START_GAME);
                }
                
            }
            resetButtons();
        }

    }

    public void mouseMoved(MouseEvent e){ //adjusrs the status of the cells based on where the mouse is
        resetButtons();
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            for (int i = 0; i < 70; i++){
                inventoryCellsPlayer[i].setMouseOver(false);
                inventoryCellsHermes[i].setMouseOver(false);
            }

            for (int i = 0; i < 70; i++){
                if ( isIn(e, inventoryCellsHermes[i])){
                    inventoryCellsHermes[i].setMouseOver(true);
                    break;
                } 

                if ( isIn(e, inventoryCellsPlayer[i])){
                    inventoryCellsPlayer[i].setMouseOver(true);
                    break;
                }
            }

            if (isIn(e, hermesSendButton)){
                hermesSendButton.setMouseOver(true);
            }
        }
    }

    private boolean isIn(MouseEvent e, UIButton button){ //checks if the mouse is in the bounds of the button
        return button.getBounds().contains(e.getX(), e.getY());
    }

    private void resetButtons() { //resets all buttons
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            for (int i = 0; i < 70; i++){
                inventoryCellsPlayer[i].resetBools();
                inventoryCellsHermes[i].resetBools();
            }

            resetCells();
            hermesSendButton.resetBools();
        }
    }

    private void resetCells(){ //empties inventory cells for player and hermes
        for (int i = 0; i < 70; i++){
            inventoryCellsPlayer[i].setContents(null);
            inventoryCellsHermes[i].setContents(null);
        }
    }


}