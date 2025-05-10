
import java.util.*;

public class QuestHandler{
    public static ArrayList<Quest> active= new ArrayList<Quest>();
    public static ArrayList<Quest> completed=new ArrayList<Quest>();
    public static ArrayList<Quest> pending=new ArrayList<Quest>();
    public static Quest[] quests= new Quest[20];
    public static int comp=0;//number of completed quests
    public static int act=1;//number of active quests

    public QuestHandler() {
        quests[0]=new Quest(0, "Talk to Apollo","Starter Quest",null,0);
        quests[1]=new Quest(1, "HIIII","Give the god of wisdom the prophecy","WOOD",1);
        quests[2]=new Quest(2, "HIIII","Give the god of wisdom the prophecy","WOOD",3);
        quests[3]=new Quest(3, "HIIII","Give the god of wisdom the prophecy","WOOD",3);
        
        update();

    }
    public static void update(){
        System.out.print(comp);
        active.removeAll(active);
        pending.removeAll(pending);
        completed.removeAll(completed);
        int amount=0;
        int index=0;
        amount=comp;
        while(amount>0){
            completed.add(quests[index]);
            index++;
            amount--;
        }
        amount=act;
        while(amount>0){
            active.add(quests[index]);
            index++;
            amount--;
        }
        while(index<quests.length){
            pending.add(quests[index]);
            index++;
        }
    }

    public QuestHandler(ArrayList<Quest> active,ArrayList<Quest> completed,ArrayList<Quest> pending) {
        this.active=active;
        this.completed=completed;
        this.pending=pending;

    }
   
   
}