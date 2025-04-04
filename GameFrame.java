
import java.awt.event.*;
import javax.swing.*;

public class GameFrame{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    
    public static final int PIXELRATIO = 16;
    public static final int SCALED = PIXELRATIO * 3;
    

    private JFrame frame;
    private JPanel cp;
    private GameCanvas canvas;
    private String serverData;
    private Player selectedPlayer;

    public GameFrame(String data){
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();
        cp.setFocusable(true);

        selectedPlayer = new Player("CamGreen", 9*SCALED, 6*SCALED);
        canvas = new GameCanvas(data, selectedPlayer);
    }

    public void recieveData(String data){
        canvas.recieveData(data);
    }

    public void setClientNumber(int n){
        canvas.setClientNumber(n);
    }

    public void setUpGUI(){
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("work please");

        cp.add(canvas);
        canvas.startAnimation();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }   

    public Player getSelectedSq(){
        return selectedPlayer;
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

        AbstractAction changeMap = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                canvas.setMap("other");
            }
        };

        am.put("UP", UP);
        am.put("DOWN", DOWN);
        am.put("RIGHT", RIGHT);
        am.put("LEFT", LEFT);
        am.put("IDLE", IDLE);

        am.put("OTHER", changeMap);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "UP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "LEFT");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "DOWN");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "RIGHT");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "IDLE");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "IDLE");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0, false), "OTHER");
    }
}