package Responders;

import Presenters.BoardPresenter;
import Ui.JavaTTTUi;
import com.server.Handlers.Responder;
import com.server.Responses.ResponseStatusLine;
import com.tictactoe.Board.Board;
import com.tictactoe.Game;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GameStart implements Responder {
    JavaTTTUi javaTTTUi = new JavaTTTUi();
    protected List cookies = new ArrayList<String>();
    private Hashtable resp = new Hashtable();
    private Hashtable req = new Hashtable();
    private Hashtable header = new Hashtable();

    public Hashtable respond(Hashtable req) {
        this.req = req;

        header.put("Content-Type", "text/html");
        header.put("Connection", "close");

        if(req.get("Method").equals("POST"))
            doPost();
        else if(req.get("Method").equals("GET"))
            doGet();

        resp.put("message-header", header);

        return resp;
    }

    private void storeCookie(String name, String value, String path) {
        cookies.add(name + "=" + value + "; " + "path=" + path);
    }

    private void doPost() {
        Hashtable params = (Hashtable) req.get("Body");
        Board board = new Board(Integer.parseInt((String) params.get("boardSize")));
        storeCookie("board", board.getSlots(), "/game");
        storeCookie("playerOne", (String) params.get("playerOne"), "/game");
        storeCookie("playerTwo", (String) params.get("playerTwo"), "/game");
        storeCookie("boardSize", (String) params.get("boardSize"), "/game");
        resp.put("Set-Cookie", cookies);
        doGet();
    }

    private void doGet() {
        if(cookies.isEmpty()) {
            header.put("Location", "http://localhost:5000/");
            resp.put("status-line", ResponseStatusLine.get("301", req.get("HTTP-Version")));
            resp.put("message-body", "".getBytes(Charset.forName("utf-8")));
        } else {
            Hashtable settings = parseCookies();
            Board board = Game.playGame(javaTTTUi, settings, "");
            String body = BoardPresenter.generateBoard(board.getSlots());
            resp.put("message-body", body.getBytes(Charset.forName("utf-8")));
            resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));
        }
    }

    private Hashtable parseCookies() {
        Hashtable parsedCookies = new Hashtable();
        for(Object cookie: cookies) {
            String theCookie = (String) cookie;
            String[] getCookie = theCookie.split(";");
            String[] splitCookie = getCookie[0].split("=");
            parsedCookies.put(splitCookie[0], splitCookie[1]);
        }
        return parsedCookies;
    }
}
