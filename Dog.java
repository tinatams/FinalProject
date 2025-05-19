/**
    Dog Class that extends NPC and contains an instances of iventory,Entity Generator, Quest Handler, and the name of the NPC,
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

public class Dog extends NPC{ 
    public static final String name = "Dog";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean captured=false;
    private QuestHandler qh=new QuestHandler();
    

    public Dog(int x, int y) { //Constructor with location the entity should be drawn and dialogue
        
        super("Dog",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        before.add("*GROWL*");
        during.add("Hint: You need a bone");
        after.add("Woof! Woof!");
        
        
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
                super.setDialogues(check(player).split("/n"));
                player.getFrame().setQuestH(qh);
        }
        else{
            super.setDialogues("This dog is really stubborn".split("/n"));
        }
        
        super.speak(); //Changes NPC dialogue
        player.getFrame().getSoundHandler().playEffect(SoundHandler.DOG_SOUND);
    }

    public String check(Player player){ //Method that returns current dialogue according to active quest list and removes inventory items when needed
        String result="";
        qh=player.getFrame().getQuestH();
        result=before.get(0);
        for(int i=0;i<qh.states.length;i++){

                if(qh.states[i]==QuestHandler.ACTIVE){
                    if(i==5){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals("BONE")){
                                result=after.get(0);
                                player.collect(new Captured_Dog());
                                dialognumber++;
                                captured=true; //variable for when a dog meets the captured criteria
                                return result;
                            }
                        }
                    }
                }
        }
        return result;
                  
    }


    public boolean isCaptured() {//getter method for captured boolean
        return captured;
    }
    
}