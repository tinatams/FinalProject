import java.awt.*;
import java.util.ArrayList;
import javax.imageio.*;

/**
    Poseidon Class that extends NPC and contains an instances of iventory,Entity Generator, Quest Handler, and the name of the NPC,
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

public class Poseidon extends NPC{
    public static final String name = "Poseidon";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean first=true;
    private boolean completed=false;
    private QuestHandler qh=new QuestHandler();
    
    /**
    Constructor with location the entity should be drawn and dialogue   
    **/
    public Poseidon(int x, int y) {
        super("Poseidon",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();

        super.hitBox = new Rectangle(x + 5 ,y + 15 ,2*spriteW , (2*spriteH)-5);
        super.interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , 2*spriteW + GameFrame.SCALED, 2*spriteH + GameFrame.SCALED);
        
        before.add("Poseidon only accepts fish as sacrifice");
        during.add("Hint: The water nearby can be used to~get fish");
        after.add("FISH SACRIFICED!~May you have safe passage!");
        
        
        this.x = x;
        this.y = y;
    }

    @Override 
    public void draw(Graphics2D g2d){ //Draw method that calls NPC draw method
        super.draw(g2d,name);
    }

    @Override
    public void interact(Player player){ //Method that dictates dialogue when players interact with this NPC
        if(super.getDialogNumber()==0){
            if(completed){
                super.setDialogues("Safe trip!".split("/n"));
            }
            else{
                super.setDialogues(check(player).split("/n"));
                player.getFrame().setQuestH(qh);
            }
        }
        
        super.speak();
    }


    public String check(Player player){
        String result="";
        qh=player.getFrame().getQuestH();
        for(int i=0;i<qh.states.length;i++){
                if(qh.states[i]==QuestHandler.ACTIVE){
                    System.out.println(i);
                    if(i<7){
                        result="It's a statue";
                    }
                    else if(i==7){
                        qh.states[7]=qh.COMPLETED;
                        qh.states[8]=qh.ACTIVE;
                        result=before.get(0);
                        return result;
                    }
                    else if(i==8){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);
                                qh.states[8]=QuestHandler.COMPLETED;
                                qh.states[10]=QuestHandler.ACTIVE;
                                completed=true;
                                Hephaestus.first=true;
                                return result;
                            }
                        }
                    }
                }
        }
        return result;
                  
    }
    
}