package Presenters;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardPresenterTest {
    String htmlBoard;

    @Before
    public void init() {
        htmlBoard = BoardPresenter.generateBoard("_________");
    }

    @Test public void generateBoardInHtmlHasForm() {
        Assert.assertTrue(htmlBoard.contains("form"));
    }

    @Test public void generateBoardHtmlHasButtons() {
        Assert.assertTrue(htmlBoard.contains("submit"));
    }

    @Test public void generateBoardHtmlHasPlayerMoveName() {
        Assert.assertTrue(htmlBoard.contains("player_move"));
    }

    @Test public void generateBoardHtmlHasFourByFourBoard() {
        Assert.assertTrue(htmlBoard.contains("15"));
    }
}
