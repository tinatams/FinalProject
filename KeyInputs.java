
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputs implements KeyListener{
    private Player selectedPlayer;
    private GameCanvas canvas;

    public KeyInputs(Player sp, GameCanvas c){
        selectedPlayer = sp;
        canvas = c;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

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

        if (code ==  KeyEvent.VK_C){
            NPC currentNPC = selectedPlayer.getNPCinteracting();
            if(currentNPC != null){
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
        }

        if (code == KeyEvent.VK_E){
            GameFrame.gameState = (GameFrame.gameState == GameFrame.PLAYING_STATE) ? GameFrame.INVENTORY_STATE : GameFrame.PLAYING_STATE;
        }

        if (code == KeyEvent.VK_ESCAPE){
            GameFrame.gameState = GameFrame.PLAYING_STATE;
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