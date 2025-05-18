/**
    Athena Class that extends NPC and contains an instances of iventory,Entity Generator, Quest Handler, and the name of the NPC,
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

public class Athena extends NPC{
    public static final String name = "Athena";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private boolean completed=false;
    private boolean first=true;
    private boolean failsafe=false;
    private QuestHandler qh=new QuestHandler();
    

    public Athena(int x, int y) { //Constructor with location the entity should be drawn and dialogue
         
        super("Athena",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        during.add("Please come back later");
        after.add("Apollo asked you to give me this you say?/n(You give her the prophecy)/nWhen faith runs dry and mortals stray,~Olympus shuts its shining way.~Five gods left in mortal dust,~Locked out by doubt, by fading trust./nTo skybound isle their hope is cast—~Go to temple high, call a power vast.~Only through prayer and kindled name~Can the twelve become axios of godly flame./n.../nInteresting./nIt seems that we are trapped here because~mortals have stopped believing in us./nThose who are among mortals remain until ~the gates of Olympus open once more./nWe must get to an island with a temple of~the Twelve…I think I know which one./nMortal, I know your kind do not believe ~in us currently but assist us and~ all of us will reward you. I swear it.");
        before.add("How to help you ask?/nWell from the prophecy we need to find~a way to the island they spoke of. ~We will need a boat./nYou must speak with Hephaestus.~He likes to stick around caves./nI do believe that you will also need to~sacrifice to the goddess Demeter/nThe islands are barren and Hephaestus~will need wood ~to build a vessel./nThe goddess Artemis can help your friend~with that.");
        during.add("(Hint: you might want to check this island~thoroughly for a hidden workshop)/n(hint: your friend might want to check ~thier island for a huntress in green)");
        after.add("Good Job!");
        
        
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
                super.setDialogues("Please stay steadfast! Our plan will work.".split("/n"));
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

    public String check(Player player){  //Method that returns current dialogue according to active quest list and removes inventory items when needed
        
        String result="";
        qh=player.getFrame().getQuestH();
        for(int i=0;i<qh.states.length;i++){
            System.out.println("HIII");
                if(qh.states[i]==qh.ACTIVE){
                    System.out.println(i);
                    if(i==0){
                        result="Hello Mortal! I am the goddess Athena./n Do you need something?/n .../nNo?/nI apologize but I'm a bit busy right now";
                    }
                    else if(i==1){
                        System.out.println("ITs her?");
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                failsafe=true;
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);

                                qh.states[1]=QuestHandler.COMPLETED;
                                qh.states[2]=QuestHandler.ACTIVE;
                                qh.states[3]=QuestHandler.ACTIVE;
                                System.out.println("IT SWITCHED");
                                return result;
                            }
                        }
                        if(failsafe==true){
                            qh.states[1]=QuestHandler.COMPLETED;
                            qh.states[2]=QuestHandler.ACTIVE;
                            qh.states[3]=QuestHandler.ACTIVE;
                            result=after.get(0);
                        }
                    }

                    else if(i==2 || i==3){
                        if(first){
                            result=before.get(0);
                            System.out.println(i);
                            first=false;
                        }
                        else{
                            result=during.get(1);
                            System.out.println(i);
                        }
                        return result;
                    }
                }
            }
        return result;
    }


    
}