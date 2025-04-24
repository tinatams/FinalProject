public class Lock extends Teleporter{
    private String lockName;
    private boolean locked;
    
    public Lock(int x, int y, int w, int h, int map, int newX, int newY, String n) {
        super(x, y, w, h, map, newX, newY);
        lockName = n;
        locked = true;
    }

    public String getlockName(){
        return lockName;
    }

    public void setLocked(boolean lock){
        locked = lock;
    }

    public boolean isLocked(){
        return locked;
    }
}