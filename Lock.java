public class Lock extends Teleporter{
    private String lockName;
    private boolean locked;
    
    public Lock(int x, int y, int w, int h, int map, int newX, int newY, int direction, String n) {
        super(x, y, w, h, map, newX, newY, direction);
        lockName = n;
        locked = true;
    }

    /**
        Gets the name of the lock
        @return lockName
    **/
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