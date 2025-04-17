import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class NPC implements Interactable{ //should extend interactable
    private int worldX, worldY;
    private int spriteW, spriteH;
    private int dialognumber=0;
    private String skin;
    private Rectangle hitBox,interactionBox;
    private BufferedImage spriteSheet;
    private BufferedImage sprite;
    private String[] dialogues;

   
    public NPC(String s, int x, int y, String[] dialogues){
        worldX = x;
        worldY = y;
        this.dialogues=dialogues;
        skin = s;
        spriteW = GameFrame.SCALED; 
        spriteH = GameFrame.SCALED;

         hitBox = new Rectangle(worldX + 10 ,worldY + 20 ,spriteW , spriteH-5);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);

        setUpSprites();
    }

    public void setUpSprites(){
        try{
            sprite = ImageIO.read(new File(String.format("./res/NPCs/%s.png",skin)));

        } catch (IOException e){
        }
    }
    @Override
    public void draw(Graphics2D g2d){
        g2d.drawImage(sprite, worldX, worldY, GameFrame.SCALED, GameFrame.SCALED, null);
    }
    @Override
    public void interact(Player player) {
        speak();
        
    }

    public int getDialogNumber(){
        return dialognumber;
    }

    public void setDialogNumber(int dialognumber){
        this.dialognumber=dialognumber;
    }
    @Override
    public int getSpriteW() {
        return spriteW;
    }

    @Override
    public int getSpriteH() {
        return spriteH;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }
    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
    @Override
    public Rectangle getInteractionBox() {
        return interactionBox;
    }


    public int getDialogueSize(){
        return dialogues.length-1;
    }

    public void speak(){
        GameCanvas.currentDialog=dialogues[dialognumber];
    }
    


}