/**
    This class extends Map, because it is a Map class. Assist one is one of 
    the Map classes, the player can control the layout of the labyrinth through
    the buttons. 
 
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

public class AssistOne extends Map{
    private boolean buttonOnePress;
    private boolean buttonTwoPress;
    
    /**
        sets the name of the map to "assist1", and sets the default values of the buttons to false
    **/
    public AssistOne(){
        super("assist1");
        buttonOnePress = false;
        buttonTwoPress = false;

    }
    
    /**
        loads/ instantiates the buttons for the map
    **/
    @Override
    protected void loadInteract(){
        interacts = super.interacts;
        interacts.add(new ButtonItem(9,5));

        interacts.add(new ButtonItem(12,5));
    }

    /** 
		Gets the version of map depending on what button is being pressed.
		@return the current health
	**/
    public String getVersion() {
        if(((ButtonItem) interacts.get(0)).isPressed()) return "button_one";
        else if (((ButtonItem) interacts.get(1)).isPressed()) return "button_two";
        else return "default";
    }
}