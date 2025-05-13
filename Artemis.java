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

        if(first && dialognumber==0){
            result="Hey! Have you seen my dogs?/n.../nNo?/nOk...";
        }
        
        
        for(int q : quests){
            for(int qh: QuestHandler.active_index){
                if(q==qh){
                    if(first && dialognumber==0){
                        result=before.get(dialognumber);
                        first=false;
                        return result;
                    }
                    else if(first && dialognumber>0){
                        result=before.get(dialognumber);
                        first=false;
                        player.collect(new WoodItem(1,1));
                        player.collect(new WoodItem(1,1));
                        return result;
                    }
                    else{
                        if(qh==2){
                            QuestHandler.active_index.remove(2);
                            QuestHandler.active_index.add(4);
                            QuestHandler.update();
                        }
                        else if(q==4){
                            result=during.get(dialognumber);
                            inventory=player.getInventory();
                            for(int i=0; i<inventory.size();i++){
                                if(inventory.get(i).getName().equals(QuestHandler.quests[qh].getItemname()) && inventory.get(i).getAmount()>=QuestHandler.quests[qh].getItemnumber()){
                                    if(inventory.get(i).getAmount()==QuestHandler.quests[qh].getItemnumber()){ //it's showing still
                                        player.discardItem(player.getInventory().get(i));
                                    }
                                    else{
                                        inventory.get(i).setAmount(inventory.get(i).getAmount()-QuestHandler.quests[qh].getItemnumber());
                                    }
                                    result=after.get(dialognumber);
                                    dialognumber++;
                                    first=true;
                                    if(dialognumber>1){
                                        completed=true;
                                    }
                                    QuestHandler.active_index.remove(4);
                                    QuestHandler.active_index.add(5);
                                    QuestHandler.update();
                                    return result;
                                }
                            }
                        }
                }
                first=false;
            }
            }
        }
        return result;
    }



    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }
    
}