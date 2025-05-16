import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;



public class FishMiniGame {
    private Player player;
    private int secondsLeftToCatch;
    private boolean fishScheduled, canCatchFish;
    private BufferedImage fishIcon, fishBorder;
    private Random rand = new Random();
    private int version;

    private Timer fishTimer;

    public FishMiniGame(Player p){
        player = p;
        fishScheduled = false;
        canCatchFish = false;
        secondsLeftToCatch = 0;
        version = -1;

        try {
            fishIcon = ImageIO.read(new File("./res/items/fish.png"));
            fishBorder = (ImageIO.read(new File(String.format("./res/uiAssets/ButtonAtlas.png")))).getSubimage(1 * 16, 3 * 16, 16, 16);
        } catch (IOException ex) {
        }
    }

    public void draw(Graphics2D g2d){
        drawTimer(g2d);
        g2d.drawImage(fishBorder, 10, 10, 48, 48, null);
        g2d.drawImage(fishIcon, 13, 13, 42, 42, null);
        if (version != -1){
            if (version >= 0 && version <= 15){
                System.out.println(version);
                g2d.setColor(new Color(255,255,255,200));
                g2d.fillRect(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
            }
            version++;
            if (version > 15) version = -1;
        }
    }

    public void drawTimer(Graphics2D g2d){
        if (canCatchFish){
            g2d.setFont(UIHandler.regularFont.deriveFont(30f));
            g2d.setColor(Color.RED);
            g2d.drawString(Integer.toString(secondsLeftToCatch), 68, 46);
        }
    }

    public void catchFish(){
        if (canCatchFish){
            canCatchFish = false;
            player.collect(new FishItem());
            player.getFrame().getSoundHandler().playEffect(SoundHandler.QUEST_DONE);
            GameFrame.gameState = GameFrame.PLAYING_STATE;
            secondsLeftToCatch = 0;
        } else {
            player.getFrame().getSoundHandler().playEffect(SoundHandler.FISH_OUT);
            GameFrame.gameState = GameFrame.PLAYING_STATE;
            secondsLeftToCatch = 0;
            fishTimer.cancel();
            fishScheduled = false;
        }
    }

    public void setFishSchedule(boolean b){
        fishScheduled = b;
    }

    public void scheduleFish(){
        System.out.println(fishScheduled + " " +canCatchFish);
        if (!fishScheduled && !canCatchFish){
            Timer timeToFish = new Timer();
            TimerTask timeLeft = new TimerTask(){
                @Override
                public void run(){
                    secondsLeftToCatch--;
                    System.out.println(secondsLeftToCatch--);
                    if (secondsLeftToCatch < 0){
                        secondsLeftToCatch = 0;
                        timeToFish.cancel();

                        fishScheduled = false;
                        if(GameFrame.gameState == GameFrame.FISHING_STATE){
                            player.getFrame().getSoundHandler().playEffect(SoundHandler.FISH_ESCAPE);
                            canCatchFish = false;
                            fishScheduled = false;
                            scheduleFish();
                        }
                    }
                }
            };

            fishTimer = new Timer();
            TimerTask fishy = new TimerTask(){
                @Override
                public void run(){
                    if(GameFrame.gameState == GameFrame.FISHING_STATE){
                        player.getFrame().getSoundHandler().playEffect(SoundHandler.FISH_BITE);
                        secondsLeftToCatch = 5;
                        version = 0;
                        timeToFish.scheduleAtFixedRate(timeLeft, 0, 1000);
                    }

                    canCatchFish = true;
                }
            };
            int randomInterval = rand.nextInt(5)*1000 + 3*1000;
            fishTimer.schedule(fishy, randomInterval);
            System.out.println("FishScheduled " + randomInterval);

            fishScheduled = true;
        }

    }


}