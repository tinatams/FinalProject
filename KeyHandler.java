/**
    This class handles keyboard inputs for player movements and interacting with the 
    game. The class listens for key events and updates game states or triggers actions.

    Actions include: player movement, player/ object/ entity interactions, etc.

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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    private Player selectedPlayer;
    private GameCanvas canvas;
    private GameFrame frame;

    /**
       Sets up the key handler components, derived from the provided Game Frame. 
       @param f is the frame where we get the canvs and selected player from. 
    **/
    public KeyHandler(GameFrame f){
        frame = f;
        selectedPlayer = frame.getSelected();
        canvas = frame.getCanvas();
    }

    /**
        Called when a key is typed (pressed and released).
        No implementations for current game, game mainly uses keyPressed and keyReleased
    **/
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
        Called when a key is pressed. Triggers actions depending on game state and key being pressed

        If Playing State:
            W : player starts moving up
            A : player starts moving left
            S : player starts moving down
            D : player starts moving right
            C : player interacts with another entity

        If Hermes state C key exits out of the state and goes to playing state
        If Dialogue state C key moves to the next set of dialogue, or exits out if end of interaction
        If Fishing state C key initiates an attempt to catch a fish.
    **/
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (GameFrame.gameState == GameFrame.INSTRUCTIONS){
            GameFrame.gameState = GameFrame.PLAYING_STATE;
        }

        //PLAYER MOVEMENT
        if (GameFrame.gameState == GameFrame.PLAYING_STATE){
            switch (code){
                case KeyEvent.VK_W:
                    selectedPlayer.setDirection(Player.UP);
                    break;
                case KeyEvent.VK_A:
                    selectedPlayer.setDirection(Player.LEFT);
                    break;
                case KeyEvent.VK_S:
                    selectedPlayer.setDirection(Player.DOWN);
                    break;
                case KeyEvent.VK_D:
                    selectedPlayer.setDirection(Player.RIGHT);
                    break;
            }
        } 

        //INTERACTION KEY
        if (code ==  KeyEvent.VK_C){
            
            NPC currentNPC = selectedPlayer.getNPCinteracting();
            if(currentNPC != null){
                if (!(currentNPC instanceof Hermes))
                GameFrame.gameState = GameFrame.DIALOG_STATE;
            }

            if(GameFrame.gameState == GameFrame.PLAYING_STATE)selectedPlayer.interact();

            else if (GameFrame.gameState == GameFrame.HERMES_STATE){
                Hermes hermes = (Hermes) canvas.getMapHandler().getNPC(Hermes.name);
                hermes.setUser(Hermes.NO_USER);
                GameFrame.gameState = GameFrame.PLAYING_STATE;
                frame.getSoundHandler().playEffect(SoundHandler.INV_OUT);
            }


            else if(GameFrame.gameState == GameFrame.DIALOG_STATE){
                if(currentNPC.getDialogNumber() > currentNPC.getDialogueSize()){
                    currentNPC.setDialogNumber(0);
                    GameFrame.gameState = GameFrame.PLAYING_STATE;
                }
                else{
                    selectedPlayer.interact();
                    currentNPC.setDialogNumber(currentNPC.getDialogNumber()+1);
                }
            }

            else if (GameFrame.gameState == GameFrame.FISHING_STATE){
                frame.getFishy().catchFish();
            }
        }

        //INVENTORY KEY
        if (code == KeyEvent.VK_E){
            if (GameFrame.gameState == GameFrame.PLAYING_STATE || GameFrame.gameState == GameFrame.INVENTORY_STATE){

                if (GameFrame.gameState == GameFrame.PLAYING_STATE){
                    GameFrame.gameState = GameFrame.INVENTORY_STATE;
                    frame.getSoundHandler().playEffect(SoundHandler.INV_IN);
                } else {
                    GameFrame.gameState = GameFrame.PLAYING_STATE;
                    frame.getSoundHandler().playEffect(SoundHandler.INV_OUT);
                }
            }
            
        }

        //RESET / ESCAPE FROM OTHER STATES
        if (code == KeyEvent.VK_ESCAPE){
            if (GameFrame.gameState == GameFrame.HERMES_STATE){
                Hermes hermes = (Hermes) canvas.getMapHandler().getNPC(Hermes.name);
                hermes.setUser(Hermes.NO_USER);
            }
            GameFrame.gameState = GameFrame.PLAYING_STATE;
            frame.getSoundHandler().playEffect(SoundHandler.INV_OUT);
        }
    }

    /**
        Called when a key is released. 
        When W, A, S, or D, keys are released. Makes player stop moving. 
    **/
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W ||
            code == KeyEvent.VK_A ||
            code == KeyEvent.VK_S ||
            code == KeyEvent.VK_D 
        ) selectedPlayer.setDirection(Player.IDLE); 

    }

}