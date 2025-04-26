import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class InventoryCellUI {
    private int x, y;
    private BufferedImage containerIMG;
    private SuperItem contents;

    public InventoryCellUI(int xPos, int yPos){
        x = xPos;
        y = yPos;
        
        try {
            containerIMG = ImageIO.read(new File(String.format("./res/uiAssets/InventoryCell.png")));
        } catch (IOException ex) {
        }
    }

    public void setContents(SuperItem item){
        contents = item;
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(containerIMG, x, y, GameFrame.SCALED, GameFrame.SCALED, null);
        if (contents != null){
            contents.drawSpecific(g2d,x+ 3,y+ 3, 14 * GameFrame.SCALER, 14 * GameFrame.SCALER);

            if (contents.getAmount() > 1){
                g2d.setColor(new Color(255, 255, 255));
                g2d.setFont(UIHandler.regularFont.deriveFont(22f));
                g2d.drawString(Integer.toString(contents.getAmount()), x + 9*GameFrame.SCALER, y + 10*GameFrame.SCALER + 10);
            }
        } 
    }

}