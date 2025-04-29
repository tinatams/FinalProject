import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class UIStartButton implements UIButton {
    private int x, y;
    private GameStarter gameStarter;
    private MenuHandler menuHandler;

    private int indexNum;

    private BufferedImage[] sprites;
    private SuperItem contents;
    private boolean mousePressed,mouseOver;
    private Rectangle bounds;
    
    public UIStartButton(int xPos, int yPos, GameStarter gs, MenuHandler mh){
        gameStarter = gs;
        menuHandler = mh;

        x = xPos;
        y = yPos;
        
        mousePressed = false;
        mouseOver = false;
        
        sprites = new BufferedImage[3];
        indexNum = 0; // 0 = default, 1 = hover, 2 = pressed;

        try {
            int tileSize = GameFrame.PIXELRATIO;
            BufferedImage temp = ImageIO.read(new File(String.format("./res/uiAssets/ButtonAtlas.png")));
            sprites[0] = temp.getSubimage(0*tileSize, 1*tileSize, 5*tileSize, 2*tileSize);
            sprites[1] = temp.getSubimage(5*tileSize, 1*tileSize, 5*tileSize, 2*tileSize);
            sprites[2] = temp.getSubimage(10*tileSize, 1*tileSize, 5*tileSize, 2*tileSize);
        } catch (IOException ex) {
        }

        bounds = new Rectangle(x, y, 5 * GameFrame.SCALED, 2*GameFrame.SCALED);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprites[indexNum], x, y, 5 * GameFrame.SCALED, 2*GameFrame.SCALED, null);
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
        if (GameMenu.STATE == GameMenu.CHOOSING){
            if (!menuHandler.getPortNumber().isBlank() && !menuHandler.getIpAddress().isBlank()){
                gameStarter.connectToServer(menuHandler.getIpAddress(), menuHandler.getPortNumber());
            }
            
            if (gameStarter.isConnected()){
                gameStarter.setUpFrame(menuHandler.getSkin());
            } else {
                menuHandler.setValidInputs(false);
            }
            
        } else {
            GameMenu.STATE = GameMenu.CHOOSING;
        }
    }

    @Override
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

}