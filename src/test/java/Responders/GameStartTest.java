package Responders;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameStartTest {
    Hashtable req, resp, headers, body;
    List<String> cookies = new ArrayList<String>();
    GameStart gameStart;

    @Before public void init() {
        gameStart = new GameStart();
        req = new Hashtable();
        body = new Hashtable();
        headers = new Hashtable();
    }

    @Test public void storesOptionsIntoACookie() {
        body.put("playerOne", "human");
        body.put("playerTwo", "computer");
        body.put("boardSize",  "3");
        req.put("Body", body);
        req.put("Method", "POST");
        resp = gameStart.respond(req);
        Hashtable header = (Hashtable) resp.get("message-header");
        cookies = (List<String>) header.get("Set-Cookie");
        assertEquals(4, cookies.size());
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
        cookies.add("playerOne=human; path=/");
        cookies.add("playerTwo=computer; path=/");
        cookies.add("board=_________; path=/");
        cookies.add("boardSize=3; path=/");
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

    @Test public void gameStartGetStoresCookies() {
        req.put("Method", "GET");
        req.put("HTTP-Version", "HTTP/1.0");
        cookies.add("playerOne=human; path=/");
        cookies.add("playerTwo=computer; path=/");
        cookies.add("board=_________; path=/");
        cookies.add("boardSize=3; path=/");
        gameStart.cookies = cookies;
        resp = gameStart.respond(req);
        List<String> cookies = gameStart.cookies;
        assertEquals("board=_________; path=/player_move", cookies.get(0));
        assertEquals("playerOne=human; path=/player_move", cookies.get(1));
        assertEquals("playerTwo=computer; path=/player_move", cookies.get(2));
        assertEquals("boardSize=3; path=/player_move", cookies.get(3));
    }
}
