import java.awt.Graphics2D;
import java.awt.Rectangle;

interface Interactable extends Collidable{
    public void interact(Player player);
    Rectangle getInteractionBox();
    void draw(Graphics2D g2d);
}
