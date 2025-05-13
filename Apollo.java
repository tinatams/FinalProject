import java.awt.Graphics2D;
import java.util.ArrayList;

public class Apollo extends NPC{
    public static final String name = "Apollo";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    
    
    public Apollo(int x, int y) {
        
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
    public void draw(Graphics2D g2d){
        super.draw(g2d,name);
    }

    @Override
    public void interact(Player player){
        if(super.getDialogNumber()==0){
            super.setDialogues(check(player).split("/n"));
        }
        super.speak();
        // }
        

    }

    public String check(Player player){
        String result="";

            for(int i=0;i<QuestHandler.states.length;i++){
                if(QuestHandler.states[i]==QuestHandler.ACTIVE){
                    if(i==0){
                        result=before.get(0);
                        player.collect(new ProphecyItem());
                        QuestHandler.states[0]=QuestHandler.COMPLETED;
                        QuestHandler.states[1]=QuestHandler.ACTIVE;
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



    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }
    }
