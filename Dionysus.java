import java.awt.Graphics2D;
import java.util.ArrayList;

public class Dionysus extends NPC{
    public static final String name = "Dionysus";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean first=true;
    private boolean completed=false;
     private QuestHandler qh=new QuestHandler();
    

    public Dionysus(int x, int y) {
        
        super("Dionysus",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        before.add("What do you want?/n…/nUgh./nFine./nCollect grapes from the bushes and~I’ll make something strong enough~to knock the Minotaur out./n");
        during.add("Finish the task first!/nStop bugging me!");
        after.add("Yeah...Yeah.../nStop pestering me./nHere’s the wine.");
        
        
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
            if(completed){
                super.setDialogues("*grunts*/nGet out".split("/n"));
            }
            else{
                super.setDialogues(check(player).split("/n"));
                player.getFrame().setQuestH(qh);
            }
        }
        
        super.speak();
        // }
        

    }

    public String check(Player player){
        String result="";
        qh=player.getFrame().getQuestH();
        for(int i=0;i<qh.states.length;i++){
                if(qh.states[i]==QuestHandler.ACTIVE){
                    System.out.println(i);
                    if(i<6){
                        result="Stop those damn dogs from~yipping would you?!/nThey’re pissing me off!";
                    }
                    else if(6<i&& i<12){
                        result="Stop bothering me!";
                    }
                    else if(i==12){
                        result=before.get(0);
                        qh.states[12]=QuestHandler.COMPLETED;
                        qh.states[13]=QuestHandler.ACTIVE;
                        return result;
                    }
                    else if(i==13){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);
                                
                                player.collect(new WineItem());
                                System.out.println("WTF");
                                qh.states[13]=QuestHandler.COMPLETED;
                                qh.states[14]=QuestHandler.ACTIVE;
                                completed=true;
                                return result;
                            }
                        }
                    }
                }
        }
        return result;
                  
    }



    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }
    
}