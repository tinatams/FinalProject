
import java.awt.Color;
import java.awt.event.*;
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

    private int gameState;
    
    public final static int PLAYING_STATE = 0;
    public final static int INVENTORY_STATE = 1;

    public GameFrame(String data, int CN){
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();
        cp.setFocusable(true);

        clientNumber = CN;
        String skin = (CN % 2 == 0) ? "Hunter" : "Vill4";
        int x = (CN % 2 == 0) ? 9 : 37;
        int y = (CN % 2 == 0) ? 10 : 11;
 
        selectedPlayer = new Player(skin, x * SCALED, y * SCALED);
        mapH = new MapHandler(selectedPlayer);
        canvas = new GameCanvas(data, selectedPlayer, CN, mapH);

        gameState = PLAYING_STATE;
    }

    public void recieveData(String data){
        canvas.recieveData(data);
    }

    public void setUpGUI(){
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("work please");

        cp.setBackground(new Color(20, 28, 22));
        cp.add(canvas);
        canvas.startAnimation();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }   

    public Player getSelectedSq(){
        return selectedPlayer;
    }

    public int getMap(){
        return canvas.getCurrentMap();
    }

    public void addKeyBindings(){
        ActionMap am = cp.getActionMap();
        InputMap im = cp.getInputMap();

        AbstractAction IDLE = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                selectedPlayer.setDirection(Player.IDLE);
            }
        };

        AbstractAction UP = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(gameState == PLAYING_STATE) selectedPlayer.setDirection(Player.UP);
            }
        };

        AbstractAction DOWN = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(gameState == PLAYING_STATE) selectedPlayer.setDirection(Player.DOWN);
            }
        };

        AbstractAction RIGHT = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(gameState == PLAYING_STATE) selectedPlayer.setDirection(Player.RIGHT);
            }
        };

        AbstractAction LEFT = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(gameState == PLAYING_STATE) selectedPlayer.setDirection(Player.LEFT);
            }
        };

        AbstractAction Interact = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(gameState == PLAYING_STATE) selectedPlayer.interact();
            }
        };

        AbstractAction Inventory = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                gameState = (gameState == PLAYING_STATE) ? INVENTORY_STATE : PLAYING_STATE;
                canvas.setGameState(gameState);
            }
        };

        am.put("UP", UP);
        am.put("DOWN", DOWN);
        am.put("RIGHT", RIGHT);
        am.put("LEFT", LEFT);
        am.put("IDLE", IDLE);

        am.put("INT", Interact);
        am.put("INV", Inventory);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "UP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "LEFT");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "DOWN");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "RIGHT");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0, false), "INT");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, false), "INV");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "IDLE");
    }
}