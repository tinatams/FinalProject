
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

    public GameFrame(String data, int CN){
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();
        cp.setFocusable(true);

        clientNumber = CN;
        String skin = (CN % 2 == 0) ? "Hunter" : "Vill4";
        int x = (CN % 2 == 0) ? 9 : 37;
        int y = (CN % 2 == 0) ? 10 : 11;
 
        selectedPlayer = new Player(skin, x * SCALED, y * SCALED);
        canvas = new GameCanvas(data, selectedPlayer, CN);
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
                selectedPlayer.setDirection(Player.UP);
            }
        };

        AbstractAction DOWN = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                selectedPlayer.setDirection(Player.DOWN);
            }
        };

        AbstractAction RIGHT = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                selectedPlayer.setDirection(Player.RIGHT);
            }
        };

        AbstractAction LEFT = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                selectedPlayer.setDirection(Player.LEFT);
            }
        };


        //MAKE IT SO THAT THEY ARE SEPARATED...
        // INTERACTABLES, COLLECTABLES ...
        AbstractAction Interact = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                selectedPlayer.interact();
            }
        };

        am.put("UP", UP);
        am.put("DOWN", DOWN);
        am.put("RIGHT", RIGHT);
        am.put("LEFT", LEFT);
        am.put("IDLE", IDLE);

        am.put("INT", Interact);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "UP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "LEFT");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "DOWN");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "RIGHT");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0, false), "INT");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "IDLE");
    }
}