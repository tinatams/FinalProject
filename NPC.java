
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
    

   
    public NPC(String name,int x, int y){ //Constructor with the NPC name and location
        this.name=name;
        worldX = x;
        worldY = y;
        spriteW = GameFrame.SCALED; 
        spriteH = GameFrame.SCALED;
        
        hitBox = new Rectangle(worldX + 10 ,worldY + 20 ,spriteW , spriteH-5);
        interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);
    }


    public void draw(Graphics2D g2d, String skin){ //draw method for a certain NPC (reads the necessary sprite)
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
    public void interact(Player player) { //changes dialog number (shown dialogue) based on interactions with the player
        speak();
        
    }

    public void speak(){
        UIHandler.currentDialog=dialogues[dialognumber];
    }

    public int getDialogNumber(){ //getter for dialogue number
        return dialognumber;
    }


    @Override
    public int getSpriteW() {  //getter for sprite width
        return spriteW;
    }

    @Override
    public int getSpriteH() { //getter for sprite height
        return spriteH;
    }

    public int getWorldX() { //getter for sprite x-coordinate
        return worldX;
    }

    public int getWorldY() { //getter for sprite y-coordinate
        return worldY;
    }
    @Override
    public Rectangle getHitBox() { //getter for hitbox
        return hitBox;
    }
    @Override
    public Rectangle getInteractionBox() { //getter for interaction box
        return interactionBox;
    }


    public int getDialogueSize(){ //getter for dialogue size
        return dialogues.length-1;
    }


    public String getName(){ //getter for name
        return name;
    }

    public void setWorldX(int worldX) { //setter for x-coordinate
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) { //setter for y-coordinate
        this.worldY = worldY;
    }

    public void setDialogues(String[] dialogues) { //setter for dialogues
        this.dialogues = dialogues;
        dialognumber=0;
    }

    public void setHitBox(Rectangle hitBox) { //setter for hitbox
        this.hitBox = hitBox;
    }

    public void setInteractionBox(Rectangle interactionBox) { //setter for interaction box
        this.interactionBox = interactionBox;
    }
    public void setDialogNumber(int dialognumber){ //setter for dialog number
        this.dialognumber=dialognumber;
    }


    
}