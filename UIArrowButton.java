import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class UIArrowButton implements UIButton{
    private int x, y;
    private String direction;

    private int indexNum;

    private BufferedImage[] sprites;
    private boolean mousePressed,mouseOver;

    private UIPickSkin skinPicker;
    private Rectangle bounds;

    public UIArrowButton(int xPos, int yPos, String d, UIPickSkin ups){
        x = xPos;
        y = yPos;

        skinPicker = ups;

        direction = d.toUpperCase();
        
        mousePressed = false;
        mouseOver = false;
        
        sprites = new BufferedImage[2];
        indexNum = 0; // 0 = default, 1 = hover;

        try {
            int tileSize = GameFrame.PIXELRATIO;
            BufferedImage temp = ImageIO.read(new File(String.format("./res/uiAssets/ButtonAtlas.png")));
            if (direction.equals("LEFT")){
                sprites[0] = temp.getSubimage(1*tileSize, 4*tileSize, 1*tileSize, 1*tileSize);
                sprites[1] = temp.getSubimage(1*tileSize, 5*tileSize, 1*tileSize, 1*tileSize);
            } else if (direction.equals("RIGHT")){
                sprites[0] = temp.getSubimage(0*tileSize, 4*tileSize, 1*tileSize, 1*tileSize);
                sprites[1] = temp.getSubimage(0*tileSize, 5*tileSize, 1*tileSize, 1*tileSize);
            }
            
        } catch (IOException ex) {
        }

        bounds = new Rectangle(x, y, 1 * GameFrame.SCALED, 1*GameFrame.SCALED);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprites[indexNum], x, y, 1 * GameFrame.SCALED, 1*GameFrame.SCALED, null);
    }

    @Override
    public void update() {
        indexNum = 0;

        if (mouseOver || mousePressed){
            indexNum = 1;
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
        if (direction.equals("RIGHT")){
            skinPicker.up();
        } else if (direction.equals("LEFT")){
            skinPicker.down();
        }
    }

    @Override
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

}