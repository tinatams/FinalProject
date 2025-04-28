import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class UISendHermes implements UIButton{
    private int x, y;
    private UIHandler ui;
    private int indexNum;

    private BufferedImage[] sprites;
    private SuperItem contents;
    private boolean mousePressed,mouseOver;
    private Rectangle bounds;

    public UISendHermes(int xPos, int yPos, UIHandler u){
        ui = u;

        x = xPos;
        y = yPos;
        
        mousePressed = false;
        mouseOver = false;
        
        sprites = new BufferedImage[3];
        indexNum = 0; // 0 = default, 1 = hover, 2 = pressed;

        try {
            sprites[0] = ImageIO.read(new File(String.format("./res/uiAssets/ButtonTemplate.png")));
            sprites[1] = ImageIO.read(new File(String.format("./res/uiAssets/HoverButtonTemplate.png")));
            sprites[2] = ImageIO.read(new File(String.format("./res/uiAssets/PressedButtonTemplate.png")));
        } catch (IOException ex) {
        }

        bounds = new Rectangle(x, y, 2 * GameFrame.SCALED, GameFrame.SCALED);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprites[indexNum], x, y, 2 * GameFrame.SCALED, GameFrame.SCALED, null);
    }

    @Override
    public void update() {
        indexNum = 0;

        if (mouseOver){
            indexNum = 1;
        }
        
        if (mousePressed){
            indexNum = 2;
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
        Hermes hermes = (Hermes) ui.getMapHandler().getNPC(Hermes.name);
        if (hermes != null){
            hermes.send();
        }
    }

    @Override
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
    
}