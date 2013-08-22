package Responders;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class GameStartTest {
    Hashtable req;
    Hashtable resp;
    GameStart gameStart;
    String cookies;

    @Before public void init() {
        gameStart = new GameStart();
        req = new Hashtable();
        cookies = "board=_________; playerOne=human; playerTwo=computer; boardSize=3";
    }

    @Test public void storesOptionsIntoACookie() {
        Hashtable body = new Hashtable();
        body.put("playerOne", "human");
        body.put("playerTwo", "computer");
        body.put("boardSize",  "3");
        req.put("Body", body);
        req.put("Method", "POST");
        resp = gameStart.respond(req);
        assertEquals(cookies, resp.get("Set-Cookie"));
    }

    @Test public void redirectsToTTTOptionWithGetMethodIfNoCookies() {
        req.put("Method", "GET");
        req.put("Host", "http://localhost:5000/");
        req.put("HTTP-Version", "HTTP/1.0");
        resp = gameStart.respond(req);
        Hashtable header = (Hashtable) resp.get("message-header");
        assertEquals("http://localhost:5000/", header.get("Location"));
        assertEquals("HTTP/1.0 301 Moved Permanently", resp.get("status-line"));
    }

    @Test public void createsAGameAfterSettings() throws IOException {
        req.put("Method", "GET");
        req.put("HTTP-Version", "HTTP/1.0");
        gameStart.cookies = cookies;
        resp = gameStart.respond(req);
        String body = new String((byte[]) resp.get("message-body"), "UTF-8");
        Hashtable header = (Hashtable) resp.get("message-header");
        Assert.assertTrue(body.contains("_"));
        Assert.assertTrue(body.contains("form"));
        Assert.assertTrue(body.contains("player_move"));
        Assert.assertTrue(body.contains("submit"));
        assertEquals("HTTP/1.0 200 OK", resp.get("status-line"));
        assertEquals("close", header.get("Connection"));
    }
}
