public class Labyrinth extends Map{
    private String version;
    
    public Labyrinth() {
        super("labyrinth");
    }

    @Override
    public void loadMap(){
        loadNewMap("default");
    }

    public void loadNewMap(String newMap){
        version = newMap;

        load(decoTileMap, String.format("%s/deco",newMap));
        load(collisionMap, String.format("%s/collisions",newMap));
        load(collidablesMap, String.format("%s/collidables",newMap));
        load(baseTileMap, String.format("%s/base",newMap));
        
    }

    public String getVersion(){
        return version;
    }
}