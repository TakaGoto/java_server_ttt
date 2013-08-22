import Responders.GameStart;
import Responders.TTTOption;
import com.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(5000);
        server.mount("/", new TTTOption());
        server.mount("/game", new GameStart());
        server.listen();
    }
}
