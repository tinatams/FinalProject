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

    //UI COMPONENTS;
    private InventoryCellUI[] inventoryCellsPlayer, inventoryCellsHermes;
    private UISendHermes hermesSendButton;
    
    public UIHandler(GameFrame frame){
        inventoryCellsHermes = new InventoryCellUI[70];
        inventoryCellsPlayer = new InventoryCellUI[70];
        
        selectedPlayer = frame.getSelected();
        mapHandler = frame.getMapHandler();

        minMap = new UIMiniMap(0, 0, frame);

        setUpUIComponents();
    }

    public void draw(Graphics2D g2d){
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
            default:
                break;
        }
    }

    public void setUpUIComponents(){
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
    
    public void drawQuestPanel(Graphics2D g2d,int panelX, int panelY){
        g2d.drawImage(blankHalfPanel, panelX * GameFrame.SCALED, panelY * GameFrame.SCALED, 9 * GameFrame.SCALED, 14 * GameFrame.SCALED, null);
    }
    
    public void setUpCells(){
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
    
    public void drawInventory(Graphics2D g2d, int panelX, int panelY, ArrayList<SuperItem> inventory, InventoryCellUI[] cells){
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

    public void drawDialogScreen(Graphics2D g2d){
        int x=16*2;
        int y=30;
        int width= GameFrame.WIDTH-90;
        int height=GameFrame.HEIGHT/3;
        
        // other way to do it
        // Color c=new Color(0,0,0,200);
        // g2d.setColor(c);
        // g2d.fillRoundRect(x,y,width,height,35,35);

        // c=new Color(250,250,250);
        // g2d.setColor(c);
        // g2d.setStroke(new BasicStroke(5));
        // g2d.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

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

    public void update(){
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            for (int i = 0; i < inventoryCellsHermes.length ; i++){
                inventoryCellsHermes[i].update();
                inventoryCellsPlayer[i].update();
            }
            hermesSendButton.update();
        }
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public void mousePressed(MouseEvent e){
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

    public void mouseReleased(MouseEvent e){
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

    public void mouseMoved(MouseEvent e){
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

    private boolean isIn(MouseEvent e, UIButton button){
        return button.getBounds().contains(e.getX(), e.getY());
    }

    private void resetButtons() {
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            for (int i = 0; i < 70; i++){
                inventoryCellsPlayer[i].resetBools();
                inventoryCellsHermes[i].resetBools();
            }

            resetCells();
            hermesSendButton.resetBools();
        }
    }

    private void resetCells(){
        for (int i = 0; i < 70; i++){
            inventoryCellsPlayer[i].setContents(null);
            inventoryCellsHermes[i].setContents(null);
        }
    }


}