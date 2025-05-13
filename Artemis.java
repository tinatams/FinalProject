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
    
    

    public Artemis(int x, int y) {
        
        super("Artemis",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        quests.add(2);
        quests.add(4);
        quests.add(5);
        before.add("Athena you say?/n Sacrifice to Demeter?/n Gather meat?/n I would love to help!/n But you see I’ve broken my bow.~Please get me string around the island!");
        during.add("Hint: String can be found around the island");
        after.add("Good Job! But now I think my dogs might be gone...");
        before.add("Could you find my dogs?/nHere you go!~You can find them using this bone");
        during.add("Hint: My dogs like to wander around.~They can also swim");
        after.add("Thank you!");
        
        
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
                super.setDialogues("Please stay steadfast! Our plan will work.".split("/n"));
            }
            else{
                super.setDialogues(check(player).split("/n"));
            }
        }
        
        super.speak();
        // }
        

    }

    public String check(Player player){
        String result="";
        
        for(int i=0;i<QuestHandler.states.length;i++){
                if(QuestHandler.states[i]==QuestHandler.ACTIVE){
                    if(i<2){
                        result="Hey! Have you seen my dogs?/n.../nNo?/nOk...";
                        return result;
                    }
                    else if(i==2){
                        QuestHandler.states[2]=QuestHandler.COMPLETED;
                        QuestHandler.states[4]=QuestHandler.ACTIVE;
                        result=before.get(0);
                        return result;
                    }
                    else if(i==4){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(QuestHandler.quests[i].getItemname()) && inventory.get(j).getAmount()>=QuestHandler.quests[i].getItemnumber()){
                                if(inventory.get(j).getAmount()==QuestHandler.quests[i].getItemnumber()){ //it's showing still
                                    player.discardItem(player.getInventory().get(j));
                                }
                                else{
                                    inventory.get(j).setAmount(inventory.get(j).getAmount()-QuestHandler.quests[i].getItemnumber());
                                }
                                result=after.get(0);
                                QuestHandler.states[4]=QuestHandler.COMPLETED;
                                QuestHandler.states[5]=QuestHandler.ACTIVE;
                                return result;
                            }
                        }
                    }
                    else if(i==5){
                        result=during.get(1);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(QuestHandler.quests[i].getItemname()) && inventory.get(j).getAmount()>=QuestHandler.quests[i].getItemnumber()){
                                if(inventory.get(j).getAmount()==QuestHandler.quests[i].getItemnumber()){ //it's showing still
                                    player.discardItem(player.getInventory().get(j));
                                }
                                else{
                                    inventory.get(j).setAmount(inventory.get(j).getAmount()-QuestHandler.quests[i].getItemnumber());
                                }
                                result=after.get(1);
                                completed=true;
                                QuestHandler.states[5]=QuestHandler.COMPLETED;
                                QuestHandler.states[6]=QuestHandler.ACTIVE;
                                return result;
                            }
                        }
                    }
                    else{
                        result=after.get(1);
                    }
                    first=false;
                
                }
        }
        return result;
                  
    }



    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }
    
}