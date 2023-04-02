/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.state;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.Player;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.exceptions.AddCardException;

import java.util.ArrayList;

public class Game{

    //REGION ATTRIBUTES
    private Board board;
    //private ArrayList<Bookshelf> bookshelves; //Game is the class that puts together multiple entities and has specific value
    private ArrayList<Player> players;
    private int playerNum;
    private GameState state;
    private int currentPlayer; //current turn player's index in "players"
    private Goal commonGoal1;
    private Goal commonGoal2;
    private boolean endGame;

    //END REGION

    //REGION CONSTRUCTOR
    public Game(int playerNum){
        board = new Board(playerNum);
        board.fillBoard(); //filling of the board with cards

        /*
        //initialization of playerNum bookshelves
        bookshelves = new Bookshelf[playerNum];
        for (Bookshelf b : bookshelves){
            b = new Bookshelf();
        }
        */
        System.out.println(board);
    }
    //END REGION

    //REGION METHODS

    //method that calls "Board.removeCard()" checking if it's the player's turn
    //"coordinates" is a matrix with the coordinates of the max 3 cards that the player selected:
    //pos = {
    //  {x1, y1},
    //  {x2, y2},
    //  {x3, y3}
    //}
    public void removeCardFromBoard(int[][] coordinates){
        for(int i = 0; i < coordinates.length; i++) board.removeCard(coordinates[i][0], coordinates[i][1]);
        System.out.println("Cards removed!");
    }

    public void addCardToBookshelf(String playerUsername, int column, Card[] cards) throws AddCardException {
        int playerIndex = players.indexOf(playerUsername); //TODO: exception in case username not found

        //inserts each selected card into the player's bookshelf
        for(Card c : cards){
            players.get(playerIndex).getBookshelf().addCard(column, c);
        }
    }

    public void scoreCommonGoal(){
        for(Player p : players){
            p.addScore(commonGoal1.checkGoal(p.getBookshelf()));
            p.addScore(commonGoal2.checkGoal(p.getBookshelf()));
        }
    }

    //method that checks each player's private goal (can be called during and at the end of the game
    //TODO: deciding when the method will be called (end game or repeatedly mid game)
    public void scorePrivateGoal(){
        for(Player p : players){
            p.addScore(p.getPrivateGoal().checkGoal(p.getBookshelf()));
        }
    }
}
