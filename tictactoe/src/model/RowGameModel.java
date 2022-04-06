package model;


public class RowGameModel 
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";
    public static final String START_TURN = "Player 1 to play 'X'";
    public static final String PLAYER1_TURN = "'X': Player 1";
    public static final String PLAYER2_TURN = "'O': Player 2";
    public static final String PLAYER1_WIN = "Player 1 wins!";
    public static final String PLAYER2_WIN = "Player 2 wins!";

    public RowBlockModel[][] blocksData;
    private int width;

    /**
     * The current player taking their turn
     */
    private String player = "1";
    private int movesLeft;

    private String finalResult = null;

    private void init() {
        blocksData = new RowBlockModel[this.width][this.width];
        movesLeft = this.width * this.width;

        for (int row = 0; row < this.width; row++) {
            for (int col = 0; col < this.width; col++) {
            blocksData[row][col] = new RowBlockModel(this);
            } // end for col
        } // end for row
    }

    /**
     * Constructor with custom width
     * @param width
     */
    public RowGameModel(int width) {
        super();
        this.width = width;
        init();
    }

    /**
     * Constructor with default width = 3
     */
    public RowGameModel() {
        super();
        this.width = 3;
        init();
    }

    /**
     * Getter for player object
     * @return string: Player object
     */
    public String getCurrentPlayer() {
        return this.player;
    }

    /**
     * Setter for player object
     * @param player String
     */
    public void setCurrentPlayer(String player) {
        this.player = player;
    }

    /**
     * getter for movesLeft Object
     * @return int: movesLeft object
     */
    public int getMovesLeft() {
        return this.movesLeft;
    }

    /**
     * setter for movesLeft Object
     * @param movesLeft
     */
    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    /**
     * getter for finalResult Object
     * @return
     */
    public String getFinalResult() {
	    return this.finalResult;
    }

    /**
     * Setter for finalResult Object
     * @param finalResult
     */
    public void setFinalResult(String finalResult) {
	    this.finalResult = finalResult;
    }
}
