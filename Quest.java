public class Quest{
    private int questno;
    private String title;
    private String description;
    private String itemname;
    private int itemnumber;

    public Quest( int questno, String title, String description, String itemname, int itemnumber) {
        this.description = description;
        this.itemname = itemname;
        this.itemnumber = itemnumber;
        this.questno = questno;
        this.title = title;
    }

    public int getQuestno() {
        return questno;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getItemname() {
        return itemname;
    }

    public int getItemnumber() {
        return itemnumber;
    }


   
}