import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FishArea implements Interactable{
    private int worldX, worldY, width, height;
    private Rectangle bounds;

    public FishArea(int x, int y, int w, int h){
        worldX = x;
        worldY = y;
        width = w;
        height = h;

        bounds = new Rectangle(worldX, worldY, width, height);
    }


    @Override
    public void interact(Player player) {
        player.getFrame().getFishy().scheduleFish();
        GameFrame.gameState = GameFrame.FISHING_STATE;
    }

    @Override
    public Rectangle getInteractionBox() {
        return bounds;
    }

    @Override
    public void draw(Graphics2D g2d) {}

    @Override
    public int getWorldX() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getWorldY() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSpriteW() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSpriteH() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Rectangle getHitBox() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}