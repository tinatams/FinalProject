public class Lock extends Teleporter{
    private String lockName;
    
    public Lock(int x, int y, int w, int h, int map, int newX, int newY, String n) {
        super(x, y, w, h, map, newX, newY);
        lockName = n;
    }

    public String getlockName(){
        return lockName;
    }
}