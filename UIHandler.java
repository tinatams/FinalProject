import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class UIHandler{
    private static final int PANEL_LEFT_X = 1 ;
    private static final int  PANEL_LEFT_Y = 1 ;
    private static final int  PANEL_RIGHT_X = 11 ;
    private static final int  PANEL_RIGHT_Y = 1 ;

    public static Font regularFont;
    public static String currentDialog = "";
    private BufferedImage dialogueBox, inventoryTemplate, blankHalfPanel;
    private Player selectedPlayer;
    private MapHandler mapHandler;

    private InventoryCellUI[] inventoryCellsPlayer, inventoryCellsHermes;
    
    public UIHandler(Player sPlayer, MapHandler mHandler){
        inventoryCellsHermes = new InventoryCellUI[70];
        inventoryCellsPlayer = new InventoryCellUI[70];
        setUpUIComponents();
        selectedPlayer = sPlayer;
        mapHandler = mHandler;
        setUpCells();
    }

    public void draw(Graphics2D g2d){
        switch (GameFrame.gameState) {
            case GameFrame.DIALOG_STATE:
                drawDialogScreen(g2d);
                break;
            case GameFrame.INVENTORY_STATE:
                g2d.setColor(new Color(0,0,0,125));
                g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);
                drawInventory(g2d, PANEL_LEFT_X, PANEL_LEFT_Y, selectedPlayer.getInventory(), inventoryCellsPlayer);
                drawQuestPanel(g2d,PANEL_RIGHT_X,PANEL_RIGHT_Y);
                break;
            case GameFrame.HERMES_STATE:
                g2d.setColor(new Color(0,0,0,125));
                g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);
                drawInventory(g2d, PANEL_LEFT_X, PANEL_LEFT_Y, selectedPlayer.getInventory(), inventoryCellsPlayer);
                if (mapHandler.getNPC("Hermes") != null){
                    Hermes hermes = (Hermes) mapHandler.getNPC("Hermes");
                    
                    drawInventory(g2d, PANEL_RIGHT_X,PANEL_RIGHT_Y, hermes.getInventory(), inventoryCellsHermes);
                }   break;
            default:
                break;
        }
    }

    public void setUpUIComponents(){
        try {
            dialogueBox = ImageIO.read(new File("./res/uiAssets/DialogueBoxSimple.png"));
            inventoryTemplate = ImageIO.read(new File("./res/uiAssets/InventoryTemplate.png"));
            blankHalfPanel = ImageIO.read(new File("./res/uiAssets/BlankTemplate.png"));
            InputStream is = getClass().getResourceAsStream("./res/Fonts/VT323-Regular.ttf");
            regularFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException ex) {
        }catch (FontFormatException ex) {
        }
    }
    
    public void drawQuestPanel(Graphics2D g2d,int panelX, int panelY){
        g2d.drawImage(blankHalfPanel, panelX * GameFrame.SCALED, panelY * GameFrame.SCALED, 9 * GameFrame.SCALED, 14 * GameFrame.SCALED, null);
    }
    
    public void setUpCells(){
        int x = 1;
        int y = 3;
        for (int i = 0; i < 70; i++){
            inventoryCellsPlayer[i] = new InventoryCellUI(x*GameFrame.SCALED+PANEL_LEFT_X* GameFrame.SCALED, y*GameFrame.SCALED + PANEL_LEFT_Y* GameFrame.SCALED, this, "Player");
            inventoryCellsHermes[i] = new InventoryCellUI(x*GameFrame.SCALED+PANEL_RIGHT_X* GameFrame.SCALED, y*GameFrame.SCALED + PANEL_RIGHT_Y* GameFrame.SCALED, this, "Hermes");
            
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

        g2d.setFont(regularFont.deriveFont(35f)); // font looks a bit weird
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

                if ( isIn(e, inventoryCellsHermes[i])){
                    inventoryCellsPlayer[i].setMousePressed(true);
                    break;
                }
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

                if ( isIn(e, inventoryCellsHermes[i])){
                    if(inventoryCellsPlayer[i].isMousePressed()){
                        inventoryCellsPlayer[i].clicked();
                    }
                    break;
                }
            }

            resetButtons();
        }

    }

    public void mouseMoved(MouseEvent e){
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

                if ( isIn(e, inventoryCellsHermes[i])){
                    inventoryCellsPlayer[i].setMouseOver(true);
                    break;
                }
            }
        }
    }

    public boolean isIn(MouseEvent e, InventoryCellUI ic){
        return ic.getBounds().contains(e.getX(), e.getY());
    }

    private void resetButtons() {
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            for (int i = 0; i < 70; i++){
                inventoryCellsPlayer[i].resetBools();
                inventoryCellsHermes[i].resetBools();
            }
        }
    }

}