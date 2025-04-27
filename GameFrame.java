
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

    private KeyHandler keyH;
    private MouseHandler mouseH;

    public static int gameState;
    
    public final static int PLAYING_STATE = 0;
    public final static int INVENTORY_STATE = 1;
    public final static int DIALOG_STATE = 2;
    public final static int HERMES_STATE = 3;

    public final static int START_STATE = 4;
    public final static int CHOOSING_STATE = 5;

    public GameFrame(String data, int CN){
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();
        //cp.setFocusable(true);

        clientNumber = CN;
        String skin = (CN % 2 == 0) ? "Hunter" : "Vill4";
        int x = (CN % 2 == 0) ? 9 : 37;
        int y = (CN % 2 == 0) ? 10 : 11;
 
        selectedPlayer = new Player(skin, x * SCALED, y * SCALED, CN);
        mapH = new MapHandler(selectedPlayer, clientNumber);
        ui = new UIHandler(selectedPlayer, mapH);
        canvas = new GameCanvas(data, selectedPlayer, CN, mapH, ui);
        keyH = new KeyHandler(selectedPlayer, canvas);
        mouseH = new MouseHandler(ui);

        gameState = PLAYING_STATE;
    }

    public void recieveData(String data){
        canvas.recieveData(data);
    }

    public void setUpGUI(){
        cp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setTitle("work please");

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
}