import java.awt.*;
import java.util.ArrayList;

public class Demeter extends NPC{
    public static final String name = "Demeter";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean first=true;
    private boolean completed=false;
    private QuestHandler qh=new QuestHandler();
    

    public Demeter(int x, int y) {
        
        super("Demeter",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        
        super.hitBox = new Rectangle(x , y + 15 ,2*spriteW , (2*spriteH)-5);
        super.interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , 2*spriteW + GameFrame.SCALED, 2*spriteH + GameFrame.SCALED);

        before.add("Demeter only accepts meat as sacrifice");
        during.add("Hint: Artemis should be able to help!");
        after.add("SACRIFICED ACCEPTED!~Spring has come!~Let the world bloom!");
        
        
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
                super.setDialogues("The world is no longer barren!".split("/n"));
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
                    if(i<6){
                        result="It's a statue";
                    }
                    else if(i==6){
                        qh.states[6]=qh.COMPLETED;
                        qh.states[9]=qh.ACTIVE;
                        result=before.get(0);
                    }
                    else if(i==9){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals(qh.quests[i].getItemname()) && inventory.get(j).getAmount()>=qh.quests[i].getItemnumber()){
                                for(int inv=0; inv<qh.quests[i].getItemnumber();inv++){
                                    player.discardItem(player.getInventory().get(j));
                                }
                                result=after.get(0);
                                qh.states[9]=QuestHandler.COMPLETED;
                                qh.states[10]=QuestHandler.ACTIVE;
                                MapHandler.setDemeter_sacrifice(true);
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