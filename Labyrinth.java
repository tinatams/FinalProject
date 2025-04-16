public class Labyrinth extends Map{
    private String version;
    private int[][] defaultBaseMap;
    private int[][] defaultCollisionMap;

    private int[][] buttonOneBaseMap;
    private int[][] buttonOneCollisionMap;

    private int[][] buttonTwoBaseMap;
    private int[][] buttonTwoCollisionMap;
    
    public Labyrinth() {
        super("labyrinth");
        defaultBaseMap = new int[maxColumn][maxRow];
        defaultCollisionMap = new int[maxColumn][maxRow];
        buttonOneBaseMap = new int[maxColumn][maxRow];
        buttonOneCollisionMap = new int[maxColumn][maxRow];
        buttonTwoBaseMap = new int[maxColumn][maxRow];
        buttonTwoCollisionMap = new int[maxColumn][maxRow];
    }

    @Override
    public void loadMap(){
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

    public void loadNewMap(String newMap){
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

    public String getVersion(){
        return version;
    }
}