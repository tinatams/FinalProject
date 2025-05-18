/**
    Artemis Class that extends NPC and contains an instances of iventory,Entity Generator, Quest Handler, and the name of the NPC,
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

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Artemis extends NPC{
    public static final String name = "Artemis";
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
    public Artemis(int x, int y) { 
        
        super("Artemis",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        quests.add(2);
        quests.add(4);
        quests.add(5);
        before.add("Athena you say?/nSacrifice to Demeter?/nGather meat?/nI would love to help!/nBut you see I’ve broken my bow.~Please get me string around the island!");
        during.add("Hint: String can be found around the island");
        after.add("Good Job! But now I can't find my dogs.");
        before.add("Could you find my dogs?/nHere you go!~You can find them using this bone");
        during.add("Hint: My dogs like to wander around.~They can also swim");
        after.add("Thank you for helping me out!/nHere's the meat");
        
        
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
                super.setDialogues("Hey! Thanks for finding my dogs!".split("/n"));
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
                    if(i==0 || i==1){
                        System.out.println(i);
                        result="Hey! Have you seen my dogs?/n.../nNo?/nOk...";
                    }
                    else if(i==2){
                        System.out.println(i);
                        result=before.get(0);
                        qh.states[2]=qh.COMPLETED;
                        qh.states[4]=qh.ACTIVE;
                        StringItem.setInteractable(true);
                        return result;
                    }
                    else if(i==4){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);
                                qh.states[4]=QuestHandler.COMPLETED;
                                qh.states[5]=QuestHandler.ACTIVE;
                                return result;
                            }
                        }
                    }
                    else if(i==5){
                        if(first){
                            result=before.get(1);
                            player.collect(new BoneItem());
                            first=false;
                            return result;
                        }
                        result=during.get(1);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(1);
                                completed=true;
                                qh.states[5]=QuestHandler.COMPLETED;
                                qh.states[6]=QuestHandler.ACTIVE;
                                player.collect(new MeatItem());
                                completed=true;
                                return result;
                            }
                        }
                    }
                
                }
        }
        return result;
                  
    }

    
}