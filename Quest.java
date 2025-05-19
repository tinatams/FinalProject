
/**
    Quest Class contains basic quest information as an object. COntains title, description, and completion conditions
 
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
public class Quest{

    private String title;
    private String description;
    private String itemname;
    private int itemnumber;

    public Quest(String title, String description, String itemname, int itemnumber) { //Constructor
        this.description = description;
        this.itemname = itemname;
        this.itemnumber = itemnumber;
        this.title = title;
    }


    public String getTitle() { //getter for title
        return title;
    }

    public String getDescription() { //getter for description
        return description;
    }

    public String getItemname() { //getter for item name
        return itemname;
    }

    public int getItemnumber() { //getter for item number
        return itemnumber;
    }


   
}