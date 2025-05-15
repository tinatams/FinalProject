public class EntityGenerator{

    public SuperItem newItem(String itemName){
        if (itemName.equals(GrapeItem.ITEMNAME)) return new GrapeItem(0,0);
        else if (itemName.equals(IronItem.ITEMNAME)) return new IronItem(0,0);
        else if (itemName.equals(WoodItem.ITEMNAME)) return new WoodItem(0,0);
        else if (itemName.equals(AxeItem.ITEMNAME)) return new AxeItem();
        else if (itemName.equals(PickaxeItem.ITEMNAME)) return new PickaxeItem();
        else if (itemName.equals(BoneItem.ITEMNAME)) return new BoneItem();
        else if (itemName.equals(WineItem.ITEMNAME)) return new WineItem();
        else if (itemName.equals(ProphecyItem.ITEMNAME)) return new ProphecyItem();
        else if (itemName.equals(StringItem.ITEMNAME)) return new StringItem(0,0);
        else if (itemName.equals(FishItem.ITEMNAME)) return new FishItem();
        else if (itemName.equals(MeatItem.ITEMNAME)) return new MeatItem();
        else if (itemName.equals(WingItem.ITEMNAME)) return new WingItem();

        return null;
    }

    public Interactable newInteractable(String entityName, int x, int y){
        if (entityName.equals(Tree.ITEMNAME)){
            return new Tree(x, y);
        } else if (entityName.equals(Bush.ITEMNAME)){
            return new Bush(x,y);
        } else if (entityName.equals(Ore.ITEMNAME)){
            return new Ore(x,y);
        }
        else if (entityName.equals(StringItem.ITEMNAME)){
            return new StringItem(x,y);
        }

        // if anything else needs to be added/ drawn onto the map (eg key or boneitem on the floor etc)
        // could be useful for particles yuh yuh
        
        return null;
    }
}