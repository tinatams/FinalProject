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

        System.out.println(GameFrame.gameState);
        //INTERACTION KEY
        if (code ==  KeyEvent.VK_C){
            if (GameFrame.gameState == GameFrame.HERMES_STATE){
                Hermes hermes = (Hermes) canvas.getMapHandler().getNPC(Hermes.name);
                hermes.setUser(Hermes.NO_USER);
                GameFrame.gameState = GameFrame.PLAYING_STATE;
                frame.getSoundHandler().playEffect(SoundHandler.INV_OUT);
            }

            NPC currentNPC = selectedPlayer.getNPCinteracting();
            if(currentNPC != null && !currentNPC.getName().equals(Hermes.name)){
                GameFrame.gameState = GameFrame.DIALOG_STATE;
            }

            if(GameFrame.gameState == GameFrame.PLAYING_STATE) selectedPlayer.interact();

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