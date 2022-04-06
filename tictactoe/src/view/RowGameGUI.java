package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import controller.RowGameController;

public class RowGameGUI {
    private int width;
    private JFrame gui = new JFrame("Tic Tac Toe");
    private RowGameModel gameModel = new RowGameModel();
    private JButton[][] blocks;
    private JButton reset = new JButton("Reset");
    private JTextArea playerturn = new JTextArea();

    /**
	 * Getter for width
	 * @return int width
	 */
	public int getWidth() {
		return this.width;
	}
	/**
	 * Setter for width
	 * @param width int
	 */
	public void setWidth(int width) {
		this.width = width;
	}

    public void setPlayerturn(JTextArea playerturn) {
        this.playerturn = playerturn;
    }
    public JTextArea getPlayerturn() {
        return this.playerturn;
    }
    
    public void setReset(JButton reset) {
        this.reset = reset;
    }
    public JButton getReset() {
        return this.reset;
    }

    public void setGameModel(RowGameModel gameModel) {
        this.gameModel = gameModel;
    }
    public RowGameModel getGameModel() {
        return this.gameModel;
    }

    public void setGUI(JFrame gui) {
        this.gui = gui;
    }
    public JFrame getGUI() {
        return this.gui;
    }

    public void setBlocks(JButton[][] blocks) {
        this.blocks = blocks;
    }
    public JButton[][] getBlocks() {
        return this.blocks;
    }

    public void initCompA(RowGameController controller) {
        JPanel gamePanel = new JPanel(new FlowLayout());
        JPanel game = new JPanel(new GridLayout(this.width, this.width));
        gamePanel.add(game, BorderLayout.CENTER);

        gui.add(gamePanel, BorderLayout.NORTH);

        // Initialize a JButton for each cell of the NxN game board.
        for(int row = 0; row < this.width; row++) {
            for(int column = 0; column < this.width; column++) {
                blocks[row][column] = new JButton();
                blocks[row][column].setPreferredSize(new Dimension(75,75));
                game.add(blocks[row][column]);
                blocks[row][column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
			            controller.move((JButton)e.getSource());
                    }
                });
            }
        }
    }

    public void initCompB(RowGameController controller) {
        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);

        gui.add(options, BorderLayout.CENTER);

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        });
    }

    public void initCompC(RowGameController controller) {
        JPanel messages = new JPanel(new FlowLayout());
        messages.setBackground(Color.white);

        gui.add(messages, BorderLayout.SOUTH);

        messages.add(playerturn);
        playerturn.setText(RowGameModel.START_TURN);
    }
    
    public void initGUI(RowGameController controller) {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(500, 350));
        gui.setResizable(true);

        initCompA(controller);
        initCompB(controller);
        initCompC(controller);
    }
    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController controller) {
        this.width = 3;
        blocks = new JButton[this.width][this.width];
        initGUI(controller);
    }

    public RowGameGUI(RowGameController controller, int width) {
        this.width = width;
        blocks = new JButton[this.width][this.width];
        initGUI(controller);
    }

    /**
     * Updates the block at the given row and column 
     * after one of the player's moves.
     *
     * @param gameModel The RowGameModel containing the block
     * @param row The row that contains the block
     * @param column The column that contains the block
     */
    public void updateBlock(RowGameModel gameModel, int row, int column) {
        blocks[row][column].setText(gameModel.blocksData[row][column].getContents());
        blocks[row][column].setEnabled(gameModel.blocksData[row][column].getIsLegalMove());
    }
}
