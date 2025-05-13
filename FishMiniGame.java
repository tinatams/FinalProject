
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public class FishMiniGame {
    private Player player;
    private int secondsLeftToCatch;
    private boolean fishScheduled, canCatchFish;
    private Random rand = new Random();
    private int version;

    public FishMiniGame(Player p){
        player = p;
        fishScheduled = false;
        canCatchFish = false;
        secondsLeftToCatch = 0;
        version = -1;
    }

    public void draw(Graphics2D g2d){

        drawTimer(g2d);
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
            g2d.drawString(Integer.toString(secondsLeftToCatch),10 , 30);
        }
    }

    public void catchFish(){
        if (canCatchFish){
            canCatchFish = false;
            player.collect(new FishItem());
            player.getFrame().getSoundHandler().playEffect(SoundHandler.QUEST_DONE);
            secondsLeftToCatch = 0;
            GameFrame.gameState = GameFrame.PLAYING_STATE;
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
                    if (secondsLeftToCatch < 0){
                        secondsLeftToCatch = 0;
                        timeToFish.cancel();

                        fishScheduled = false;
                        if(GameFrame.gameState == GameFrame.FISHING_STATE){
                            player.getFrame().getSoundHandler().playEffect(SoundHandler.FISH_ESCAPE);
                            canCatchFish = false;
                            scheduleFish();
                        }
                    }
                }
            };

            Timer fishTimer = new Timer();
            TimerTask fishy = new TimerTask(){
                @Override
                public void run(){
                    player.getFrame().getSoundHandler().playEffect(SoundHandler.FISH_BITE);
                    secondsLeftToCatch = 5;
                    version = 0;
                    canCatchFish = true;
                    timeToFish.scheduleAtFixedRate(timeLeft, 0, 1000);
                }
            };
            int randomInterval = rand.nextInt(10)*1000 + 5*1000;
            fishTimer.schedule(fishy, randomInterval);
            System.out.println("FishScheduled " + randomInterval);

            fishScheduled = true;
        }

    }


}