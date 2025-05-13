import java.awt.Graphics2D;
import java.util.ArrayList;

public class Hephaestus extends NPC{
    public static final String name = "Hephaestus";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private boolean completed=false;
    private boolean first=true;
    

    public Hephaestus(int x, int y) {
        
        super("Hephaestus",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        before.add("*cling*/n*clang*/nHmm? Hello kid/nBuild a boat?/nWell you’ll need wood from the other island.~I think I still have a few materials./nHere take this pickaxe,~get me some more iron from the~mines and I’ll forge your friend an ax.");
        during.add("Hint: it may take a few hits to break iron");
        after.add("Here you go kid/nI’d suggest you hand that over to your~friend then come back I need you~to get me more materials!");
        before.add("How to help you ask?/nWell from the prophecy we need to find~a way to the island they spoke of. ~We will need a boat./nYou must speak with Hephaestus.~He likes to stick around caves./nI do believe that you will also need to~sacrifice to the goddess Demeter/nThe islands are barren and Hephaestus~will need wood ~to build a vessel./nThe goddess Artemis can help your friend~with that.");
        during.add("(Hint: you might want to check this island~thoroughly for a hidden workshop)/n(hint: your friend might want to check ~thier island for a huntress in green)");
        after.add("Good Job!");
        // 
        
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
                String result=check(player);
                super.setDialogues(result.split("/n"));
                System.out.println(result);
            }
        }
        
        super.speak();
        // }
        

    }

    public String check(Player player){
        
        String result="";
        
        for(int i=0;i<QuestHandler.states.length;i++){
            System.out.println("HIII");
                if(QuestHandler.states[i]==QuestHandler.ACTIVE){
                    System.out.println(i);
                    if(i<3){
                        result="*cling* *clang*/nI'm busy";
                    }
                    else if(i==3){
                        result=before.get(0);
                        player.collect(new PickaxeItem());
                        QuestHandler.states[3]=QuestHandler.COMPLETED;
                        QuestHandler.states[6]=QuestHandler.ACTIVE;
                        return result;
    
                    }

                    else if(i==6){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(QuestHandler.quests[i].getItemname()) && inventory.get(j).getAmount()>=QuestHandler.quests[i].getItemnumber()){
                                System.out.println("Found");
                                if(inventory.get(j).getAmount()==QuestHandler.quests[i].getItemnumber()){ //it's showing still
                                    player.discardItem(player.getInventory().get(j));
                                }
                                else{
                                    inventory.get(j).setAmount(inventory.get(j).getAmount()-QuestHandler.quests[i].getItemnumber());
                                }
                                result=after.get(0);
                                player.collect(new AxeItem());
                                QuestHandler.states[6]=QuestHandler.COMPLETED;
                                QuestHandler.states[7]=QuestHandler.ACTIVE;
                                first=true;
                                return result;
                            }
                        }
                    }
                    else{
                        result=after.get(1);
                        return result;
                    }
                }
            }
        return result;
    }



    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }
    
}