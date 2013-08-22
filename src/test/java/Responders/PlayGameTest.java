package Responders;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class PlayGameTest {
    PlayGame playGame = new PlayGame();
    Hashtable req, resp;

    @Test public void retrievesCookies() throws IOException {
        Hashtable body = new Hashtable();
        body.put("playerMove", "5");
        req = new Hashtable();
        req.put("Cookie", "board=_________; playerOne=human; playerTwo=computer; boardSize=3");
        req.put("Method", "POST");
        req.put("Body", body);
        resp = playGame.respond(req);
        String newBody = new String((byte[]) resp.get("message-body"), "UTF-8");
        Assert.assertTrue(newBody.contains("X"));
    }

    @Test public void parseCookies() {
        req = new Hashtable();
        req.put("Cookie", "board=_________; playerOne=human; playerTwo=computer; boardSize=3");
        Hashtable cookies = playGame.parseCookie(req);
        assertEquals("_________", cookies.get("board"));
        assertEquals("human", cookies.get("playerOne"));
        assertEquals("computer", cookies.get("playerTwo"));
        assertEquals("3", cookies.get("boardSize"));
    }
}
