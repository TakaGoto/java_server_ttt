package Presenters;

import junit.framework.Assert;
import org.junit.Test;

public class GamePresenterTest {

    @Test public void displaysUIMessage() {
        String htmlMessage = GamePresenter.generateMessage("Welcome to Tic Tac Toe!");
        Assert.assertTrue(htmlMessage.contains("Welcome to Tic Tac Toe!"));
    }
}
