
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    private Player selectedPlayer;
    private GameCanvas canvas;

    public KeyHandler(Player sp, GameCanvas c){
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





        if(GameFrame.gameState == GameFrame.HERMES_STATE && code == KeyEvent.VK_1){
            if (selectedPlayer.getItem("WOOD") != null){
                selectedPlayer.discardItem(new WoodItem(0,0));
                Hermes hermes = (Hermes) canvas.getMapHandler().getNPC("Hermes");
                hermes.collect(new WoodItem(0,0));
            }
        } else if(GameFrame.gameState == GameFrame.HERMES_STATE && code == KeyEvent.VK_2){
            selectedPlayer.discardItem(new GrapeItem(0,0));
            Hermes hermes = (Hermes) canvas.getMapHandler().getNPC("Hermes");
            hermes.collect(new GrapeItem(0,0));
        } else if (GameFrame.gameState == GameFrame.HERMES_STATE && code == KeyEvent.VK_3){
            Hermes hermes = (Hermes) canvas.getMapHandler().getNPC("Hermes");
            hermes.send();
        } else if (GameFrame.gameState == GameFrame.HERMES_STATE && code == KeyEvent.VK_4){
            KeyItem key = (KeyItem) (selectedPlayer.getNotStackableItem("KEY")).get(0);
            if (key != null){
                selectedPlayer.discardItem(key);
                Hermes hermes = (Hermes) canvas.getMapHandler().getNPC("Hermes");
                hermes.collect(key);
            }
        } else if (GameFrame.gameState == GameFrame.HERMES_STATE && code == KeyEvent.VK_5){
            Hermes hermes = (Hermes) canvas.getMapHandler().getNPC("Hermes");
            KeyItem key = (KeyItem) (hermes.getNotStackableItem("KEY")).get(0);
            if (key != null){
                selectedPlayer.collect(key);
                hermes.discardItem(key);
            }
        } 







        if (code ==  KeyEvent.VK_C){
            if (GameFrame.gameState == GameFrame.HERMES_STATE){
                Hermes hermes = (Hermes) canvas.getMapHandler().getNPC("Hermes");
                hermes.setUser(Hermes.NO_USER);
                GameFrame.gameState = GameFrame.PLAYING_STATE;
            }

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
            if (GameFrame.gameState == GameFrame.PLAYING_STATE || GameFrame.gameState == GameFrame.INVENTORY_STATE)
            GameFrame.gameState = (GameFrame.gameState == GameFrame.PLAYING_STATE) ? GameFrame.INVENTORY_STATE : GameFrame.PLAYING_STATE;
        }

        if (code == KeyEvent.VK_ESCAPE){
            if (GameFrame.gameState == GameFrame.HERMES_STATE){
                Hermes hermes = (Hermes) canvas.getMapHandler().getNPC("Hermes");
                hermes.setUser(Hermes.NO_USER);
            }
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