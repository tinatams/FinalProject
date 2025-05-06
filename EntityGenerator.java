public class EntityGenerator{

    public SuperItem newItem(String itemName){
        switch(itemName){
            case GrapeItem.ITEMNAME: return new GrapeItem(0,0);
            case IronItem.ITEMNAME: return new IronItem(0,0);
            case WoodItem.ITEMNAME: return new WoodItem(0,0);
            case AxeItem.ITEMNAME: return new AxeItem();
            case PickaxeItem.ITEMNAME: return new PickaxeItem();
            case BoneItem.ITEMNAME: return new BoneItem();
        }

        return null;
    }

    public Interactable newInteractable(String entityName){
        case 
    }
    
    public NPC newNPC(String npcName){
        
    }
}