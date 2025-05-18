/**
    Apollo Class that extends NPC and contains an instances of iventory,Entity Generator, Quest Handler, and the name of the NPC,
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

public class Apollo extends NPC{ 
    public static final String name = "Apollo";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private QuestHandler qh=new QuestHandler();
    
    
    public Apollo(int x, int y) { //Constructor with location the entity should be drawn and dialogue
        
        super("Apollo",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        quests.add(0);
        before.add("Greetings Traveller! Oh who am I kidding./nHey mortal! I’m the god Apollo./nMaybe you’ve heard of me?~God of music, the sun, awesomeness, etc.~etc. /nWhy do you look so confused? /nHaven’t you heard of me before?/n.../nNo you say?~Ok then, I’m having trouble accessing~my godly mojo./nGive me a second before you get~to bask in my brilliance./n.../n*huff*/n.../n*puff*/nIt’s not working. Its not working. WHY!!/nOh…I think I just got a prophecy./nHmm…/nI don’t have the powers to divine it/nI think I see Athena over on the~other island. She’s pretty smart.~Maybe she can make sense of it./nGive her this prophecy mortal. ~She should know what to do");
        during.add("(Hint: You may need to talk to Hermes. ~The messenger god can deliver items~to the other island)");
        after.add("Did you come here to admire my awesomeness? ~You’re welcome to!");

        super.setDialogues(before.get(0).split("/n"));
        
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
        super.speak(); //Changes NPC dialogue
        

    }

    public String check(Player player){ //Method that returns current dialogue according to active quest list and removes inventory items when needed
        String result="";
        qh=player.getFrame().getQuestH();
            for(int i=0;i<qh.states.length;i++){
                if(qh.states[i]==qh.ACTIVE){
                    if(i==0){
                        result=before.get(0);
                        player.collect(new ProphecyItem());
                        qh.states[0]=QuestHandler.COMPLETED;
                        qh.states[1]=QuestHandler.ACTIVE;
                        break;
                    }
                    else if(i==1){
                        result=during.get(0);
                        break;
                    }
                    else{
                        result=after.get(0);
                        break;
                    }
                    }
            }
        return result;
        }


    }
