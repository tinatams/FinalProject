
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

public class GameFrame{
    public static final int WIDTH = 1024; // 21 tiles
    public static final int HEIGHT = 768; // 16 tiles.
    
    public static final int PIXELRATIO = 16;
    public static final int SCALER = 3;
    public static final int SCALED = PIXELRATIO * SCALER;
    
    private static int clientNumber;

    private JFrame frame;
    private JPanel cp;
    private GameCanvas canvas;
    private String serverData;
    private Player selectedPlayer;
    private MapHandler mapH;
    private UIHandler ui; 
    private SoundHandler soundHandler;
    private QuestHandler questH;
    
    private KeyHandler keyH;
    private MouseHandler mouseH;


    public static int gameState;
    
    public final static int PLAYING_STATE = 0;
    public final static int INVENTORY_STATE = 1;
    public final static int DIALOG_STATE = 2;
    public final static int HERMES_STATE = 3;

    public GameFrame(String data, int CN){
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();  

        clientNumber = CN;
        String skin = (CN % 2 == 0) ? "Hunter" : "Villager4";
        int x = (CN % 2 == 0) ? 9 : 37;
        int y = (CN % 2 == 0) ? 10 : 11;
 
        selectedPlayer = new Player(skin, x * SCALED, y * SCALED, this);
        mapH = new MapHandler(this);
        ui = new UIHandler(this);
        canvas = new GameCanvas(data, this);
        keyH = new KeyHandler(this);
        mouseH = new MouseHandler(this);
        soundHandler = new SoundHandler();
        questH=new QuestHandler();

        gameState = PLAYING_STATE;
    }

    public GameFrame(String data, int CN, String skin){
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();  

        clientNumber = CN;
        int x = (CN % 2 == 0) ? 9 : 37;
        int y = (CN % 2 == 0) ? 10 : 11;
 
        selectedPlayer = new Player(skin, x * SCALED, y * SCALED, this);
        mapH = new MapHandler(this);
        ui = new UIHandler(this);
        canvas = new GameCanvas(data, this);
        keyH = new KeyHandler(this);
        mouseH = new MouseHandler(this);
        soundHandler = new SoundHandler();
        questH=new QuestHandler();

        gameState = PLAYING_STATE;
    }

    public void recieveData(String data){
        canvas.recieveData(data);
    }

    public void setUpGUI(){
        cp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setTitle("Axios: Path of the Worthy");

        cp.setFocusable(true);
        cp.addKeyListener(keyH);
        cp.addMouseListener(mouseH);
        cp.addMouseMotionListener(mouseH);

        cp.setBackground(new Color(20, 28, 22));
        cp.add(canvas);
        canvas.startAnimation();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }   

    public Player getSelected(){
        return selectedPlayer;
    }

    public int getMap(){
        return canvas.getCurrentMap();
    }
    
    public MapHandler getMapHandler() {
        return mapH;
    }

    public static int getClientNumber(){
        return clientNumber;
    }

    public GameCanvas getCanvas() {
        return canvas;
    }

    public UIHandler getUi() {
        return ui;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public MouseHandler getMouseH() {
        return mouseH;
    }

    public SoundHandler getSoundHandler() {
        return soundHandler;
    }

    public QuestHandler getQuestH() {
        return questH;
    }
}