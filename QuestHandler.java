

public class QuestHandler{
    public static int[] states= new int[8];
    public static Quest[] quests= new Quest[8];
    public static int BEFORE_ASSIGNED=0;
    public static int ACTIVE=1;
    public static int COMPLETED=2;

    public QuestHandler() {
        quests[0]=new Quest(0, "Talk to Apollo","Starter Quest",null,0);
        quests[1]=new Quest(1, "Find Athena","Give the god of wisdom the prophecy","PROPHECY",1);
        quests[2]=new Quest(2, "Speak to Artemis","The goddess Artemis should be able to help you!",null,0);
        quests[3]=new Quest(3, "Speak to Hephaestus","Ask him what is needed to build a boat.",null,0);
        quests[4]=new Quest(4, "Get string!","It can be found around the island","WOOD",2);
        quests[5]=new Quest(5, "Find my Dogs!","The dogs can be captured with the bone","WOOD",2);
        quests[6]=new Quest(6, "Collect Iron","Get 5 iron from the mines","IRON",5);
        quests[7]=new Quest(7, "Collect Iron","Get 5 iron from the mines","IRON",5);

        states[0]=ACTIVE;
        states[1]=BEFORE_ASSIGNED;
        states[2]=BEFORE_ASSIGNED;
        states[3]=BEFORE_ASSIGNED;
        states[4]=BEFORE_ASSIGNED;
        states[5]=BEFORE_ASSIGNED;
        states[6]=BEFORE_ASSIGNED;
        states[7]=BEFORE_ASSIGNED;
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
   
   
}