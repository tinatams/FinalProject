import java.util.ArrayList;

public class Hermes extends NPC{
    private ArrayList<SuperItem> inventory;

    public Hermes(String s, int x, int y, String[] dialogues) {
        super(s, x, y, dialogues);
        inventory = new ArrayList<SuperItem>();
        collect(new WoodItem(0,0));
        collect(new WoodItem(0,0));
        collect(new WoodItem(0,0));
        collect(new IronItem(0,0));
    }

    @Override
    public void interact(Player player){
        GameFrame.gameState = GameFrame.HERMES_STATE;
    }

    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }

        public void collect(SuperItem item){
        SuperItem itemCollect = getItem(item.getName());
        if (itemCollect != null && item.isStackable()){
            itemCollect.setAmount(itemCollect.getAmount() + 1);
        } else {
            inventory.add(item);
            item.setOwner(null);
        }
    }

    public void discardItem(SuperItem item){
        SuperItem discardItem = getItem(item.getName());
        if (discardItem != null && discardItem.isStackable()){
            discardItem.setAmount(discardItem.getAmount() + 1);
        } else {
            inventory.remove(item);
            item.setOwner(null);
        }
    }

    public SuperItem getItem(String name){
        for(SuperItem item : inventory){
            if ((item.getName()).equals(name)){
                return item;
            }
        }

        return null;
    }

    public ArrayList<SuperItem> getNotStackableItem(String name){
        ArrayList<SuperItem> notStackItems = new ArrayList<SuperItem>();
        for(SuperItem item : inventory){
            if ((item.getName()).equals(name)){
                notStackItems.add(item);
            }
        }

        return notStackItems;
    }

    @Override 
    public String getName(){
        return "Hermes";
    }
    
}