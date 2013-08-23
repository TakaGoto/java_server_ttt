import Ui.JavaTTTUi;
import com.tictactoe.Board.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavaTTTUiTest {
    JavaTTTUi javaTTTUi = new JavaTTTUi();

    @Test public void testWelcomeMessage() throws Exception {
        javaTTTUi.welcomeMessage();
        String message = javaTTTUi.getMessage();
        assertEquals("Welcome to Tic Tac Toe!", message);
    }

    @Test public void testEndGame() throws Exception {
        javaTTTUi.endGame();
        String message = javaTTTUi.getMessage();
        assertEquals("Thanks for playing! Good bye.", message);
    }

    @Test public void testPrintBoard() throws Exception {
        Board board = new Board(3);
        javaTTTUi.printBoard(board);
        String message = javaTTTUi.getBoard();
        assertEquals("_________", message);
    }

    @Test public void testDisplayResult() throws Exception {
        javaTTTUi.displayResult("tie");
        String message = javaTTTUi.getMessage();
        assertEquals("Game over!  It is a tie<p> play again ? </p><a href='/'>yes</a>", message);
    }

    @Test public void testDisplayResultWinner() {
        javaTTTUi.displayResult("X");
        String message = javaTTTUi.getMessage();
        assertEquals("Game over!  X wins!<p> play again ? </p><a href='/'>yes</a>", message);
    }
}
