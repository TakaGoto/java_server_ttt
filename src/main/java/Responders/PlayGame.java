package Responders;

import Presenters.BoardPresenter;
import Presenters.GamePresenter;
import Ui.JavaTTTUi;
import com.server.Handlers.Responder;
import com.server.Responses.ResponseStatusLine;
import com.tictactoe.Board.Board;
import com.tictactoe.Game;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class PlayGame implements Responder {
    private Hashtable resp = new Hashtable();
    private Hashtable req = new Hashtable();
    private Hashtable header = new Hashtable();
    protected List<String> cookies = new ArrayList<String>();

    public Hashtable respond(Hashtable req) {
        this.req = req;

        header.put("Content-Type", "text/html");
        header.put("Connection", "close");

        if(req.get("Method").equals("POST"))
            doPost();
        else if(req.get("Method").equals("GET")) {
            doGet();
        }

        header.put("Set-Cookie", cookies);
        resp.put("message-header", header);

        return resp;
    }

    private void doGet() {
        JavaTTTUi javaTTTUi = new JavaTTTUi();
        Hashtable settings = parseCookie(req);
        Board board = Game.playGame(javaTTTUi, settings, "");
        String body = GamePresenter.generateMessage(javaTTTUi.getMessage());
        body += BoardPresenter.generateBoard(board.getSlots());
        resp.put("message-body", body.getBytes(Charset.forName("utf-8")));
        resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));

        storeAllCookies(settings, board);
    }

    private void doPost() {
        JavaTTTUi javaTTTUi = new JavaTTTUi();
        Hashtable settings = parseCookie(req);
        Hashtable params = (Hashtable) req.get("Body");
        Board board = Game.playGame(javaTTTUi, settings, (String) params.get("playerMove"));
        resp.put("message-body", "".getBytes(Charset.forName("utf-8")));
        header.put("Location", "http://" + req.get("Host") + "/player_move");
        resp.put("status-line", ResponseStatusLine.get("301", req.get("HTTP-Version")));

        storeAllCookies(settings, board);
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

    private void storeCookie(String name, String value, String path) {
        cookies.add(name + "=" + value + "; " + "path=" + path);
    }

    private void storeAllCookies(Hashtable settings, Board board) {
        storeCookie("board", board.getSlots(), "/player_move");
        storeCookie("playerOne", (String) settings.get("playerOne"), "/player_move");
        storeCookie("playerTwo", (String) settings.get("playerTwo"), "/player_move");
        storeCookie("boardSize", (String) settings.get("boardSize"), "/player_move");
    }
}
