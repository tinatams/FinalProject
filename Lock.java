/**
    Lock Class that extends Teleporter. It recieves the dimensions of the teleporter then determines whether it 
    can be used by the player depending on whether the lock is locked or not(whether the player has the appropriate key)
 
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


public class Lock extends Teleporter{
    private String lockName;
    private boolean locked;
    /**
    Constructor
    **/
    public Lock(int x, int y, int w, int h, int map, int newX, int newY, int direction, String n) { 
        super(x, y, w, h, map, newX, newY, direction);
        lockName = n;
        locked = true;
    }
    /**
    returns the name of the lock and the key needed to open it 
    **/
    public String getlockName(){ 
        return lockName;
    }
    /**
    setter for the state of the lock
    **/
    public void setLocked(boolean lock){ 
        locked = lock;
    }
    /**
    returns the state of the lock
    **/
    public boolean isLocked(){ 
        return locked;
    }
}