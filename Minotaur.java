import java.awt.*;
import java.util.ArrayList;

public class Minotaur extends NPC{
    public static final String name = "Minotaur";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean first=true;
    private boolean completed=false;
     private QuestHandler qh=new QuestHandler();
    

    public Minotaur(int x, int y) {
        
        super("Minotaur",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        
        super.hitBox = new Rectangle(x + 5 ,y + 15 ,2*spriteW , (2*spriteH)-5);
        super.interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , 2*spriteW + GameFrame.SCALED, 2*spriteH + GameFrame.SCALED);

        before.add("*ROAR!*/nYou cannot defeat this beast!/nPerhaps you need to subdue it./nLook for the god of wine./nHe may be able to help you!");
        during.add("Hint: The god of wine might be~on the other island");
        after.add("*The Minotaur faints*/nHint: There is an assist room for the~labyrinth in island 1");
        
        
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
                super.setDialogues("*snore*".split("/n"));
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
                    if(i==11){
                        result=before.get(0);
                        first=false;
                        qh.states[11]=QuestHandler.COMPLETED;
                        qh.states[12]=QuestHandler.ACTIVE;
                        qh.states[14]=QuestHandler.ACTIVE;
                        return result;                        
                    }
                    else if(i==14){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);
                                qh.states[14]=QuestHandler.COMPLETED;
                                qh.states[15]=QuestHandler.ACTIVE;
                                player.collect(new KeyItem(1,1,"MINOTAUR"));
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