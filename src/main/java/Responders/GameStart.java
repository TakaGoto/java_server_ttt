package Responders;

import Presenters.BoardPresenter;
import Ui.JavaTTTUi;
import com.server.Handlers.Responder;
import com.server.Responses.ResponseStatusLine;
import com.tictactoe.Board.Board;
import com.tictactoe.Game;

import java.nio.charset.Charset;
import java.util.Hashtable;

public class GameStart implements Responder {
    JavaTTTUi javaTTTUi = new JavaTTTUi();
    protected String cookies = "";
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

    private void storeCookie(String name, String value) {
        cookies += name + "=" + value + "; ";
    }

    private void doPost() {
        Hashtable params = (Hashtable) req.get("Body");
        Board board = new Board(Integer.parseInt((String) params.get("boardSize")));
        storeCookie("board", board.getSlots());
        storeCookie("playerOne", (String) params.get("playerOne"));
        storeCookie("playerTwo", (String) params.get("playerTwo"));
        cookies += "boardSize=" + params.get("boardSize");
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
        String[] cookies = this.cookies.split("; ");
        for(String cookie: cookies) {
            String[] splitCookies = cookie.split("=");
            parsedCookies.put(splitCookies[0], splitCookies[1]);
        }
        return parsedCookies;
    }
}
