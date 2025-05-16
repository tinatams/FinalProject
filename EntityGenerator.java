/**
    This class is a helper class. Creates new instances of entities (Interactable Items, and SuperItems)

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

public class EntityGenerator{

    /**
        Method creates a new instance of an SuperItem, depending on the given itemName

        @param itemName is the name of the item being looked for
        @return insance of item being looked for
    **/
    public SuperItem newItem(String itemName){
        if (itemName.equals(GrapeItem.ITEMNAME)) return new GrapeItem(0,0);
        else if (itemName.equals(IronItem.ITEMNAME)) return new IronItem(0,0);
        else if (itemName.equals(WoodItem.ITEMNAME)) return new WoodItem(0,0);
        else if (itemName.equals(AxeItem.ITEMNAME)) return new AxeItem();
        else if (itemName.equals(PickaxeItem.ITEMNAME)) return new PickaxeItem();
        else if (itemName.equals(BoneItem.ITEMNAME)) return new BoneItem();
        else if (itemName.equals(WineItem.ITEMNAME)) return new WineItem();
        else if (itemName.equals(ProphecyItem.ITEMNAME)) return new ProphecyItem();
        else if (itemName.equals(StringItem.ITEMNAME)) return new StringItem();
        else if (itemName.equals(FishItem.ITEMNAME)) return new FishItem();
        else if (itemName.equals(MeatItem.ITEMNAME)) return new MeatItem();
        else if (itemName.equals(WingItem.ITEMNAME)) return new WingItem();

        return null;
    }

    /**
        Method creates a new instance of an interactable item, depending on the given entityName

        @param entityName is the name of the object being looked for
        @param  x is the items x position
        @param  y is the y position
        @return insance of Interactable item being looked for
    **/
    public Interactable newInteractable(String entityName, int x, int y){
        if (entityName.equals(Tree.ITEMNAME)){
            return new Tree(x, y);
        } else if (entityName.equals(Bush.ITEMNAME)){
            return new Bush(x,y);
        } else if (entityName.equals(Ore.ITEMNAME)){
            return new Ore(x,y);
        } 

        // if anything else needs to be added/ drawn onto the map (eg key or boneitem on the floor etc)
        // could be useful for particles yuh yuh
        
        return null;
    }
}