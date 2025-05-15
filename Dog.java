import java.awt.Graphics2D;
import java.util.ArrayList;

public class Dog extends NPC{
    public static final String name = "Dog";
    private ArrayList<SuperItem> inventory;
    private EntityGenerator eg;
    private int x,y;
    private int dialognumber=0;
    private boolean captured=false;
    private QuestHandler qh=new QuestHandler();
    

    public Dog(int x, int y) {
        
        super("Dog",x, y);
        inventory = new ArrayList<SuperItem>();
        eg = new EntityGenerator();
        before.add("*GROWL*");
        during.add("Hint: You need a bone");
        after.add("Woof! Woof!");
        
        
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
                super.setDialogues(check(player).split("/n"));
                player.getFrame().setQuestH(qh);
        }
        
        super.speak();
        // }
        

    }

    public String check(Player player){
        String result="";
        qh=player.getFrame().getQuestH();
        for(int i=0;i<qh.states.length;i++){
                if(qh.states[i]==QuestHandler.ACTIVE){
                    result=before.get(0);
                    
                    if(i==5){
                        result=during.get(0);
                        inventory=player.getInventory();
                        for(int j=0; j<inventory.size();j++){
                            if(inventory.get(j).getName().equals("BONE")){
                                result=after.get(0);
                                player.collect(new Captured_Dog());
                                captured=true;
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

    public boolean isCaptured() {
        return captured;
    }
    
}