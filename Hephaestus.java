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
    

    public Hephaestus(int x, int y) {
        
        super("Hephaestus",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        before.add("*cling*/n*clang*/nHmm? Hello kid/nBuild a boat?/nWell you’ll need wood from the other island.~I think I still have a few materials./nHere take this pickaxe,~get me some more iron from the~mines and I’ll forge your friend an ax.");
        during.add("Hint: it may take a few hits to break iron");
        after.add("Here you go kid/nI’d suggest you hand that over to your~friend then come back I need you~to get me more materials!");
        before.add("*grunts*/n Hey kid./nIf we’re travelling by ship we need~to give an offering for safe travel./nYou’ll need to go to sacrifice fish to poseidon.~I think his statue should be near the entrance to the mines./n");
        during.add("Hint: you might wanna talk to a statue");
        // after.add("Nice work kid. Get the other materials now");
        before.add("Nice work kid.~Get the other materials now/n You need to give me the wood so I can start building");
        during.add("You don't got any wood yet");
        after.add("Nice work kid.~I need one last thing");
        before.add("It’s in Daedalus’s workshop on the other island.~He always keeps his workshop locked./nThe key is at the center of the labyrinth.~The entrance to the labyrinth should~be somewhere in these mines");
        during.add("*cling* *clang*/nYou need to get the item first kid");
        after.add("Good Job kid. Give me a second then we can go");

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
            System.out.println("HIII");
                if(qh.states[i]==qh.ACTIVE){
                    System.out.println(i);
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
                                System.out.println("Found");
                                if(inventory.get(j).getAmount()==qh.quests[i].getItemnumber()){ //it's showing still
                                    player.discardItem(player.getInventory().get(j));
                                }
                                else{
                                    inventory.get(j).setAmount(inventory.get(j).getAmount()-qh.quests[i].getItemnumber());
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
                    else if(i==7 || i==8){
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
                                System.out.println("Found");
                                if(inventory.get(j).getAmount()==qh.quests[i].getItemnumber()){ //it's showing still
                                    player.discardItem(player.getInventory().get(j));
                                }
                                else{
                                    inventory.get(j).setAmount(inventory.get(j).getAmount()-qh.quests[i].getItemnumber());
                                }
                                result=after.get(1);
                                qh.states[10]=QuestHandler.COMPLETED;
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
                            return result;
                        }
                        result=during.get(3);
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                System.out.println("Found");
                                if(inventory.get(j).getAmount()==qh.quests[i].getItemnumber()){ //it's showing still
                                    player.discardItem(player.getInventory().get(j));
                                }
                                else{
                                    inventory.get(j).setAmount(inventory.get(j).getAmount()-qh.quests[i].getItemnumber());
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
                    else{
                        result=after.get(2);
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