package Ui;

import com.tictactoe.Board.Board;
import com.tictactoe.UserInterface.UserInterface;

import java.util.Hashtable;

public class JavaTTTUi implements UserInterface{
    Hashtable messages = new Hashtable();

    public void welcomeMessage() {
        addMessage("Welcome to Tic Tac Toe!");
    }

    public void endGame() {
        addMessage("Thanks for playing! Good bye.");
    }

    public void printBoard(Board board) {
        messages.put("board", board.getSlots());
    }

    public void displayResult(String result) {
        String htmlResult;
        if(result.equals("tie")) {
            htmlResult = "Game over!  It is a tie";
        } else {
            htmlResult = "Game over!  " + result + " wins!";
        }
        htmlResult += "<p> play again ? </p><a href='/'>yes</a>";
        addMessage(htmlResult);
    }

    public String getMessage() {
        return (String) messages.get("message");
    }

    public String getBoard() {
        return (String) messages.get("board");
    }

    private void addMessage(String message) {
        messages.put("message", message);
    }

    public String askPlayerOneOption() {return null;}
    public String askPlayerTwoOption() {return null;}
    public String getPlayerOptionInput() {return null;}
    public String getBoardOptionInput() {return null;}
    public int askBoardOption() {return 0;}
    public int askPlayerMove() {return 0;}
    public boolean askContinue() {return false;}
}
