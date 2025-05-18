
/**
    QuestHandler Class contains an array of quests and the state of these quests. It also sends and receives data from the server.
 
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

public class QuestHandler{
    public int[] states= new int[17];
    public Quest[] quests= new Quest[17];
    public static int BEFORE_ASSIGNED=0;
    public static int ACTIVE=1;
    public static int COMPLETED=2;

    public QuestHandler() { //Constructor to initialize the quests and their initial states
        quests[0]=new Quest("Talk to Apollo","Starter Quest",null,0);
        quests[1]=new Quest("Find Athena","Give the god of wisdom the prophecy","PROPHECY",1);
        quests[2]=new Quest("Speak to Artemis","The goddess Artemis should be able to help you!",null,0);
        quests[3]=new Quest("Speak to Hephaestus","Ask him what is needed to build a boat.",null,0);
        quests[4]=new Quest("Get string!","It can be found around the island","STRING",3);
        quests[5]=new Quest("Find my Dogs!","The dogs can be captured with the bone","CAPTURED_DOG",3);
        quests[6]=new Quest("Collect Iron","Get 3 iron from the mines","IRON",3);
        quests[7]=new Quest("Talk to Poseidon","Find the statue",null,0);
        quests[8]=new Quest("Sacrfice to Poseidon","Get 3 fish to sacrifice","FISH",3);
        quests[9]=new Quest("Sacrifice to Demeter","Talk to the statue of demeter to give the meat","MEAT",1);
        quests[10]=new Quest("Collect Wood","Get 5 wood and give to Hepheastus","WOOD",5);
        quests[11]=new Quest("Encounter the Minotaur","Find the entrance to the labyrinth",null,0);
        quests[12]=new Quest("Talk to Dionysus","Find a way to defeat the minotaur",null,0);
        quests[13]=new Quest("Collect Grapes","Get 3 berries to make wine","GRAPE",3);
        quests[14]=new Quest("Enter the Labyrinth","Knock the Minotaur out","WINE",1);
        quests[15]=new Quest("Get the key","Finish the labarynth (with help from and assist room) then talk to Hepheastus",null,0);
        quests[16]=new Quest("Give the Wings","Hand over the wings to Hephaestus","WINGS",1);

        states[0]=ACTIVE;
        states[1]=BEFORE_ASSIGNED;
        states[2]=BEFORE_ASSIGNED;
        states[3]=BEFORE_ASSIGNED;
        states[4]=BEFORE_ASSIGNED;
        states[5]=BEFORE_ASSIGNED;
        states[6]=BEFORE_ASSIGNED;
        states[7]=BEFORE_ASSIGNED;
        states[8]=BEFORE_ASSIGNED;
        states[9]=BEFORE_ASSIGNED;
        states[10]=BEFORE_ASSIGNED;
        states[11]=BEFORE_ASSIGNED;
        states[12]=BEFORE_ASSIGNED;
        states[13]=BEFORE_ASSIGNED;
        states[14]=BEFORE_ASSIGNED;
        states[15]=BEFORE_ASSIGNED;
        states[16]=BEFORE_ASSIGNED;
        // update();

    }

    public String gatherData(){ //returns a string of the states of each quest
        String result="";
        
        for(int i=0; i<states.length;i++){
            result=result+Integer.toString(states[i]);
            if(i!=states.length-1){
                result=result+",";
            }
            
        }
        return result;
    }

    public void recieveData(String data){ //reads data from server and decides whether it's recent data or old and whether or not to update the states
        
        String[] quests = data.split(",");

        int sumcurrent = 0;
        
        for (int i = 1; i < quests.length; i++) {
            sumcurrent += Integer.parseInt(quests[i]);
            
        }

        int sumpast = 0;
        for (int i = 0; i < states.length; i++) {
            sumpast += states[i];
        }

        if (sumcurrent > sumpast) {
            for(int i=1;i<18;i++){
                states[i-1]=Integer.parseInt(quests[i]);
            }
        } 

    }
   
   
}