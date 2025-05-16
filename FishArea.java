/**
    This class determines an area in which a player can fish.
    It implements interactable interphase as the player can interact with 
    the area to start fishing. 

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
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FishArea implements Interactable{
    public static String ITEMNAME = "FISHAREA"; 

    private int worldX, worldY, width, height;
    private Rectangle bounds;

    /**
        Sets the default values of the area. 

        @param x is the x position of the area
        @param y is the y position of the area
        @param w is how wide the area is
        @param h is how long the area is 

        bounds is a rectangle object that determines the fishing area. 
    **/
    public FishArea(int x, int y, int w, int h){
        worldX = x * GameFrame.SCALED;
        worldY = y * GameFrame.SCALED;
        width = w * GameFrame.SCALED;
        height = h * GameFrame.SCALED;

        bounds = new Rectangle(worldX, worldY, width, height);
    }

    /**
        Method allows the Player to interact with the area. 
        Method schedules a fish to be allowed to get caught
        and changes the state of the game to fishing state

        Plays sound effect that signifies the change in state,
        and the start of fishing
    **/
    @Override
    public void interact(Player player) {
        player.getFrame().getFishy().scheduleFish();
        GameFrame.gameState = GameFrame.FISHING_STATE;
        player.getFrame().getSoundHandler().playEffect(SoundHandler.FISH_IN);
    }

    /**
        Gets the iteraction box of area
        @return bounds
    **/
    @Override
    public Rectangle getInteractionBox() {
        return bounds;
    }

    /**
        draws the area, is empty because the area has no physical form
    **/
    @Override
    public void draw(Graphics2D g2d) {}

    /**
        Gets the left position of area
        @return worldX
    **/
    @Override
    public int getWorldX() {
        return worldX;
    }

    /**
        Gets the top position of the area
        @return worldY
    **/
    @Override
    public int getWorldY() {
        return worldY;
    }

    /**
        Gets the right position of area
        @return width
    **/
    @Override
    public int getSpriteW() {
        return width;
    }

    /**
        Gets the bottom position of area
        @return height
    **/
    @Override
    public int getSpriteH() {
        return height;
    }

    /**
        Gets the iteraction box of area
        @return small rectangle 
    **/
    @Override
    public Rectangle getHitBox() {
        return new Rectangle(-1,-1,0,0);
    }
    
}