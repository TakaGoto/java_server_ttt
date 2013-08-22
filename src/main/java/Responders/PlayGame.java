package Responders;

import Presenters.BoardPresenter;
import Ui.JavaTTTUi;
import com.server.Handlers.Responder;
import com.server.Responses.ResponseStatusLine;
import com.tictactoe.Board.Board;
import com.tictactoe.Game;

import java.nio.charset.Charset;
import java.util.Hashtable;

public class PlayGame implements Responder {
    private JavaTTTUi javaTTTUi = new JavaTTTUi();
    private Hashtable resp = new Hashtable();
    private Hashtable req = new Hashtable();
    private Hashtable header = new Hashtable();

    public Hashtable respond(Hashtable req) {
        this.req = req;

        header.put("Content-Type", "text/html");
        header.put("Connection", "close");

        if(req.get("Method").equals("POST"))
            doPost();

        return resp;
    }

    private void doPost() {
        Hashtable settings = parseCookie(req);
        Hashtable params = (Hashtable) req.get("Body");
        Board board = Game.playGame(javaTTTUi, settings, (String) params.get("playerMove"));
        String body = BoardPresenter.generateBoard(board.getSlots());
        resp.put("message-body", body.getBytes(Charset.forName("utf-8")));
        resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));
    }

    public Hashtable parseCookie(Hashtable req) {
        Hashtable parsedCookies = new Hashtable();

        String reqCookie = (String) req.get("Cookie");
        String[] cookies = reqCookie.split("; ");
        for(String eachCookie: cookies) {
            String[] theCookie = eachCookie.split("=");
            parsedCookies.put(theCookie[0], theCookie[1]);
        }

        return parsedCookies;
    }
}
