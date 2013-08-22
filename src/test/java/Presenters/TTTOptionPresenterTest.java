package Presenters;

import junit.framework.Assert;
import org.junit.Test;

public class TTTOptionPresenterTest {
    @Test public void createHtmlForTTTOptions() {
        String html = TTTOptionPresenter.generateOptions();
        Assert.assertTrue(html.contains("form"));
        Assert.assertTrue(html.contains("player one"));
        Assert.assertTrue(html.contains("player two"));
    }
}
