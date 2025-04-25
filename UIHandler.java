import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class UIHandler{
    public static Font regularFont;
    public static String currentDialog = "";
    private BufferedImage dialogueBox, inventoryTemplate, blankHalfPanel;
    private Player selectedPlayer;
    private MapHandler mapHandler;

    public UIHandler(Player sPlayer, MapHandler mHandler){
        setUpUIComponents();
        selectedPlayer = sPlayer;
        mapHandler = mHandler;
    }

    public void draw(Graphics2D g2d){
        if(GameFrame.gameState == GameFrame.DIALOG_STATE) drawDialogScreen(g2d);

        else if(GameFrame.gameState == GameFrame.INVENTORY_STATE){
            g2d.setColor(new Color(0,0,0,125));
            g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);

            drawInventory(g2d, selectedPlayer.getInventory(),1,1);
            drawQuestPanel(g2d,11,1);

        }

        else if (GameFrame.gameState == GameFrame.HERMES_STATE){
            g2d.setColor(new Color(0,0,0,125));
            g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);

            drawInventory(g2d, selectedPlayer.getInventory(),1,1);

            if (mapHandler.getNPC("Hermes") != null){
                Hermes hermes = (Hermes) mapHandler.getNPC("Hermes");

                drawInventory(g2d, hermes.getInventory() , 11, 1);
            }
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
    
    public void drawInventory(Graphics2D g2d, ArrayList<SuperItem> inventory, int panelX, int panelY){
        g2d.drawImage(inventoryTemplate, panelX* GameFrame.SCALED, panelY*GameFrame.SCALED, 9 * GameFrame.SCALED, 14 * GameFrame.SCALED, null);
        
        int x = 1;
        int y = 3;
        for (SuperItem item : inventory){
            item.drawSpecific(g2d, x*GameFrame.SCALED + 3 + panelX* GameFrame.SCALED, y*GameFrame.SCALED + 3 + panelY* GameFrame.SCALED, 14 * GameFrame.SCALER, 14 * GameFrame.SCALER);
            if (item.getAmount() > 1){
                g2d.setColor(new Color(255, 255, 255));
                g2d.setFont(regularFont.deriveFont(24f));
                g2d.drawString(Integer.toString(item.getAmount()), (x+1)*GameFrame.SCALED - 14 + panelX* GameFrame.SCALED, (y+1)*GameFrame.SCALED - 7+ panelY* GameFrame.SCALED);
            }
            x++;
            if (x > 8){
                x = 2;
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

    public void mouseClicked(MouseEvent me){

    }

    public void update(){
        
    }
}