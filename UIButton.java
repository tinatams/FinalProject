import java.awt.*;

public interface UIButton{
    public abstract void draw(Graphics2D g2d);
    public abstract void update();
    public abstract boolean  isMousePressed();
    public abstract void setMousePressed(boolean mousePressed);
    public abstract boolean isMouseOver();
    public abstract void setMouseOver(boolean mouseOver);
    public abstract Rectangle getBounds();
    public abstract void clicked();
    public abstract void resetBools();
}