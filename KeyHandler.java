
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    private Player selectedPlayer;
    private GameCanvas canvas;
    private GameFrame frame;

    public KeyHandler(GameFrame f){
        frame = f;
        selectedPlayer = frame.getSelected();
        canvas = frame.getCanvas();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();

    }

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
    }
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