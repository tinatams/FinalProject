
/**
    NPC Class that implements Interactable. Handles dialogue and which NPCs are drawn from files.
 
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
import java.util.ArrayList;
import javax.imageio.*;

public abstract class NPC implements Interactable{
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
    

    /**
        Constructor with the NPC name and location
    **/
    public NPC(String name,int x, int y){ 
        this.name=name;
        worldX = x;
        worldY = y;
        spriteW = GameFrame.SCALED; 
        spriteH = GameFrame.SCALED;
        
        hitBox = new Rectangle(worldX + 10 ,worldY + 20 ,spriteW , spriteH-5);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);
    }

     /**
        draw method for a certain NPC (reads the necessary sprite)
    **/
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

    /**
        changes dialog number (shown dialogue) based on interactions with the player
    **/
    @Override
    public void interact(Player player) {
        speak(); 
    }

    /**
        sets shown dialog based on  current dialoghnumber
    **/
    public void speak(){
        UIHandler.currentDialog=dialogues[dialognumber];
    }

    /**
        getter for dialogue number
    **/
    public int getDialogNumber(){ 
        return dialognumber;
    }

     /**
        getter for sprite width
    **/
    @Override
    public int getSpriteW() { 
        return spriteW;
    }

     /**
       getter for sprite height
    **/
    @Override
    public int getSpriteH() {
        return spriteH;
    }

     /**
        getter for sprite x-coordinate
    **/
    public int getWorldX() { 
        return worldX;
    }

     /**
        getter for sprite y-coordinate
    **/
    public int getWorldY() { 
        return worldY;
    }

     /**
        getter for hitbox
    **/
    @Override
    public Rectangle getHitBox() { 
        return hitBox;
    }

     /**
        getter for interaction box
    **/
    @Override
    public Rectangle getInteractionBox() { 
        return interactionBox;
    }

     /**
        getter for dialogue size
    **/
    public int getDialogueSize(){
        return dialogues.length-1;
    }

     /**
        getter for name
    **/
    public String getName(){ 
        return name;
    }

    /**
        setter for x-coordinate
    **/
    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

     /**
        setter for y-coordinate
    **/
    public void setWorldY(int worldY) { //
        this.worldY = worldY;
    }

     /**
        setter for dialogues
    **/
    public void setDialogues(String[] dialogues) { 
        this.dialogues = dialogues;
        dialognumber=0;
    }

     /**
        setter for hitbox
    **/
    public void setHitBox(Rectangle hitBox) { 
        this.hitBox = hitBox;
    }

     /**
        setter for interaction box
    **/
    public void setInteractionBox(Rectangle interactionBox) { 
        this.interactionBox = interactionBox;
    }

     /**
       setter for dialog number
    **/
    public void setDialogNumber(int dialognumber){ 
        this.dialognumber=dialognumber;
    }


    
}