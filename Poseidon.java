import java.awt.*;
import java.util.ArrayList;
import javax.imageio.*;


public class Poseidon extends NPC{
    public static final String name = "Poseidon";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean first=true;
    private boolean completed=false;
    private QuestHandler qh=new QuestHandler();
    

    public Poseidon(int x, int y) {
        super("Poseidon",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();

        super.hitBox = new Rectangle(x + 5 ,y + 15 ,2*spriteW , (2*spriteH)-5);
        super.interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , 2*spriteW + GameFrame.SCALED, 2*spriteH + GameFrame.SCALED);
        
        before.add("Poseidon only accepts fish as sacrifice");
        during.add("Hint: The water nearby can be used to~get fish");
        after.add("FISH SACRIFICED!~May you have safe passage!");
        
        
        this.x = x;
        this.y = y;
    }

    @Override 
    public void draw(Graphics2D g2d){ //Draw method that calls NPC draw method
        super.draw(g2d,name);
    }

    @Override
    public void interact(Player player){ //Method that dictates dialogue when players interact with this NPC
        if(super.getDialogNumber()==0){
            if(completed){
                super.setDialogues("Safe trip!".split("/n"));
            }
            else{
                super.setDialogues(check(player).split("/n"));
                player.getFrame().setQuestH(qh);
            }
        }
        
        super.speak();
    }


    public String check(Player player){
        String result="";
        qh=player.getFrame().getQuestH();
        for(int i=0;i<qh.states.length;i++){
                if(qh.states[i]==QuestHandler.ACTIVE){
                    System.out.println(i);
                    if(i<7){
                        result="It's a statue";
                    }
                    else if(i==7){
                        qh.states[7]=qh.COMPLETED;
                        qh.states[8]=qh.ACTIVE;
                        result=before.get(0);
                        return result;
                    }
                    else if(i==8){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);
                                qh.states[8]=QuestHandler.COMPLETED;
                                qh.states[10]=QuestHandler.ACTIVE;
                                completed=true;
                                Hephaestus.first=true;
                                return result;
                            }
                        }
                    }
                }
        }
        return result;
                  
    }
    
}