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

    public UITextBox(int xPos, int yPos){

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

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage toDraw = containerIMG;
        if (highlighted){
            toDraw = highlightedIMG;
        }

        g2d.drawImage(toDraw, x, y, 7*GameFrame.SCALED, GameFrame.SCALED, null);

        g2d.setColor(new Color(255, 208, 158));
        g2d.setFont(regularFont.deriveFont(20f));
        g2d.drawString(contents, x + 4 * GameFrame.SCALER, y + 1 * GameFrame.SCALED - 4 * GameFrame.SCALER);
    }

    @Override
    public void update(){
        if (mouseOver || mousePressed || selected){
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
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void clicked() {
        selected = !selected;
    }

    @Override
    public void resetBools(){
        mousePressed = false;
        mouseOver = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void type(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_1:
                contents += "1";
                break;
            case KeyEvent.VK_2:
                contents += "2";
                break;
            case KeyEvent.VK_3:
                contents += "3";
                break;
            case KeyEvent.VK_4:
                contents += "4";
                break;
            case KeyEvent.VK_5:
                contents += "5";
                break;
            case KeyEvent.VK_6:
                contents += "6";
                break;
            case KeyEvent.VK_7:
                contents += "7";
                break;
            case KeyEvent.VK_8:
                contents += "8";
                break;
            case KeyEvent.VK_9:
                contents += "9";
                break;
            case KeyEvent.VK_0:
                contents += "0";
                break;
            case KeyEvent.VK_PERIOD:
                contents += ".";
                break;
            case KeyEvent.VK_BACK_SPACE:
                contents = contents.substring(0, contents.length()-2);
                break;
        }
    }

}