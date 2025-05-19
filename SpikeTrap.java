/**
    SpikeTrap Class extends teleporter. Trap for the labyrinth that teleports players to a new location when activated.
 
	@author Martina Amale M. Llamas (242648); Zoe Angeli G. Uy (246707)
	@version May 19, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.

    

**/

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

    public SpikeTrap(int x, int y, int map, int newX, int newY) { //constructor for the spike with the status location and where it teleports players
        super(x, y, GameFrame.SCALED, GameFrame.SCALED, map, newX, newY, -1); // direction doesnt matter
        activated = DOWN;
        scheduled = false;
        sprites = new BufferedImage[3];
        loadImage();
    }

    private void loadImage(){ //loads the image depending on what the status of the spike is
        try {
            BufferedImage spriteSet = ImageIO.read(new File("./res/tileSets/TilesetDungeon.png")); 
            int size = 16;
            sprites[UP] = spriteSet.getSubimage(4*size,1*size, size,size);
            sprites[DOWN] = spriteSet.getSubimage(5*size,1*size,size,size);
            sprites[READY] = spriteSet.getSubimage(6*size,1*size,size,size);
        } catch (IOException ex) {
        }
    }

    public void draw(Graphics2D g2d){ //draws the spike based on location
        g2d.drawImage(sprites[activated], super.getWorldX(), super.getWorldY(), super.getSpriteW(), super.getSpriteH(), null);
    }

    public void setActivation(int activation){ //setter for the status of the spike
        activated = activation;
    }

    public boolean isActivated(){ //setter for the status of the spike
        if (activated < 2){
            return false;
        } else {
            return true;
        }
    }

    public void schedule(){ //times the activation to random intervals
        if (!scheduled){
            Timer timer = new Timer();
            TimerTask changeSpikes = new TimerTask(){
                @Override 
                public void run(){
                    activated = isActivated() ? DOWN : UP;
                    scheduled = false;
                }
            };

            int randomInterval = (rand.nextInt(5-1) + 1) * 1000;
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