
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

public class GameFrame{
    public static final int WIDTH = 1024; // 21 tiles
    public static final int HEIGHT = 768; // 16 tiles.
    
    public static final int PIXELRATIO = 16;
    public static final int SCALER = 3;
    public static final int SCALED = PIXELRATIO * SCALER;
    
    private int clientNumber;

    private JFrame frame;
    private JPanel cp;
    private GameCanvas canvas;
    private String serverData;
    private Player selectedPlayer;
    private MapHandler mapH;
    private UIHandler ui; 

    private KeyInputs keyInputs;

    public static int gameState;
    
    public final static int PLAYING_STATE = 0;
    public final static int INVENTORY_STATE = 1;
    public final static int DIALOG_STATE = 2;
    public final static int HERMES_STATE = 3;

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
        keyInputs = new KeyInputs(selectedPlayer, canvas);

        gameState = PLAYING_STATE;
    }

    public void recieveData(String data){
        canvas.recieveData(data);
    }

    public void setUpGUI(){
        cp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setTitle("work please");
        frame.addKeyListener(keyInputs);

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
}