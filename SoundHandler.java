import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.*;

public class SoundHandler{
    private Clip clip;

    private String[] effectName;

    public static final int WALK = 0;

    public static final int HIT_ROCK = 1;
    public static final int STONE_CRACK = 2; // Stone crack v2 at index 3
    public static final int SHAKE_BUSH = 4;
    public static final int WOOD_CHOP = 5;
    public static final int TREE_BREAK = 6;

    public static final int DOG_SOUND = 7; // dog sound v2 at index 8

    public static final int STAIRS = 9;

    public static final int BUTTON = 10;
    public static final int INV_IN = 11;
    public static final int INV_OUT = 12;
    public static final int QUEST_DONE = 13;
    public static final int START_GAME = 14;

    public static final int FISH_BITE = 15;
    public static final int FISH_ESCAPE = 16;
    public static final int FISH_IN = 17;
    public static final int FISH_OUT = 18;

    private Random rand = new Random();

    public SoundHandler(){
        effectName =  new String[] {"walk", "hitRock", "stoneCrack1", "stoneCrack2", "shakeBush", "woodChop", "treeBreak", "dogSound1", "dogSound2", "stairs", "buttonClick", "inventoryIn", "inventoryOut", "questDone", "startGame", "fishBite", "byeFishy", "startFish", "endFish"};
    }

    private Clip getClip(String name){
        try {
            AudioInputStream audio; 
            audio = AudioSystem.getAudioInputStream(new File(String.format("./res/sounds/%s.wav",name)));
            Clip c= AudioSystem.getClip();
            c.open(audio);

            return c;
        } catch (IOException ex) {
            System.out.println("ex");
        } catch (LineUnavailableException ex) {
            System.out.println("lex");
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("uex" + name);
        }
        return null;
    }

    public void playEffect(int index){
        if (index == STONE_CRACK || index == DOG_SOUND){
		    index += rand.nextInt(2);
        }

        System.out.println(effectName[index]);
        Clip clipToPlay = getClip(effectName[index]);
        clipToPlay.setMicrosecondPosition(0);
        clipToPlay.start();
    }

}