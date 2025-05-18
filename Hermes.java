/**
    This class is a Hermes NPC that the player can interact with in game
    It extends NPC, since it is an item. 
    
    Hermes NPC acts as a messenger between players, kind of like a chest.
    Contains items, and can be sent between 2 islands/ coordinates

	@author Martina Amale M. Llamas (242648); Zoe Angeli G. Uy (246707)
	@version May 19, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

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

    /**
        Draws hermes depending on which players/ which island hermes is on. If hermes updates it sets
        action to 'UPDATE' and exits out of the hermes menu. 
    **/
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

        super.hitBox = new Rectangle(worldX + 10 ,worldY + 20 ,spriteW , spriteH-5);
        super.interactionBox = new Rectangle(worldX - GameFrame.SCALED/2 ,worldY - GameFrame.SCALED/2 , spriteW + GameFrame.SCALED, spriteH + GameFrame.SCALED);

        if (originalx != getWorldX()){
            action = "UPDATE";
            GameFrame.gameState = GameFrame.PLAYING_STATE;
            user = NO_USER;
        }

        super.draw(g2d, name);
    }

    /**
        Method allows Hermes to interact with a player object. 

        If there is no current user, then the Hermes menu opens. 
        sets the user to the players client number. 

        else the NPC speaks.
    **/
    @Override
    public void interact(Player player){
        if (user == NO_USER){
            GameFrame.gameState = GameFrame.HERMES_STATE;
            player.getFrame().getSoundHandler().playEffect(SoundHandler.INV_IN);
            user = player.getCliNum();
        }
    }

    /**
        Gets the inventory of Hermes
        @return inventory
    **/
    public ArrayList<SuperItem> getInventory(){
        return inventory;
    }

    /**
        Method allows hermes to collect an item. 
        If item is stackable, gets item of same type from inventory and adds one to its amount. 
        If the item is not already in inventory or the item is not stackable. Add the item to inventory
        if within the limit. 

        @param item is the item to be collected
    **/
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

    /**
        Removes item from inventory. 

        If item is stackable, gets item of same type from inventory and removes one from its amount, if
        the amount is less than or equal to 0, it removes the item from inventory. 

        If the item is not stackable, it removes the item from inventory. 

        @param item is the item to be collected
    **/
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

    /**
        Gets an item from the inventory, looks through inventory and looks for an item with the same name.

        @param name, is the name that you are looking for 
        @return Item with the same name
    **/
    public SuperItem getItem(String name){
        for(SuperItem item : inventory){
            if ((item.getName()).equals(name)){
                return item;
            }
        }

        return null;
    }

    /**
        Method changes the action of hermes to SEND
    **/
    public void send(){
        action = "SEND";
    }

    /**
        Gets the action of hermes
        @return action
    **/
    public String getAction(){
        return action;
    }

    /**
        Gets the name of Hermes
        @return "Hermes"
    **/
    @Override 
    public String getName(){
        return "Hermes";
    }

    /**
        Sets the user of Hermes
        @param cn is the client number of the current user
    **/
    public void setUser(int cn){
        user = cn;
    }

    /**
        Gets the current user of Hermes
        @return user
    **/
    public int getUser(){
        return user;
    }

    /**
        Gets the contents of Hermes as a string
        For each item it sends the name and the amount. 
        
        If the inventory is empty, sends out "NULL"

        @return the contents of the inventory as a string
    **/
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

    /**
        Recieves hermes inventory data (from server), and uses it to update inventory.
        Process data to make it so that only takes in data if the data is of the right size, and not empty. 

        @param data is the data of hermes inventory from server. 
    **/
    public void recieveData(String data) {
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

    /**
        Method updates the contents of Hermes's inventory, based on data recieved from server. 
        Splits data and adds to inventory using entity generator, and sets amount if necessary.

        @param inventoryData is the data from server to be used to update inventory
    **/
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
}