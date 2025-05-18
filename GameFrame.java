/**
    The GameFrame class sets up and manages all the main parts of the game,
    like the player, map, canvas, UI, sound, and mini-games. Creates the game window 
    and stores the state that the game is in (like playing, inventory, or dialog).

    This class connects all the visuals, input, and logic parts of the program
    and allows other classes to access them.

	@author Martina Amale M. Llamas (242648); Zoe Angeli G. Uy (246707)
	@version May 19, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import java.awt.*;
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

    private FishMiniGame fishy;
    
    private KeyHandler keyH;
    private MouseHandler mouseH;


    public static int gameState;
    
    public final static int PLAYING_STATE = 0;
    public final static int INVENTORY_STATE = 1;
    public final static int DIALOG_STATE = 2;
    public final static int HERMES_STATE = 3;
    public final static int FISHING_STATE = 4;

    /**
        Constructor that instantiates the default values of the GameFrame

     	@param data is the Server data
        @param CN is the client number
        @param skin is the skin of the player

        Creates instances of all the handlers and minigames
        Handlers: MapHandler, UI, Canvas, Key Handler, Mouse Handler, Sound Handler
        Minigames: fishy (Fishing minigame)

        Instatiates a player passes on a starting position depending on client number,
        and skin from @param skin.
    **/
    public GameFrame(String data, int CN, String skin){
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();  

        clientNumber = CN;
        int x = (CN % 2 == 0) ? 9 : 37;
        int y = (CN % 2 == 0) ? 10 : 11;
 
        selectedPlayer = new Player(skin, x * SCALED, y * SCALED, this);
        mapH = new MapHandler(this);
        fishy = new FishMiniGame(selectedPlayer);
        ui = new UIHandler(this);
        canvas = new GameCanvas(data, this);
        keyH = new KeyHandler(this);
        mouseH = new MouseHandler(this);
        soundHandler = new SoundHandler();
        questH=new QuestHandler();

        gameState = PLAYING_STATE;
    }

    /**
       Recieves data and passes it to the canvas   
    **/
    public void recieveData(String data){
        canvas.recieveData(data);
    }

    /**
       Sets up the Frame/ Graphial User Interface
        --> sets up basic frame components:
                dimentions, title, mouse and key listeners, background color

        Adds the canvas to the frame, and starts the Animation

        sets up default closing operation, and visibility
    **/
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

    /**
        Gets selected player 
        @return selectedPlayer
    **/
    public Player getSelected(){
        return selectedPlayer;
    }

    /**
        Gets the current map
        @return current map index from mapHandler
    **/
    public int getMap(){
        return mapH.getCurrentMap();
    }

    /**
        Gets the map Handler
        @return mapH
    **/
    public MapHandler getMapHandler() {
        return mapH;
    }

    /**
        Gets the client number
        @return clientNumber
    **/
    public static int getClientNumber(){
        return clientNumber;
    }

    /**
        Gets the canvas
        @return canvas 
    **/
    public GameCanvas getCanvas() {
        return canvas;
    }

    /**
        Gets the ui handler
        @return ui
    **/
    public UIHandler getUi() {
        return ui;
    }

    /**
        Gets the Key Handler
        @return keyH
    **/
    public KeyHandler getKeyH() {
        return keyH;
    }

    /**
        Gets the Mouse Handler
        @return mouseH
    **/
    public MouseHandler getMouseH() {
        return mouseH;
    }

    /**
        Gets the Sound Handler
        @return soundHandler
    **/
    public SoundHandler getSoundHandler() {
        return soundHandler;
    }

    public QuestHandler getQuestH() {
        return questH;
    }

    public FishMiniGame getFishy() {
        return fishy;
    }

    public void setQuestH(QuestHandler questH) {
        this.questH = questH;
    }
}