import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundHandler{
    private Clip clip;

    private Clip[] effects, music;
    private String[] effectName, musicName;

    public SoundHandler(){
        loadSounds();
    }

    public void loadSounds(){
        clip = getClip("BushShake");
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
            System.out.println("uex");
        }
        return null;
    }

    public void playEffect(){
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    


}