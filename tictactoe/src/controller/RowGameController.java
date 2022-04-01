package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import view.RowGameGUI;

public class RowGameController {
    private RowGameModel gameModel;
    private RowGameGUI gameView;
	private int width;

    /**
     * Creates a new game initializing the GUI.
     */
	private void initState() {
		gameModel = new RowGameModel();
		gameView = new RowGameGUI(this);
        for(int row = 0; row < this.width; row++) {
            for(int column = 0; column< this.width;column++) {
				gameModel.blocksData[row][column].setContents("");
				gameModel.blocksData[row][column].setIsLegalMove(true);
				gameView.updateBlock(gameModel,row,column);
            }
        }
	}
    public RowGameController(int width) {
		this.width = width;
		initState();
    }
	public RowGameController() {
		this.width = 3;
		initState();
    }

	public RowGameModel getGameModel() {
		return this.gameModel;
	}

	public RowGameGUI getGameView() {
		return this.gameView;
	}

	private boolean straightRow(int row) {
		for (int i = 0; i < this.width-1; i++) {
			if (!gameModel.blocksData[row][i].getContents()
			.equals(gameModel.blocksData[row][i+1].getContents())) {
				return false;
			}
		}
		return true;
	}

	private boolean straightCol(int col) {
		for (int i = 0; i < this.width-1; i++) {
			if (!gameModel.blocksData[i][col].getContents()
			.equals(gameModel.blocksData[i+1][col].getContents())) {
				return false;
			}
		}
		return true;
	}

	private boolean diag1() {
		for (int i = 0; i < this.width-1; i++) {
			if (!gameModel.blocksData[i][i].getContents()
			.equals(gameModel.blocksData[i+1][i+1].getContents())) {
				return false;
			}
		}
		return true;
	}

	private boolean diag2() {
		for (int i = 0; i < this.width-1; i++) {
			int len = this.width - 1;
			if (!gameModel.blocksData[i][len-i].getContents()
			.equals(gameModel.blocksData[i+1][len-(i+1)].getContents())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isWin(int i, int j) {
		// return diag1();
		int div = this.width % 2;
		if (i == j) {
			if (div == 0 || i != div + 1) {
				return straightRow(i) || straightCol(j) || diag1();
			}
			else {
				return straightRow(i) || straightCol(j) || diag1() || diag2();
			}
		}
		else {
			if ((i == 0 && j == this.width) || (i == this.width || j == 0)) {
				return straightRow(i) || straightCol(j) || diag2();
			}
			else {
				return straightRow(i) || straightCol(j);
			}
		}
	}
	
	private void updateState(int i, int j, String icon, String opponent, String msg) {
		gameModel.blocksData[i][j].setContents(icon);
		gameView.updateBlock(gameModel,i,j);
		gameModel.player = opponent;
		if(gameModel.movesLeft<7) {
			if(isWin(i, j)) {
				gameModel.setFinalResult(msg);
				endGame();

			} else if(gameModel.movesLeft==0) {
				gameModel.setFinalResult(RowGameModel.GAME_END_NOWINNER);
			}

			if (gameModel.getFinalResult() != null) {
				gameView.playerturn.setText(gameModel.getFinalResult());
			}
		}
	}
	private void checkWin(JButton block, String msg, String icon, String opponent) {
		
		if(block==gameView.blocks[0][0]) {
			updateState(0, 0, icon, opponent, msg);
	    } else if(block==gameView.blocks[0][1]) {
			updateState(0, 1, icon, opponent, msg);
	    } else if(block==gameView.blocks[0][2]) {
			updateState(0, 2, icon, opponent, msg);
	    } else if(block==gameView.blocks[1][0]) {
			updateState(1, 0, icon, opponent, msg);
	    } else if(block==gameView.blocks[1][1]) {
			updateState(1, 1, icon, opponent, msg);
	    } else if(block==gameView.blocks[1][2]) {
			updateState(1, 2, icon, opponent, msg);
	    } else if(block==gameView.blocks[2][0]) {
			updateState(2, 0, icon, opponent, msg);
	    } else if(block==gameView.blocks[2][1]) {
			updateState(2, 1, icon, opponent, msg);
	    } else if(block==gameView.blocks[2][2]) {
			updateState(2, 2, icon, opponent, msg);
		}
	}
    /**
     * Moves the current player into the given block.
     *
     * @param block The block to be moved to by the current player
     */
    public void move(JButton block) {
		gameModel.movesLeft--;
		if(gameModel.movesLeft%2 == 1) {
			gameView.playerturn.setText("'X': Player 1");
		} else{
			gameView.playerturn.setText("'O': Player 2");
		}
		
		if(gameModel.player.equals("1")) {
			checkWin(block, "Player 1 wins!", "X", "2");
		} else {
			checkWin(block, "Player 2 wins!", "O", "1");
		}
    }

    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
		for(int row = 0;row<3;row++) {
			for(int column = 0;column<3;column++) {
				gameView.blocks[row][column].setEnabled(false);
			}
		}
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                gameModel.blocksData[row][column].reset();
		gameModel.blocksData[row][column].setIsLegalMove(true);
		gameView.updateBlock(gameModel,row,column);
            }
        }
        gameModel.player = "1";
        gameModel.movesLeft = 9;
		gameModel.setFinalResult(null);
        gameView.playerturn.setText("Player 1 to play 'X'");
    }
}
