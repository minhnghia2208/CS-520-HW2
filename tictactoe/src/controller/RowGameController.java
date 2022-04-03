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
		gameModel = new RowGameModel(this.width);
		gameView = new RowGameGUI(this, this.width);
        for(int row = 0; row < this.width; row++) {
            for(int column = 0; column < this.width; column++) {
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
		for (int i = 0; i < this.width - 1; i++) {
			if (!gameModel.blocksData[row][i].getContents()
			.equals(gameModel.blocksData[row][i+1].getContents())) {
				return false;
			}
		}
		return true;
	}

	private boolean straightCol(int col) {
		for (int i = 0; i < this.width - 1; i++) {
			if (!gameModel.blocksData[i][col].getContents()
			.equals(gameModel.blocksData[i+1][col].getContents())) {
				return false;
			}
		}
		return true;
	}

	private boolean diag1() {
		for (int i = 0; i < this.width - 1; i++) {
			if (!gameModel.blocksData[i][i].getContents()
			.equals(gameModel.blocksData[i+1][i+1].getContents())) {
				return false;
			}
		}
		return true;
	}

	private boolean diag2() {
		for (int i = 0; i < this.width - 1; i++) {
			int len = this.width - 1;
			if (!gameModel.blocksData[i][len-i].getContents()
			.equals(gameModel.blocksData[i+1][len-(i+1)].getContents())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isWin(int i, int j) {
		int div = this.width % 2;
		if (i == j) {
			if (div == 0 || i != div) {
				return straightRow(i) || straightCol(j) || diag1();
			}
			else {
				return straightRow(i) || straightCol(j) || diag1() || diag2();
			}
		}
		else {
			if ((i == 0 && j == this.width-1) || (i == this.width-1 && j == 0)) {
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
		gameModel.setCurrentPlayer(opponent);
		if(gameModel.getMovesLeft() < 7) {
			if(isWin(i, j)) {
				gameModel.setFinalResult(msg);
				endGame();

			} else if(gameModel.getMovesLeft() == 0) {
				gameModel.setFinalResult(RowGameModel.GAME_END_NOWINNER);
			}

			if (gameModel.getFinalResult() != null) {
				JTextArea newPlayerturn = gameView.getPlayerturn();
				newPlayerturn.setText(gameModel.getFinalResult());

				gameView.setPlayerturn(newPlayerturn);
			}
		}
	}
	private void checkWin(JButton block, String msg, String icon, String opponent) {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.width; j++) {
				if (block == gameView.getBlocks()[i][j]) {
					updateState(i, j, icon, opponent, msg);
				}
			}
		}
	}
    /**
     * Moves the current player into the given block.
     *
     * @param block The block to be moved to by the current player
     */
    public void move(JButton block) {
		gameModel.setMovesLeft(gameModel.getMovesLeft() - 1);
		if(gameModel.getMovesLeft() % 2 == 1) {
			JTextArea newPlayerturn = gameView.getPlayerturn();
			newPlayerturn.setText(RowGameModel.PLAYER1_TURN);

			gameView.setPlayerturn(newPlayerturn);
		} else{
			JTextArea newPlayerturn = gameView.getPlayerturn();
			newPlayerturn.setText(RowGameModel.PLAYER2_TURN);

			gameView.setPlayerturn(newPlayerturn);
		}
		
		if(gameModel.getCurrentPlayer().equals("1")) {
			checkWin(block, RowGameModel.PLAYER1_WIN, "X", "2");
		} else {
			checkWin(block, RowGameModel.PLAYER2_WIN, "O", "1");
		}
    }

    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
		for(int row = 0; row < this.width ;row++) {
			for(int column = 0; column < this.width; column++) {
				JButton[][] newBlocks = gameView.getBlocks();
				newBlocks[row][column].setEnabled(false);

				gameView.setBlocks(newBlocks);
			}
		}
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        for(int row = 0;row < this.width; row++) {
            for(int column = 0;column < this.width; column++) {
                gameModel.blocksData[row][column].reset();
				gameModel.blocksData[row][column].setIsLegalMove(true);
				gameView.updateBlock(gameModel,row,column);
            }
        }
        gameModel.setCurrentPlayer("1");
        gameModel.setMovesLeft(this.width * this.width);
		gameModel.setFinalResult(null);
		JTextArea newPlayerturn = gameView.getPlayerturn();
		newPlayerturn.setText("Player 1 to play 'X'");

		gameView.setPlayerturn(newPlayerturn);
    }
}
