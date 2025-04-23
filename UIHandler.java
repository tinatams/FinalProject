import java.awt.*;

public class UIHandler{
    public static String currentDialog = "";

    public UIHandler(){

    }

    public void draw(Graphics2D g2d){
        if(GameFrame.gameState == GameFrame.DIALOG_STATE) drawDialogScreen(g2d);
        if(GameFrame.gameState == GameFrame.INVENTORY_STATE){
            g2d.setColor(new Color(0,0,0,125));
            g2d.fillRect(0,0,GameFrame.WIDTH, GameFrame.HEIGHT);


        }
    }

    public void drawInventory(){
        
    }

    public void drawDialogScreen(Graphics2D g2d){
        int x=16*2;
        int y=30;
        int width= GameFrame.WIDTH-90;
        int height=GameFrame.HEIGHT/3;
        Color c=new Color(0,0,0,200);
        g2d.setColor(c);
        g2d.fillRoundRect(x,y,width,height,35,35);

        c=new Color(250,250,250);
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
        
        x+=GameFrame.SCALED;
        y+=GameFrame.SCALED;

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,24F));
        for (String line: currentDialog.split("#")){
            g2d.drawString(line,x,y);
            y+=40;
        }
    }
}