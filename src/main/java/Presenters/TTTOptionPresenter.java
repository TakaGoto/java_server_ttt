package Presenters;

public class TTTOptionPresenter {
    public static String generateOptions() {
        String htmlOptions = "<form class='start-button' action='/game' method=\"post\">";
        htmlOptions += "<small> player one </small>";
        htmlOptions += "<select name=\"playerOne\" size=\"1\">";
        htmlOptions += "<option value=" + "human> human" + "</option>";
        htmlOptions += "<option value=" + "computer> computer" + "</option></select>";
        htmlOptions += "<small> player two </small>";
        htmlOptions += "<select name=\"playerTwo\" size=\"1\">";
        htmlOptions += "<option value=" + "human> human" + "</option>";
        htmlOptions += "<option value=" + "computer> computer" + "</option></select>";
        htmlOptions += "<small> board size </small>";
        htmlOptions += "<select name=\"boardSize\" size=\"1\">";
        htmlOptions += "<option value=" + "3> 3" + "</option>";
        htmlOptions += "<option value=" + "4> 4" + "</option></br>";
        htmlOptions += "<div><input id=\"start_game\" type=\"submit\" value=\"Start Game\"></div>";
        htmlOptions += "</form>";
        return htmlOptions;
    }
}
