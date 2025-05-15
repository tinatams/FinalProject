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

    @Override 
    public void draw(Graphics2D g2d){
        super.draw(g2d,name);
    }

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
        // }
        

    }

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



    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }
    
}