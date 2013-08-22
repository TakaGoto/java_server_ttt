package Responders;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class TTTOptionTest {
    TTTOption tttOption;
    Hashtable req;
    Hashtable resp;
    Hashtable header;

    @Before public void init() {
        tttOption = new TTTOption();
        req = new Hashtable();
        req.put("HTTP-Version", "HTTP/1.0");
        resp = tttOption.respond(req);
        header = (Hashtable) resp.get("message-header");
    }

    @Test public void hasAByteBody() throws IOException {
        String body = new String((byte[]) resp.get("message-body"), "UTF-8");
        Assert.assertTrue(body.contains("form"));
    }

    @Test public void hasAContentType() {
        assertEquals("text/html", header.get("Content-Type"));
    }

    @Test public void hasAConnectionHeader() {
        assertEquals("close", header.get("Connection"));
    }

    @Test public void hasATwoHundreadResponse() {
        assertEquals("HTTP/1.0 200 OK", resp.get("status-line"));
    }
}
