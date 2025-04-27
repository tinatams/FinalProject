import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class InventoryCellUI implements UIButton{
    private int x, y;
    private UIHandler ui;
    private EntityGenerator eg;

    private String owner;

    private BufferedImage containerIMG, highlightedIMG;
    private SuperItem contents;
    private boolean highlighted,mousePressed,mouseOver;
    private Rectangle bounds;

    public InventoryCellUI(int xPos, int yPos, UIHandler u, String o){
        ui = u;
        owner = o;

        x = xPos;
        y = yPos;
        
        highlighted = false;
        mousePressed = false;
        mouseOver = false;

        eg = new EntityGenerator();

        try {
            containerIMG = ImageIO.read(new File(String.format("./res/uiAssets/InventoryCell.png")));
            highlightedIMG = ImageIO.read(new File(String.format("./res/uiAssets/InventoryCellHighlight.png")));
        } catch (IOException ex) {
        }

        bounds = new Rectangle(x, y, GameFrame.SCALED, GameFrame.SCALED);
    }

    public void setContents(SuperItem item){
        contents = item;
    }

    @Override
    public void draw(Graphics2D g2d){
        BufferedImage toDraw = containerIMG;
        if (highlighted){
            toDraw = highlightedIMG;
        }

        g2d.drawImage(toDraw, x, y, GameFrame.SCALED, GameFrame.SCALED, null);
        if (contents != null){
            contents.drawSpecific(g2d,x+ 3,y+ 3, 14 * GameFrame.SCALER, 14 * GameFrame.SCALER);

            if (contents.getAmount() > 1){
                g2d.setColor(new Color(255, 255, 255));
                g2d.setFont(UIHandler.regularFont.deriveFont(22f));
                g2d.drawString(Integer.toString(contents.getAmount()), x + 9*GameFrame.SCALER, y + 10*GameFrame.SCALER + 10);
            }
        } 
    }

    @Override
    public void update(){
        if (mouseOver || mousePressed){
            highlighted = true;
        } else {
            highlighted = false;
        }
    }

    @Override
    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    @Override
    public boolean isMouseOver() {
        return mouseOver;
    }

    @Override
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    @Override
    public Rectangle getBounds(){
        return bounds;
    }

    @Override
    public void clicked(){
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            if(contents != null){
                SuperItem itemToTransfer = null;
                if (contents instanceof KeyItem){
                    itemToTransfer = contents;
                    // there might be more code i need to put here lol
                } else {
                    itemToTransfer = eg.newItem(contents.getName());
                }

                if (itemToTransfer != null){
                    if (owner.equals("Player")){
                        ui.getSelectedPlayer().discardItem(itemToTransfer);
                        ((Hermes) ui.getMapHandler().getNPC("Hermes")).collect(itemToTransfer);
                        System.out.println("swapped P TO H");
                    } else if (owner.equals("Hermes")){
                        ((Hermes) ui.getMapHandler().getNPC("Hermes")).discardItem(itemToTransfer);
                        System.out.println("swapped H TO P");
                        ui.getSelectedPlayer().collect(itemToTransfer);
                    }
                }
                

                System.out.println("userInv:");
                for (SuperItem item : ui.getSelectedPlayer().getInventory()){
                    
                    System.out.println(item.getName() + " : " + item.getAmount());
                    if (item instanceof KeyItem){
                        System.out.println(((KeyItem) item).getLockName());
                    }
                }

                System.out.println("hermesInv:");
                for (SuperItem item : ((Hermes) ui.getMapHandler().getNPC("Hermes")).getInventory()){
                    
                    System.out.println(item.getName() + " : " + item.getAmount());
                }
            }
        }
        

    }

    @Override
    public void resetBools(){
        mousePressed = false;
        mouseOver = false;
    }
}