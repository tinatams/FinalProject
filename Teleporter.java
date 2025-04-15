
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Teleporter implements Collidable{
    private int teleportTo;

    private int worldX, worldY, width, height;
    private int putPlayerX, putPlayerY;

    private Rectangle hitBox;
    
    public Teleporter(int x, int y, int w, int h, int map, int newX, int newY){
        teleportTo = map;
        worldX = x;
        worldY = y;
        width = w;
        height = h;

        putPlayerX = newX;
        putPlayerY = newY;

        hitBox = new Rectangle(worldX, worldY, width, height);
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(Color.BLUE);
        g2d.fill(hitBox);
    }

    public int teleportToMap(){
        return teleportTo;
    }

    public int teleportPlayerX(){
        return putPlayerX;
    }

    public int teleportPlayerY(){
        return putPlayerY;
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
    public int getWorldX() {
        return worldX;
    }

    @Override
    public int getWorldY() {
        return worldY;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
}