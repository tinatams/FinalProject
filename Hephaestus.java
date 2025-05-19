/**
    Hephaestus Class that extends NPC and contains an instances of iventory,Entity Generator, Quest Handler, and the name of the NPC,
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

public class Hephaestus extends NPC{ 
    public static final String name = "Hephaestus";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private boolean completed=false;
    public static boolean first=true;
    private QuestHandler qh=new QuestHandler();
    
    /**
    Constructor with location the entity should be drawn and dialogue   
    **/
    public Hephaestus(int x, int y) {
        
        super("Hephaestus",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        before.add("*cling*/n*clang*/nHmm? Hello kid/nBuild a boat?/nWell you’ll need wood from the other island~I think I still have a few materials./nHere take this pickaxe,get me some more~iron from the mines and I’ll forge your~friend an ax.");
        during.add("Hint: it may take a few hits to break iron");
        after.add("Here you go kid/nI’d suggest you hand that over to your~friend then come back I need you~to get me more materials!");
        before.add("*grunts*/nHey kid./nIf we’re travelling by ship we need~to give an offering for safe travel./nYou’ll need to go to sacrifice fish to~Poseidon.I think his statue should be near~the entrance to the mines.");
        during.add("Hint: you might wanna talk to a statue");
        before.add("Nice work kid.~Get the other materials now/nYou need to give me the wood so I can~start building");
        during.add("You don't got any wood yet");
        after.add("Nice work kid.~I need one last thing");
        before.add("It’s in Daedalus’s workshop on the other~island. He always keeps his workshop locked/nThe key is at the center of the labyrinth.~The entrance to the labyrinth should~be somewhere in these mines/nHere take this to open the~labyrinth!");
        during.add("*cling* *clang*/nYou need to get the item first kid");
        after.add("Good Job kid./nGive me a second then we can go");
        
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
                super.setDialogues("Ok then let's go.".split("/n"));
                // GameFrame.gameState=GameFrame.END_STATE;
            }
            else{
                String result=check(player);
                super.setDialogues(result.split("/n"));
                System.out.println(result);
                player.getFrame().setQuestH(qh);
            }
        }
        
        super.speak(); //Changes NPC dialogue
        
        

    }
    /**
     Method that returns current dialogue according to active quest list and removes inventory items when needed
    **/
    public String check(Player player){ 
        
        String result="";
        qh=player.getFrame().getQuestH();
        for(int i=0;i<qh.states.length;i++){
                if(qh.states[i]==qh.ACTIVE){
                    inventory=player.getInventory();
                    if(i<3){
                        result="*cling* *clang*/nI'm busy";
                    }
                    else if(i==3){
                        result=before.get(0);
                        player.collect(new PickaxeItem());
                        qh.states[3]=QuestHandler.COMPLETED;
                        qh.states[6]=QuestHandler.ACTIVE;
                        return result;
    
                    }

                    else if(i==6){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);
                                player.collect(new AxeItem());
                                qh.states[6]=QuestHandler.COMPLETED;
                                qh.states[7]=QuestHandler.ACTIVE;
                                first=true;
                                return result;
                            }
                        }
                    }
                    else if(i==7 || i==8 || i==9){
                        if(first){
                            result=before.get(1);
                            first=false;
                            return result;
                        }
                        result=during.get(1);
                        return result;
                    }
                    else if(i==10){
                        if(first){
                            result=before.get(2);
                            first=false;
                            return result;
                        }
                        result=during.get(2);
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(1);
                                qh.states[10]=QuestHandler.COMPLETED;
                                qh.states[11]=QuestHandler.ACTIVE;
                                qh.states[16]=QuestHandler.ACTIVE;
                                first=true;
                                return result;
                            }
                        }
                        return result;
                    }
                    else if(i==16){
                        
                        if(first){
                            result=before.get(3);
                            first=false;
                            player.collect(new KeyItem(1,1,"HEPHEASTUS"));
                            return result;
                        }
                        System.out.println(inventory.size());
                        result=during.get(3);
                        for(int j=0; j<inventory.size();j++){
                            System.out.println(inventory.get(j).getName());
                            if(inventory.get(j).getName().equals("WINGS") && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(2);
                                qh.states[16]=QuestHandler.COMPLETED;
                                first=true;
                                completed=true;
                                return result;
                            }
                        }
                        return result;
                    }

                }
            }
        return result;
    }

    
}