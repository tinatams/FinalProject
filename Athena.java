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
        before.add("How to help you ask?/nWell from the prophecy we need to find~a way to the island they spoke of. We will need a boat./nYou must speak with Hephaestus.~He likes to stick around caves./nI do believe that you will also need to~sacrifice to the goddess Demeter/nThe islands are barren and Hephaestus~will need wood ~to build a vessel./nThe goddess Artemis can help your friend~with that.");
        during.add("(Hint: you might want to check this island~thoroughly for a hidden workshop)/n(hint: your friend might want to check thier ~island for a huntress in green)");
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
                super.setDialogues(check(player).split("/n"));
            }
        }
        
        super.speak();
        // }
        

    }

    public String check(Player player){
        
        System.out.println(dialognumber);
        String result="";

        if(first && dialognumber==0){
            result="Hello Mortal! I am the goddess Athena./n Do you need something?/n .../nNo?/nI apologize but I'm a bit busy right now";
        }
        
        for(int q : quests){
            for(Quest qh: QuestHandler.active){
                if(q==qh.getQuestno()){
                    if(first && dialognumber>0){
                        result=before.get(dialognumber-1);
                        first=false;
                        return result;
                    }
                    else{
                        result=during.get(dialognumber);
                        inventory=player.getInventory();
                        for(int i=0; i<inventory.size();i++){
                            System.out.println("HIII It checked the inventory");
                            if(inventory.get(i).getName().equals(qh.getItemname()) && inventory.get(i).getAmount()>=qh.getItemnumber()){
                                if(inventory.get(i).getAmount()==qh.getItemnumber()){ //it's showing still
                                    player.discardItem(player.getInventory().get(i));
                                }
                                else{
                                    inventory.get(i).setAmount(inventory.get(i).getAmount()-qh.getItemnumber());
                                }
                                System.out.println("It found something");
                                result=after.get(dialognumber);
                                dialognumber++;
                                first=true;
                                if(dialognumber>1){
                                    completed=true;
                                }
                                QuestHandler.comp=QuestHandler.comp+1;
                                QuestHandler.update();
                                return result;
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


    // public void discardItem(SuperItem item){
    //     SuperItem discardItem = getItem(item.getName());
    //     if (discardItem != null && discardItem.isStackable()){
    //         discardItem.setAmount(discardItem.getAmount() - 1);
    //         if (discardItem.getAmount() <= 0){
    //             inventory.remove(discardItem);
    //         }
    //     } else {
    //         inventory.remove(discardItem);
    //         item.setOwner(null);
    //     }
    // }

    // public SuperItem getItem(String name){
    //     for(SuperItem item : inventory){
    //         if ((item.getName()).equals(name)){
    //             return item;
    //         }
    //     }

    //     return null;
    // }

    // public ArrayList<SuperItem> getNotStackableItem(String name){
    //     ArrayList<SuperItem> notStackItems = new ArrayList<SuperItem>();
    //     for(SuperItem item : inventory){
    //         if ((item.getName()).equals(name)){
    //             notStackItems.add(item);
    //         }
    //     }

    //     return notStackItems;
    // }


    // @Override 
    // public String getName(){
    //     return "Hermes";
    // }


    // public void recieveData(String data) {
    //     //System.out.println(data);
    //     String[] serverData = data.split(",");

    //     if (!serverData[0].equals("null")){
    //         if(serverData.length >=3){
    //             user = Integer.parseInt(serverData[0]);
    //             playersWith = serverData[1];
    //             setInventory(serverData[2]);
    //         }
    //     } else{
    //         user = NO_USER;
    //         playersWith = serverData[1];
    //     }
    // }

    // private void setInventory(String inventoryData){
    //     if (user != GameFrame.getClientNumber() || firstInteraction){
    //         firstInteraction = false;
    //         inventory.clear();
    //         String[] itemData = inventoryData.split("#");
    //         for (int i = 0; i < itemData.length-1; i+=2){
    //             String itemName = itemData[i];
    //             int amount = Integer.parseInt(itemData[i+1]);

    //             if (!itemName.equals(KeyItem.ITEMNAME)){
    //                 inventory.add(eg.newItem(itemName));
    //                 if(eg.newItem(itemName).isStackable()){
    //                     inventory.get(inventory.size() - 1).setAmount(amount);
    //                 }
    //             } else {
    //                 KeyItem k = new KeyItem(0,0,itemData[i+2]);
    //                 k.setAmount(amount);
    //                 inventory.add(k);
    //                 i +=2;
    //             }
    //         }
    //     }   
    // }

    // public void setAction(String action) {
    //     this.action = action;
    // }
    
}