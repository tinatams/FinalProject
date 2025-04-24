import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class SpikeTrap extends Teleporter{
    private BufferedImage[] sprites;
    private boolean scheduled;
    private int activated;
    private Random rand = new Random();


    private static final int DOWN = 0;
    private static final int READY = 1;
    private static final int UP = 2;

    public SpikeTrap(int x, int y, int map, int newX, int newY) {
        super(x, y, GameFrame.SCALED, GameFrame.SCALED, map, newX, newY);
        activated = DOWN;
        scheduled = false;
        sprites = new BufferedImage[3];
        loadImage();
    }

    private void loadImage(){
        try {
            BufferedImage spriteSet = ImageIO.read(new File("./res/tileSets/TilesetDungeon.png")); 
            int size = 16;
            sprites[UP] = spriteSet.getSubimage(4*size,1*size, size,size);
            sprites[DOWN] = spriteSet.getSubimage(5*size,1*size,size,size);
            sprites[READY] = spriteSet.getSubimage(6*size,1*size,size,size);
        } catch (IOException ex) {
        }
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(sprites[activated], super.getWorldX(), super.getWorldY(), super.getSpriteW(), super.getSpriteH(), null);
    }

    public void setActivation(int activation){
        activated = activation;
    }

    public boolean isActivated(){
        if (activated < 2){
            return false;
        } else {
            return true;
        }
    }

    public void schedule(){
        if (!scheduled){
            Timer timer = new Timer();
            TimerTask changeSpikes = new TimerTask(){
                @Override 
                public void run(){
                    activated = isActivated() ? DOWN : UP;
                    scheduled = false;
                }
            };

            int randomInterval = (rand.nextInt(5-1) + 1) * 10000;
            timer.schedule(changeSpikes, randomInterval);
            scheduled = true;

            Timer warnTime = new Timer();
            TimerTask warning = new TimerTask(){
                @Override 
                public void run(){
                    if (!isActivated()) setActivation(READY);
                }
            };

            warnTime.schedule(warning, randomInterval - 1000);
            System.out.println("scheduled");
        }
    }

}