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
    public RowGameModel gameModel;
    public RowGameGUI gameView;

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController() {
	gameModel = new RowGameModel();
	gameView = new RowGameGUI(this);

        for(int row = 0; row<3; row++) {
            for(int column = 0; column<3 ;column++) {
				gameModel.blocksData[row][column].setContents("");
				gameModel.blocksData[row][column].setIsLegalMove(true);
				gameView.updateBlock(gameModel,row,column);
            }
        }
    }

	private boolean straightRow0() {
		return gameModel.blocksData[0][0].getContents().equals(gameModel.blocksData[0][1].getContents()) &&
			gameModel.blocksData[0][1].getContents().equals(gameModel.blocksData[0][2].getContents());
	}
	private boolean straightRow1() {
		return gameModel.blocksData[1][2].getContents().equals(gameModel.blocksData[1][1].getContents()) &&
			gameModel.blocksData[1][1].getContents().equals(gameModel.blocksData[1][0].getContents());
	}
	private boolean straightRow2() {
		return gameModel.blocksData[2][0].getContents().equals(gameModel.blocksData[2][1].getContents()) &&
			gameModel.blocksData[2][1].getContents().equals(gameModel.blocksData[2][2].getContents());
	}

	private boolean straightCol0() {
		return gameModel.blocksData[0][0].getContents().equals(gameModel.blocksData[1][0].getContents()) &&
			gameModel.blocksData[1][0].getContents().equals(gameModel.blocksData[2][0].getContents());
	}
	private boolean straightCol1() {
		return 	gameModel.blocksData[0][1].getContents().equals(gameModel.blocksData[1][1].getContents()) &&
			gameModel.blocksData[1][1].getContents().equals(gameModel.blocksData[2][1].getContents());
	}
	private boolean straightCol2() {
		return gameModel.blocksData[2][2].getContents().equals(gameModel.blocksData[1][2].getContents()) &&
			gameModel.blocksData[1][2].getContents().equals(gameModel.blocksData[0][2].getContents());
	}

	private boolean diag03() {
		return gameModel.blocksData[0][0].getContents().equals(gameModel.blocksData[1][1].getContents()) &&
			gameModel.blocksData[1][1].getContents().equals(gameModel.blocksData[2][2].getContents());
	}
	private boolean diag30() {
		return gameModel.blocksData[0][2].getContents().equals(gameModel.blocksData[1][1].getContents()) &&
			gameModel.blocksData[1][1].getContents().equals(gameModel.blocksData[2][0].getContents());
	}
	
	private boolean isWin(int i, int j) {
		switch (i) {
			case 0:
				switch (j) {
					case 0: return isWin00();
					case 1: return isWin01();
					case 2: return isWin02();
					default: break;
				}
			case 1:
				switch (j) {
					case 0: return isWin10();
					case 1: return isWin11();
					case 2: return isWin12();
					default: break;
				}
			case 2:
				switch (j) {
					case 0: return isWin20();
					case 1: return isWin21();
					case 2: return isWin22();
					default: break;
				}
			default: 
				return false;
		}
	}
	private boolean isWin00() {
		return straightRow0() || straightCol0() || diag03();
	} 

	private boolean isWin01() {
		return straightRow0() || straightCol1();
	}

	private boolean isWin02() {
		return straightRow0() || straightCol2() || diag30();
	}

	private boolean isWin10() {
		return straightRow1() || straightCol0();
	}

	private boolean isWin11() {
		return straightRow1() || straightCol1() || diag03()|| diag30();
	}

	private boolean isWin12() {
		return straightRow1() || straightCol2();
	}

	private boolean isWin20() {
		return straightRow2() || straightCol0() || diag30();
	}

	private boolean isWin21() {
		return straightRow2() || straightCol1();
	}

	private boolean isWin22() {
		return straightRow2() || straightCol2() || diag03();
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
