package Responders;

import Presenters.TTTOptionPresenter;
import com.server.Handlers.Responder;
import com.server.Responses.ResponseStatusLine;

import java.nio.charset.Charset;
import java.util.Hashtable;

public class TTTOption implements Responder {
    private Hashtable resp = new Hashtable();

    public Hashtable respond(Hashtable req) {
        Hashtable header = new Hashtable();

        header.put("Content-Type", "text/html");
        header.put("Connection", "close");

        resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));
        resp.put("message-header", header);
        String body = TTTOptionPresenter.generateOptions();
        resp.put("message-body", body.getBytes(Charset.forName("utf-8")));
        return resp;
    }
}
