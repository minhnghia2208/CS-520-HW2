import javax.swing.JFrame;

import controller.RowGameController;

public class RowGameApp 
{
    /**
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
        RowGameController game = new RowGameController();

        JFrame newgui = game.getGameView().getGUI();
        newgui.setVisible(true);
        
        game.getGameView().setGUI(newgui);
    }
}
