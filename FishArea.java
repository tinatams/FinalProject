import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FishArea implements Interactable{
    public static String ITEMNAME = "FISHAREA"; 

    private int worldX, worldY, width, height;
    private Rectangle bounds;

    public FishArea(int x, int y, int w, int h){
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;
        width = w * GameFrame.SCALED;
        height = h * GameFrame.SCALED;

        bounds = new Rectangle(worldX, worldY, width, height);
    }


    @Override
    public void interact(Player player) {
        player.getFrame().getFishy().scheduleFish();
        GameFrame.gameState = GameFrame.FISHING_STATE;
        player.getFrame().getSoundHandler().playEffect(SoundHandler.FISH_IN);
    }

    @Override
    public Rectangle getInteractionBox() {
        return bounds;
    }

    @Override
    public void draw(Graphics2D g2d) {}

    @Override
    public int getWorldX() {
        return worldX;
    }

    @Override
    public int getWorldY() {
        return worldY;
    }

    @Override
    public int getSpriteW() {
        return width;
    }

    @Override
    public int getSpriteH() {
        return height;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(-1,-1,0,0);
    }
    
}