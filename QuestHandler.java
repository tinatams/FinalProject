

public class QuestHandler{
    public int[] states= new int[17];
    public Quest[] quests= new Quest[17];
    public static int BEFORE_ASSIGNED=0;
    public static int ACTIVE=1;
    public static int COMPLETED=2;

    public QuestHandler() {
        quests[0]=new Quest(0, "Talk to Apollo","Starter Quest",null,0);
        quests[1]=new Quest(1, "Find Athena","Give the god of wisdom the prophecy","PROPHECY",1);
        quests[2]=new Quest(2, "Speak to Artemis","The goddess Artemis should be able to help you!",null,0);
        quests[3]=new Quest(3, "Speak to Hephaestus","Ask him what is needed to build a boat.",null,0);
        quests[4]=new Quest(4, "Get string!","It can be found around the island","STRING",3);
        quests[5]=new Quest(5, "Find my Dogs!","The dogs can be captured with the bone","CAPTURED_DOG",3);
        quests[6]=new Quest(6, "Collect Iron","Get 3 iron from the mines","IRON",3);
        quests[7]=new Quest(7, "Talk to Poseidon","Find the statue",null,0);
        quests[8]=new Quest(8, "Sacrfice to Poseidon","Get 3 fish to sacrifice","FISH",3);
        quests[9]=new Quest(9, "Sacrifice to Demeter","Talk to the statue of demeter to give the meat","MEAT",1);
        quests[10]=new Quest(10, "Collect Wood","Get 5 wood and give to Hepheastus","WOOD",5);
        quests[11]=new Quest(11, "Encounter the Minotaur","Find the entrance to the labyrinth",null,0);
        quests[12]=new Quest(12, "Talk to Dionysus","Find a way to defeat the minotaur",null,0);
        quests[13]=new Quest(13, "Collect Grapes","Get 3 berries to make wine","GRAPE",3);
        quests[14]=new Quest(14, "Enter the Labyrinth","Knock the Minotaur out","WINE",1);
        quests[15]=new Quest(15, "Get the key","Finish the labarynth (with help from and assist room) then talk to Hepheastus",null,0);
        quests[16]=new Quest(16, "Give the Wings","Hand over the wings to Hephaestus","WING",1);

        states[0]=COMPLETED;
        states[1]=COMPLETED;
        states[2]=COMPLETED;
        states[3]=COMPLETED;
        states[4]=COMPLETED;
        states[5]=COMPLETED;
        states[6]=COMPLETED;
        states[7]=COMPLETED;
        states[8]=COMPLETED;
        states[9]=COMPLETED;
        states[10]=COMPLETED;
        states[11]=ACTIVE;
        states[12]=BEFORE_ASSIGNED;
        states[13]=BEFORE_ASSIGNED;
        states[14]=BEFORE_ASSIGNED;
        states[15]=BEFORE_ASSIGNED;
        states[16]=ACTIVE;
        // update();

    }

    public String gatherData(){
        String result="";
        for(int i=0; i<states.length;i++){

            result=result+Integer.toString(states[i]);
            if(i!=states.length-1){
                result=result+",";
            }
            
        }
        // System.out.println(result);
        return result;
    }

    public void recieveData(String data){
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
            for(int i=0;i<quests.length-1;i++){
                states[i]=Integer.parseInt(quests[i+1]);
            }
            
        }  
    }
   
   
}