package controller;

import javax.swing.JButton;
import javax.swing.JTextArea;
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

	/**
	 * Constructor for custom width
	 * @param width
	 */
    public RowGameController(int width) {
		this.width = width;
		initState();
    }
	/**
	 * Constructor for default width = 3
	 */
	public RowGameController() {
		this.width = 3;
		initState();
    }

	/**
	 * Encapsulation for gameModel
	 * @return gameModel
	 */
	public RowGameModel getGameModel() {
		return this.gameModel;
	}

	/**
	 * Encapsulation for getGameView 
	 * @return gameView
	 */
	public RowGameGUI getGameView() {
		return this.gameView;
	}

	/**
	 * Function to check for Contents of straight row on the board
	 * @param row width of the game
	 * @return true if characters in a straight row are the same; And false otherwise
	 */
	private boolean straightRow(int row) {
		for (int i = 0; i < this.width - 1; i++) {
			if (!gameModel.blocksData[row][i].getContents()
			.equals(gameModel.blocksData[row][i+1].getContents())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Function to check for Contents of straight column on the board
	 * @param col width of the game
	 * @return true if characters in a straight column are the same; And false otherwise
	 */
	private boolean straightCol(int col) {
		for (int i = 0; i < this.width - 1; i++) {
			if (!gameModel.blocksData[i][col].getContents()
			.equals(gameModel.blocksData[i+1][col].getContents())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Function to check for Contents of straight diagonal from [0,0] to [n,n] on the board
	 * @return true if characters in a straight diagonal are the same; And false otherwise
	 */
	private boolean diag1() {
		for (int i = 0; i < this.width - 1; i++) {
			if (!gameModel.blocksData[i][i].getContents()
			.equals(gameModel.blocksData[i+1][i+1].getContents())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Function to check for Contents of straight diagonal from [0,n] to [n,0] on the board
	 * @return true if characters in a straight diagonal are the same; And false otherwise
	 */
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
	
	/**
	 * Check if CurrentPlayer win if play at [i,j] position
	 * @param i x coordinate
	 * @param j y coordinate
	 * @return true if player win and false otherwise
	 */
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
	
	/**
	 * Helper function to Update block in Board
	 * @param i x coordinate
	 * @param j y coordinate
	 * @param icon icon of current player turn
	 * @param opponent number of next player
	 * @param msg message to report current player turn
	 */
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

	/**
	 * Update block in Board
	 * @param block clicked block
	 * @param msg message to report current player turn
	 * @param icon icon of current player turn
	 * @param opponent number of next player
	 */
	private void update(JButton block, String msg, String icon, String opponent) {
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
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.width; j++) {
				if (block == gameView.getBlocks()[i][j]) {
					if (gameModel.blocksData[i][j].getContents().equals("X")
						|| gameModel.blocksData[i][j].getContents().equals("O")) {
							throw new IllegalArgumentException("Cannot play on this tile.");
					}
				}
			}
		}
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
			update(block, RowGameModel.PLAYER1_WIN, "X", "2");
		} else {
			update(block, RowGameModel.PLAYER2_WIN, "O", "1");
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
