public class EntityGenerator{

    public SuperItem newItem(String itemName){
        if (itemName.equals(GrapeItem.ITEMNAME)) return new GrapeItem(0,0);
        else if (itemName.equals(IronItem.ITEMNAME)) return new IronItem(0,0);
        else if (itemName.equals(WoodItem.ITEMNAME)) return new WoodItem(0,0);
        else if (itemName.equals(AxeItem.ITEMNAME)) return new AxeItem();
        else if (itemName.equals(PickaxeItem.ITEMNAME)) return new PickaxeItem();
        else if (itemName.equals(BoneItem.ITEMNAME)) return new BoneItem();
        else if (itemName.equals(ProphecyItem.ITEMNAME)) return new ProphecyItem();
        

        return null;
    }
}