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

        if (code == KeyEvent.VK_BACK_SPACE){
            if (!contents.isBlank() && !contents.isEmpty()){
                contents = contents.substring(0, contents.length()-1);
            }
        } else if (Character.isLetter(e.getKeyChar()) || Character.isDigit(e.getKeyChar()) || Character.isWhitespace(e.getKeyChar()) || code == KeyEvent.VK_PERIOD){
            contents += String.valueOf(e.getKeyChar());
        }
    }

    public String getContents() {
        return contents;
    }



}