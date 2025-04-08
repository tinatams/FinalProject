import java.awt.*;
import java.util.ArrayList;

public interface Collidable{
    public boolean isColliding(Collidable c);
    public Collidable getCollidingWith(ArrayList<Collidable> items);
    
    public int getWorldX();
    public int getWorldY();
    public int getSpriteW();
    public int getSpriteH();
    public Rectangle getHitBox();
}