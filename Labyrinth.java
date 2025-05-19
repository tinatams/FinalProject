/**
    Labyrinth Class that extends Map and determines what map is to be drawn depending on which button is pressed.
    It contains the necessary arrays to determine this and has a method to return the current version of the map active.
 
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



public class Labyrinth extends Map{
    private String version;
    private int[][] defaultBaseMap;
    private int[][] defaultCollisionMap;

    private int[][] buttonOneBaseMap;
    private int[][] buttonOneCollisionMap;

    private int[][] buttonTwoBaseMap;
    private int[][] buttonTwoCollisionMap;

    /**
    Constructor for the labyrinth 
    **/
    
    public Labyrinth() { //
        super("labyrinth");
        defaultBaseMap = new int[maxColumn][maxRow];
        defaultCollisionMap = new int[maxColumn][maxRow];
        buttonOneBaseMap = new int[maxColumn][maxRow];
        buttonOneCollisionMap = new int[maxColumn][maxRow];
        buttonTwoBaseMap = new int[maxColumn][maxRow];
        buttonTwoCollisionMap = new int[maxColumn][maxRow];
    }

    @Override
    public void loadMap(){ //Loads default base of map, decoration, collisions, and buttons
        load(decoTileMap, "deco");
        load(collidablesMap, "collidables");
        
        load(defaultCollisionMap, "default/collisions");
        load(defaultBaseMap, "default/base");
      
        load(buttonOneCollisionMap, "button_one/collisions");
        load(buttonOneBaseMap, "button_one/base");

        load(buttonTwoCollisionMap, "button_two/collisions");
        load(buttonTwoBaseMap, "button_two/base");

        baseTileMap = defaultBaseMap;
        collisionMap = defaultCollisionMap;
    }

    public void loadNewMap(String newMap){ //Loads new map, decoration, collisions, and buttons based on what button is pressed
        version = newMap;

        switch (version){
            case "button_one":
                baseTileMap = buttonOneBaseMap;
                collisionMap = buttonOneCollisionMap;
                break;
            case "button_two":
                baseTileMap = buttonTwoBaseMap;
                collisionMap = buttonTwoCollisionMap;
                break;
            default:
                baseTileMap = defaultBaseMap;
                collisionMap = defaultCollisionMap;
        }
    }

    public String getVersion(){ //returns what version of the map is currently being drawn
        return version;
    }
}