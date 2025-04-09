import java.awt.*;

public interface Collidable{
    public int getWorldX();
    public int getWorldY();
    public int getSpriteW();
    public int getSpriteH();
    public Rectangle getHitBox();
}