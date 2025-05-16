/**
    This class handles the fishing mechanic of the game. Schedules time for a fish to be 'appear'
    and for the fish to be caught. If fish is successfully caught player recieves a fish item.

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

    /**
        Constructor that instantiates the original values of the mini game

     	@param p player that recieves the fish item
        fishScheduled is false, because a fish hasn't been scheduled to 'appear'
        canCatchFish is false because there is no fish to cach
        secondsLeftToCatch = 0
        version = -1, because there will be no 'flash'

        the icons for the fish icon and border are also set.
    **/
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

    /**
        Method draws the UI of the minigame. Including the game indicator in the top left corner
        the Game timer, and the flash to signify that a fish can be caught.     
    **/
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

    /**
        Method draws the timer of the game, if there is a fish available to be caught. 
        It writes the amount of time, which is represented by seconds to catch.
    **/
    public void drawTimer(Graphics2D g2d){
        if (canCatchFish){
            g2d.setFont(UIHandler.regularFont.deriveFont(30f));
            g2d.setColor(Color.RED);
            g2d.drawString(Integer.toString(secondsLeftToCatch), 68, 46);
        }
    }

    /**
        Method allows the player to catch a fish

        if there is a fish available to be caught then
            The player will recieve a fish item.
            The game state will be set to playing state
            Seconds Left To Catch is reset
            and a Sound is played to indicate that a fish has been caught

        else 
            Sound is played to signal that the fishing game has been excited
            The game state is set to playig state
            The timer to schedule a fish is canceled
            Seconds Left To Catch is reset
            and fishScheduled is set to false.
    **/
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

    /**
        Method schedules for a fish to 'appear' if a fish can be scheduled and there
        is no fish currently available to be caught.

        The fish is randomly scheduled to appear between 3 and 7 seconds. 
        When the fish is scheduled it starts the count down for when the fish is to be caught.

        For the countdown, if the countdown is less than 0, it resets the time left to catch and cancels
        the timer, and allows for another fish to be scheduled. 
        If the gameState is fishing state (meaning a fish hasn't been caught) then another fish is 
        scheduled and a sound is played to signify the fish was not caught.
    **/
    public void scheduleFish(){
        //System.out.println(fishScheduled + " " +canCatchFish);
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