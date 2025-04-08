
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

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
    public boolean isColliding(Collidable c){
        hitBox = new Rectangle(worldX, worldY, width, height);
        Rectangle itemHitBox = c.getHitBox();

        return hitBox.intersects(itemHitBox);
    }

    @Override
    public Collidable getCollidingWith(ArrayList<Collidable> items) {
        for (Collidable other: items){
            if (isColliding(other))
                return other;
        }

        return null;
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