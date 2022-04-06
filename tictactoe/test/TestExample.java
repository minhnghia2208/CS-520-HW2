import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import model.RowGameModel;
import controller.RowGameController;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameController game;

    @Before
    public void setUp() {
	    game = new RowGameController();
    }
    @After
    public void tearDown() {
	    game = null;
    }

    @Test
    public void testNewGame() {
        assertEquals ("1", game.getGameModel().getCurrentPlayer());
        assertEquals (9, game.getGameModel().getMovesLeft());
        assertEquals (RowGameModel.START_TURN, game.getGameView().getPlayerturn().getText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	    RowBlockModel block = new RowBlockModel(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalMove() {
        game.move(game.getGameView().getBlocks()[0][0]);
        assertEquals("X", game.getGameModel().blocksData[0][0].getContents());
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
                if (i != 0 && j != 0) {
                    assertNotEquals("X", game.getGameModel().blocksData[i][j].getContents());
                    assertNotEquals("O", game.getGameModel().blocksData[i][j].getContents());
                }
			}
		}

        // Throw Exception Here
        game.move(game.getGameView().getBlocks()[0][0]); 
        assertEquals("X", game.getGameModel().blocksData[0][0].getContents());
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
                if (i != 0 && j != 0) {
                    assertNotEquals("X", game.getGameModel().blocksData[i][j].getContents());
                    assertNotEquals("O", game.getGameModel().blocksData[i][j].getContents());
                }
			}
		}
    }

    @Test 
    public void legalMove() {
        game.move(game.getGameView().getBlocks()[0][0]);
        assertEquals("X", game.getGameModel().blocksData[0][0].getContents());
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
                if (i != 0 && j != 0) {
                    assertNotEquals("X", game.getGameModel().blocksData[i][j].getContents());
                    assertNotEquals("O", game.getGameModel().blocksData[i][j].getContents());
                }
			}
		}

        game.move(game.getGameView().getBlocks()[1][1]); 
        assertEquals("X", game.getGameModel().blocksData[0][0].getContents());
        assertEquals("O", game.getGameModel().blocksData[1][1].getContents());
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
                if ((i != 0 && j != 0) && (i != 1 && j != 1)) {
                    assertNotEquals("X", game.getGameModel().blocksData[i][j].getContents());
                    assertNotEquals("O", game.getGameModel().blocksData[i][j].getContents());
                }
			}
		}
    }
    
    @Test 
    public void Player1Win() {
        // diagonal 1
        game.move(game.getGameView().getBlocks()[0][0]);
        game.move(game.getGameView().getBlocks()[0][1]);

        game.move(game.getGameView().getBlocks()[1][1]);
        game.move(game.getGameView().getBlocks()[1][2]);

        game.move(game.getGameView().getBlocks()[2][2]);
        assertEquals(RowGameModel.PLAYER1_WIN, game.getGameModel().getFinalResult());

        // diagonal 2
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[2][0]);
        game.move(game.getGameView().getBlocks()[0][1]);

        game.move(game.getGameView().getBlocks()[1][1]);
        game.move(game.getGameView().getBlocks()[1][2]);

        game.move(game.getGameView().getBlocks()[0][2]);
        assertEquals(RowGameModel.PLAYER1_WIN, game.getGameModel().getFinalResult());

        // row 1
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][0]);
        game.move(game.getGameView().getBlocks()[1][1]);

        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[2][2]);

        game.move(game.getGameView().getBlocks()[0][2]);
        assertEquals(RowGameModel.PLAYER1_WIN, game.getGameModel().getFinalResult());

        // row 2
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[1][0]);
        game.move(game.getGameView().getBlocks()[0][0]);

        game.move(game.getGameView().getBlocks()[1][1]);
        game.move(game.getGameView().getBlocks()[2][2]);

        game.move(game.getGameView().getBlocks()[1][2]);
        assertEquals(RowGameModel.PLAYER1_WIN, game.getGameModel().getFinalResult());

        // row 3
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[2][0]);
        game.move(game.getGameView().getBlocks()[0][0]);

        game.move(game.getGameView().getBlocks()[2][1]);
        game.move(game.getGameView().getBlocks()[0][2]);

        game.move(game.getGameView().getBlocks()[2][2]);
        assertEquals(RowGameModel.PLAYER1_WIN, game.getGameModel().getFinalResult());

        // column 1
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][0]);
        game.move(game.getGameView().getBlocks()[0][1]);

        game.move(game.getGameView().getBlocks()[1][0]);
        game.move(game.getGameView().getBlocks()[0][2]);

        game.move(game.getGameView().getBlocks()[2][0]);
        assertEquals(RowGameModel.PLAYER1_WIN, game.getGameModel().getFinalResult());

        // column 2
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[1][2]);

        game.move(game.getGameView().getBlocks()[1][1]);
        game.move(game.getGameView().getBlocks()[0][2]);

        game.move(game.getGameView().getBlocks()[2][1]);
        assertEquals(RowGameModel.PLAYER1_WIN, game.getGameModel().getFinalResult());

        // column 3
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][2]);
        game.move(game.getGameView().getBlocks()[0][1]);

        game.move(game.getGameView().getBlocks()[1][2]);
        game.move(game.getGameView().getBlocks()[1][1]);

        game.move(game.getGameView().getBlocks()[2][2]);
        assertEquals(RowGameModel.PLAYER1_WIN, game.getGameModel().getFinalResult());
    }

    @Test 
    public void Player2Win() {
        // diagonal 1
        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[0][0]);
        
        game.move(game.getGameView().getBlocks()[1][2]);
        game.move(game.getGameView().getBlocks()[1][1]);
        
        game.move(game.getGameView().getBlocks()[1][0]);
        game.move(game.getGameView().getBlocks()[2][2]);
        assertEquals(RowGameModel.PLAYER2_WIN, game.getGameModel().getFinalResult());

        // diagonal 2
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[2][0]);
        
        game.move(game.getGameView().getBlocks()[1][2]);
        game.move(game.getGameView().getBlocks()[1][1]);
        
        game.move(game.getGameView().getBlocks()[2][2]);
        game.move(game.getGameView().getBlocks()[0][2]);
        assertEquals(RowGameModel.PLAYER2_WIN, game.getGameModel().getFinalResult());

        // row 1
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[1][1]);
        game.move(game.getGameView().getBlocks()[0][0]);
        
        game.move(game.getGameView().getBlocks()[2][2]);
        game.move(game.getGameView().getBlocks()[0][1]);
        
        game.move(game.getGameView().getBlocks()[1][0]);
        game.move(game.getGameView().getBlocks()[0][2]);
        assertEquals(RowGameModel.PLAYER2_WIN, game.getGameModel().getFinalResult());

        // row 2
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][0]);
        game.move(game.getGameView().getBlocks()[1][0]);
        
        game.move(game.getGameView().getBlocks()[2][2]);
        game.move(game.getGameView().getBlocks()[1][1]);
        
        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[1][2]);
        assertEquals(RowGameModel.PLAYER2_WIN, game.getGameModel().getFinalResult());

        // row 3
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][0]);
        game.move(game.getGameView().getBlocks()[2][0]);
        
        game.move(game.getGameView().getBlocks()[0][2]);
        game.move(game.getGameView().getBlocks()[2][1]);
        
        game.move(game.getGameView().getBlocks()[1][0]);
        game.move(game.getGameView().getBlocks()[2][2]);
        assertEquals(RowGameModel.PLAYER2_WIN, game.getGameModel().getFinalResult());

        // column 1
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[0][0]);
        
        game.move(game.getGameView().getBlocks()[0][2]);
        game.move(game.getGameView().getBlocks()[1][0]);
        
        game.move(game.getGameView().getBlocks()[1][2]);
        game.move(game.getGameView().getBlocks()[2][0]);
        assertEquals(RowGameModel.PLAYER2_WIN, game.getGameModel().getFinalResult());

        // column 2
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[1][2]);
        game.move(game.getGameView().getBlocks()[0][1]);
        
        game.move(game.getGameView().getBlocks()[0][2]);
        game.move(game.getGameView().getBlocks()[1][1]);
        
        game.move(game.getGameView().getBlocks()[1][0]);
        game.move(game.getGameView().getBlocks()[2][1]);
        assertEquals(RowGameModel.PLAYER2_WIN, game.getGameModel().getFinalResult());

        // column 3
        game = new RowGameController();
        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[0][2]);
        
        game.move(game.getGameView().getBlocks()[1][1]);
        game.move(game.getGameView().getBlocks()[1][2]);
        
        game.move(game.getGameView().getBlocks()[2][0]);
        game.move(game.getGameView().getBlocks()[2][2]);
        assertEquals(RowGameModel.PLAYER2_WIN, game.getGameModel().getFinalResult());
    }

    @Test 
    public void twoPlayerTie() {
        game.move(game.getGameView().getBlocks()[0][0]);
        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[0][2]);
        
        game.move(game.getGameView().getBlocks()[1][0]);
        game.move(game.getGameView().getBlocks()[1][1]);
        game.move(game.getGameView().getBlocks()[2][2]);

        game.move(game.getGameView().getBlocks()[2][1]);
        game.move(game.getGameView().getBlocks()[2][0]);
        game.move(game.getGameView().getBlocks()[1][2]);

        assertEquals(RowGameModel.GAME_END_NOWINNER, game.getGameModel().getFinalResult());
    }

    @Test
    public void reset() {
        game.move(game.getGameView().getBlocks()[0][0]);
        game.move(game.getGameView().getBlocks()[0][1]);
        game.move(game.getGameView().getBlocks()[0][2]);
        
        game.move(game.getGameView().getBlocks()[1][0]);
        game.move(game.getGameView().getBlocks()[1][1]);
        game.move(game.getGameView().getBlocks()[2][2]);

        game.move(game.getGameView().getBlocks()[2][1]);
        game.move(game.getGameView().getBlocks()[2][0]);
        game.move(game.getGameView().getBlocks()[1][2]);

        game.resetGame();
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
                assertNotEquals("X", game.getGameModel().blocksData[i][j].getContents());
                assertNotEquals("O", game.getGameModel().blocksData[i][j].getContents());
			}
		}
        assertEquals (RowGameModel.START_TURN, game.getGameView().getPlayerturn().getText());
    }

    @Test
    public void model() {
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
                assertNotEquals("X", game.getGameModel().blocksData[i][j].getContents());
                assertNotEquals("O", game.getGameModel().blocksData[i][j].getContents());
			}
		}
        assertEquals("1", game.getGameModel().getCurrentPlayer());
        assertEquals(9, game.getGameModel().getMovesLeft());

        game.move(game.getGameView().getBlocks()[0][1]);
        assertEquals("2", game.getGameModel().getCurrentPlayer());
        assertEquals(8, game.getGameModel().getMovesLeft());       
    }

    @Test
    public void view() {
        assertEquals(game.getGameView().getWidth(), game.getGameView().getBlocks().length);
        assertEquals(RowGameModel.START_TURN, game.getGameView().getPlayerturn().getText());

        game.move(game.getGameView().getBlocks()[2][1]);
        assertEquals(RowGameModel.PLAYER2_TURN, game.getGameView().getPlayerturn().getText());

        game.move(game.getGameView().getBlocks()[1][1]);
        assertEquals(RowGameModel.PLAYER1_TURN, game.getGameView().getPlayerturn().getText());

        assertNotNull(game.getGameView().getReset());
        assertNotNull(game.getGameView().getGUI());
    }

    @Test
    public void controller() {
        assertNotNull(game.getGameModel());
        assertNotNull(game.getGameView());
        assertEquals(3, game.getWidth());
    }
}
