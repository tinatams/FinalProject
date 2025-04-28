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
    private BufferedImage dialogueBox, blankHalfPanel, backgroundImage;
    private Player selectedPlayer;
    private MapHandler mapHandler;

    //UI COMPONENTS;
    private InventoryCellUI[] inventoryCellsPlayer, inventoryCellsHermes;
    private UISendHermes hermesSendButton;
    
    public UIHandler(Player sPlayer, MapHandler mHandler){
        inventoryCellsHermes = new InventoryCellUI[70];
        inventoryCellsPlayer = new InventoryCellUI[70];
        
        selectedPlayer = sPlayer;
        mapHandler = mHandler;

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

                hermesSendButton.draw(g2d);
                break;
            case GameFrame.START_STATE:
                    g2d.drawImage(backgroundImage,(int) -9*GameFrame.SCALED + GameFrame.SCALED/4, -GameFrame.SCALED, 36 * GameFrame.SCALED, (int) 20.25 * GameFrame.SCALED, null);
                    g2d.setColor(new Color(0,0,0,90));
                    g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);
                break;
            default:
                break;
        }
    }

    public void setUpUIComponents(){
        try {
            dialogueBox = ImageIO.read(new File("./res/uiAssets/BasicComponents/DialogueBoxSimple.png"));
            blankHalfPanel = ImageIO.read(new File("./res/uiAssets/BasicComponents/BlankTemplate.png"));
            backgroundImage = ImageIO.read(new File("./res/uiAssets/Background.png"));
            InputStream is = getClass().getResourceAsStream("./res/Fonts/dogicabold.ttf");
            regularFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException ex) {
        }catch (FontFormatException ex) {
        }

        setUpCells();

        hermesSendButton = new UISendHermes((PANEL_RIGHT_X+6)* GameFrame.SCALED, (PANEL_Y+1)*GameFrame.SCALED, this);
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

        for (String line: currentDialog.split("#")){
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

    public void mouseClicked(MouseEvent e){

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
                    }
                    break;
                } 

                if ( isIn(e, inventoryCellsPlayer[i])){
                    if(inventoryCellsPlayer[i].isMousePressed()){
                        inventoryCellsPlayer[i].clicked();
                    }
                    break;
                }
            }

            if (isIn(e, hermesSendButton)){
                if(hermesSendButton.isMousePressed()){
                    hermesSendButton.clicked();
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

    public boolean isIn(MouseEvent e, UIButton button){
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