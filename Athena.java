import java.awt.Graphics2D;
import java.util.ArrayList;

public class Athena extends NPC{
    public static final String name = "Athena";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean first=true;
    private boolean completed=false;
    
    

    public Athena(int x, int y) {
        
        super("Athena",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        quests.add(1);
        quests.add(2);
        during.add("Please come back later");
        after.add("Apollo asked you to give me this you say?/n(You give her the prophecy)/nWhen faith runs dry and mortals stray,~Olympus shuts its shining way.~Five gods left in mortal dust,~Locked out by doubt, by fading trust./nTo skybound isle their hope is cast—~Go to temple high, call a power vast.~Only through prayer and kindled name~Can the twelve become axios of godly flame./n.../nInteresting./nIt seems that we are trapped here because~mortals have stopped believing in us./nThose who are among mortals remain until ~the gates of Olympus open once more./nWe must get to an island with a temple of~the Twelve…I think I know which one./nMortal, I know your kind do not believe ~in us currently but assist us and~ all of us will reward you. I swear it.");
        before.add("How to help you ask?/nWell from the prophecy we need to find~a way to the island they spoke of. ~We will need a boat./nYou must speak with Hephaestus.~He likes to stick around caves./nI do believe that you will also need to~sacrifice to the goddess Demeter/nThe islands are barren and Hephaestus~will need wood ~to build a vessel./nThe goddess Artemis can help your friend~with that.");
        during.add("(Hint: you might want to check this island~thoroughly for a hidden workshop)/n(hint: your friend might want to check ~thier island for a huntress in green)");
        after.add("Good Job!");
        
        
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
                    if(i==0){
                        result="Hello Mortal! I am the goddess Athena./n Do you need something?/n .../nNo?/nI apologize but I'm a bit busy right now";
                        return result;
                    }
                    else if(i==1){
                        System.out.println("ITs her?");
                        result=during.get(dialognumber);
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
                                result=after.get(dialognumber);
                                dialognumber++;
                                first=true;
                                if(dialognumber>1){
                                    completed=true;
                                }

                                QuestHandler.states[1]=QuestHandler.COMPLETED;
                                QuestHandler.states[2]=QuestHandler.ACTIVE;
                                QuestHandler.states[3]=QuestHandler.ACTIVE;
                                
                                System.out.println("IT SWITCHED");
                                return result;
                            }
                        }
                    }

                    else if(i==2 || i==3){
                        if(first && dialognumber>0){
                            result=before.get(dialognumber-1);
                            System.out.println(i);
                            first=false;
                        }
                        else{
                            result=during.get(dialognumber);
                            System.out.println(i);
                        }
                        return result;
                    }
                    else{
                        result=after.get(dialognumber);
                        first=true;
                        return result;
                    }
                }

                first=false;
            }
        return result;
    }



    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }
    
}