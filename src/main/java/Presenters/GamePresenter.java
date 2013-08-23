package Presenters;

public class GamePresenter {
    public static String generateMessage(String message) {
        if(!(message == null))
            return "<p> " + message + "</p>";
        else
            return "<p> enjoy! </p>";
    }
}
