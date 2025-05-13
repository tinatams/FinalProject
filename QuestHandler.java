
import java.util.*;

public class QuestHandler{
    // public static ArrayList<Quest> active= new ArrayList<Quest>();
    public static ArrayList<Integer> active_index= new ArrayList<Integer>();
    public static Quest[] quests= new Quest[20];
    // public static int comp=0;//number of completed quests
    // public static int act=1;//number of active quests

    public QuestHandler() {
        quests[0]=new Quest(0, "Talk to Apollo","Starter Quest",null,0);
        quests[1]=new Quest(1, "Find Athena","Give the god of wisdom the prophecy","WOOD",1);
        quests[2]=new Quest(2, "Speak to Artemis","The goddess Artemis should be able to help you!",null,0);
        quests[3]=new Quest(3, "Speak to Hephaestus","Ask him what is needed to build a boat.",null,0);
        quests[4]=new Quest(4, "Get string!","It can be found around the island","ORE",2);
        quests[5]=new Quest(5, "Find my Dogs!","The dogs can be captured with the bone","WOOD",2);
        active_index.add(0);
        
        // update();

    }
    public static void update(){
        // active.removeAll(active);
        // System.out.println(active_index.size());
        // for(int i=0;i<active_index.size();i++){
        //     // System.out.println(quests[active_index.get(i)].getQuestno());
        //     active.add(quests[active_index.get(i)]);
        // }
        
    }
   
   
}