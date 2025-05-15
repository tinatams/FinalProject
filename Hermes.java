import java.awt.Graphics2D;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import javax.imageio.plugins.tiff.ExifGPSTagSet;

public class Hermes extends NPC{
    public static final String name = "Hermes";
    private ArrayList<SuperItem> inventory;
    private int user;
    
    private String action = "UPDATE";
    private String playersWith = "ODD";

    private EntityGenerator eg;

    private boolean firstInteraction = true;

    private int x1,y1,x2,y2;
    
    public static final int NO_USER = -1;

    public Hermes(int x, int y, int x_2, int y_2) {
        super("Hermes", x, y);
        x1 = x;
        x2 = x_2;
        y1 = y;
        y2 = y_2;
        inventory = new ArrayList<SuperItem>();

        user = NO_USER;
        eg = new EntityGenerator();
    }

    @Override 
    public void draw(Graphics2D g2d){
        int originalx = getWorldX();
        if (playersWith.equals("EVEN")){
            setWorldX(x2);
            setWorldY(y2);
        } else if (playersWith.equals("ODD")){
            setWorldX(x1);
            setWorldY(y1);
        }

        if (originalx != getWorldX()){
            action = "UPDATE";
            GameFrame.gameState = GameFrame.PLAYING_STATE;
            user = NO_USER;
        }

        super.draw(g2d, name);
    }

    @Override
    public void interact(Player player){
        if (user == NO_USER){
            GameFrame.gameState = GameFrame.HERMES_STATE;
            player.getFrame().getSoundHandler().playEffect(SoundHandler.INV_IN);
            user = player.getCliNum();
        }
    }

    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }

    public void collect(SuperItem item){
        SuperItem itemCollect = getItem(item.getName());
        if (itemCollect != null && itemCollect.isStackable()){
            itemCollect.setAmount(itemCollect.getAmount() + 1);
            System.out.println("collected "+ itemCollect.getName());
        } else {
            if (inventory.size() < 70){
                inventory.add(item);
                item.setOwner(null);
            }
        }
    }

    public void discardItem(SuperItem item){
        SuperItem discardItem = getItem(item.getName());
        if (discardItem != null && discardItem.isStackable()){
            discardItem.setAmount(discardItem.getAmount() - 1);
            if (discardItem.getAmount() <= 0){
                inventory.remove(discardItem);
            }
        } else {
            inventory.remove(discardItem);
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

    public void send(){
        action = "SEND";
    }

    public String getAction(){
        return action;
    }

    @Override 
    public String getName(){
        return "Hermes";
    }

    public void setUser(int cn){
        user = cn;
    }

    public int getUser(){
        return user;
    }

    public String getItemString(){
        String tempString = "";

        if (inventory.size() > 0){
            for(SuperItem item : inventory){
                if (!(item instanceof KeyItem)){
                    tempString += String.format("%s#%d#", item.getName(), item.getAmount());
                } else {
                    tempString += String.format("%s#%d#%s#%s#", item.getName(), item.getAmount(),((KeyItem) item).getLockName(), "NULL");
                }
                    
            }
        } else {
            tempString += "NULL";
        }
        
        return tempString;
    }

    public void recieveData(String data) {
        //System.out.println(data);
        String[] serverData = data.split(",");

        if (!serverData[0].equals("null")){
            if(serverData.length >=3){
                user = Integer.parseInt(serverData[0]);
                playersWith = serverData[1];
                setInventory(serverData[2]);
            }
        } else{
            user = NO_USER;
            playersWith = serverData[1];
        }
    }

    private void setInventory(String inventoryData){
        if (user != GameFrame.getClientNumber() || firstInteraction){
            firstInteraction = false;
            inventory.clear();
            String[] itemData = inventoryData.split("#");
            for (int i = 0; i < itemData.length-1; i+=2){
                String itemName = itemData[i];
                int amount = Integer.parseInt(itemData[i+1]);

                if (!itemName.equals(KeyItem.ITEMNAME) && !itemName.equals(ProphecyItem.ITEMNAME)){
                    inventory.add(eg.newItem(itemName));
                    if(eg.newItem(itemName).isStackable()){
                        inventory.get(inventory.size() - 1).setAmount(amount);
                    }
                } 
                else {
                    if(itemName.equals(KeyItem.ITEMNAME)){
                        KeyItem k = new KeyItem(0,0,itemData[i+2]);
                        k.setAmount(amount);
                        inventory.add(k);
                        i +=2;
                    }  
                    else if(itemName.equals(ProphecyItem.ITEMNAME)){
                        ProphecyItem k = new ProphecyItem();
                        k.setAmount(amount);
                        inventory.add(k);
                    }  
                    
                }
            }
        }   
    }

    public void setAction(String action) {
        this.action = action;
    }
    
}