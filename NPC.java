import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public abstract class NPC implements Interactable{ //should extend interactable
    protected int worldX, worldY;
    protected int spriteW, spriteH;
    
    private String skin, name;
    protected Rectangle hitBox,interactionBox;
    private BufferedImage sprite;

    private String[] dialogues= new String[20];
    private int dialognumber=0;

    protected ArrayList<Integer> quests= new ArrayList<Integer>();

    protected ArrayList<String> before= new ArrayList<String>();
    protected ArrayList<String> during= new ArrayList<String>();
    protected ArrayList<String> after= new ArrayList<String>();
    

   
    public NPC(String name,int x, int y){
        this.name=name;
        worldX = x;
        worldY = y;
        spriteW = GameFrame.SCALED; 
        spriteH = GameFrame.SCALED;
        
        hitBox = new Rectangle(worldX + 10 ,worldY + 20 ,spriteW , spriteH-5);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);
    }


    public void draw(Graphics2D g2d, String skin){
        try{
            sprite = ImageIO.read(new File(String.format("./res/NPCs/%s.png",skin)));

        } catch (IOException e){
        }
        if(name.equals("Poseidon") || name.equals("Demeter") || name.equals("Minotaur")){
            g2d.drawImage(sprite, worldX, worldY, 2*GameFrame.SCALED, 2*GameFrame.SCALED, null);
        }
        else{
            g2d.drawImage(sprite, worldX, worldY, GameFrame.SCALED, GameFrame.SCALED, null);
        }
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
        UIHandler.currentDialog=dialogues[dialognumber];
    }

    public String getName(){
        return name;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setDialogues(String[] dialogues) {
        this.dialogues = dialogues;
        dialognumber=0;
        // for (String s:dialogues){
        //     System.out.println(s);
        // }
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public void setInteractionBox(Rectangle interactionBox) {
        this.interactionBox = interactionBox;
    }


    
}