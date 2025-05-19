/**
    Minotaur Class that extends NPC and contains an instances of iventory,Entity Generator, Quest Handler, and the name of the NPC,
    as well as variables to determine the location of where in the frame the entity is drawn. It contains dialogue options that shift
    according to active quests and removes quest items when needed. It also has a draw method that activates the NPC draw method.
 
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
import java.util.ArrayList;


public class Minotaur extends NPC{ 
    public static final String name = "Minotaur";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean first=true;
    private boolean completed=true;
     private QuestHandler qh=new QuestHandler();
    
    /**
    Constructor with location the entity should be drawn and dialogue   
    **/
    public Minotaur(int x, int y) {
        
        super("Minotaur",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        
        super.hitBox = new Rectangle(x + 5 ,y + 15 ,2*spriteW , (2*spriteH)-5);
        super.interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , 2*spriteW + GameFrame.SCALED, 2*spriteH + GameFrame.SCALED);

        before.add("*ROAR!*/nYou cannot defeat this beast!/nPerhaps you need to subdue it./nLook for the god of wine./nHe may be able to help you!");
        during.add("Hint: The god of wine might be~on the other island");
        after.add("*The Minotaur faints*/nHint: There is an assist room for the~labyrinth in island 1./nHere's the key!");
        
        
        this.x = x;
        this.y = y;
    }
    /**
     Draw method that calls NPC draw method
    **/
    @Override 
    public void draw(Graphics2D g2d){ 
        super.draw(g2d,name); 
    }
    /**
     Method that dictates dialogue when players interact with this NPC
    **/
    @Override
    public void interact(Player player){
        if(super.getDialogNumber()==0){
            if(completed){
                super.setDialogues("*snore*".split("/n"));
                x=5*GameFrame.SCALED;
                y=5*GameFrame.SCALED;
                super.worldX=x;
                super.worldY=y;
                super.hitBox = new Rectangle(x + 5 ,y + 15 ,2*spriteW , (2*spriteH)-5);
                super.interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , 2*spriteW + GameFrame.SCALED, 2*spriteH + GameFrame.SCALED);
            }
            else{
                super.setDialogues(check(player).split("/n"));
                player.getFrame().setQuestH(qh);
            }
        }
        
        super.speak();

        

    }
    /**
     Method that returns current dialogue according to active quest list and removes inventory items when needed
    **/
    public String check(Player player){
        String result="";
        qh=player.getFrame().getQuestH();
        for(int i=0;i<qh.states.length;i++){
                if(qh.states[i]==QuestHandler.ACTIVE){
                    System.out.println(i);
                    if(i==11){
                        result=before.get(0);
                        first=false;
                        qh.states[11]=QuestHandler.COMPLETED;
                        qh.states[12]=QuestHandler.ACTIVE;
                        qh.states[14]=QuestHandler.ACTIVE;
                        return result;                        
                    }
                    else if(i==14){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);
                                player.collect(new KeyItem(1,1,"DIONYSUS"));
                                completed=true;
                                
                               
                                
                                qh.states[14]=QuestHandler.COMPLETED;
                                qh.states[15]=QuestHandler.ACTIVE;

                                
                                return result;
                            }
                        }
                    }

                }
        }
        return result;
                  
    }



}