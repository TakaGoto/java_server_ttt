package Presenters;

public class BoardPresenter {
    private static int size;
    private static String htmlBoard = "";

    public static String generateBoard(String board) {
        if(board.length() == 9) size = 3; else size = 4;

        htmlBoard = String.format("<div style='width: %spx;'>", size * 50);

        for(int i=1; i <= board.length(); i++) {
            String value;
            if(board.charAt(i-1) != '_') value = String.valueOf(board.charAt(i-1));
            else value = String.valueOf(i);

            htmlBoard += "<form action='/player_move' method='post' style='float:left;'>";
            htmlBoard += "<div style='width: 50px; length: 50px;'>";
            htmlBoard += String.format("<input type='submit' value= %s name='playerMove'>", value);
            htmlBoard += "</div></form>";
        }

        htmlBoard += "</div>";

        return htmlBoard;
    }
}
