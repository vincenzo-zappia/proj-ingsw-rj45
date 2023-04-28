/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.goals.PrivateGoal;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {

    //region ATTRIBUTES
    private String username;
    private int gameID; //per contesto multipartita
    private int score;
    private boolean isMyTurn;
    private boolean firstPlayer;
    private PrivateGoal privateGoal;
    private Bookshelf bookshelf;
    //endregion


    //region CONSTRUCTOR
    public Player(String username, int gameID){
        this.username=username;
        this.gameID=gameID;
        score = 0;
        bookshelf = new Bookshelf();
    }

    public Player() {
        //TODO: remove after networking complete
    }
    //endregion

    //region METHODS
    public void addScore(int points){score+=points;}

    public boolean isBookshelfFull(){
        return bookshelf.checkIfFull();    //per non cambiare precedenti metodi, implementazione corretta e spostata in bookshelf.
    }

    //method to order players by score (see class Game.java)
    @Override
    public int compareTo(Player o) {
        return Integer.compare(score, o.getScore());
    }

    //endregion

    //region GETTER AND SETTER
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    public boolean getTurn(){
        return isMyTurn;
    }
    public void setTurn(boolean turn){
        isMyTurn = turn;
    }
    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }
    public void setPrivateGoal(PrivateGoal privateGoal) {
        this.privateGoal = privateGoal;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){this.username=username;} //TODO: remove after networking complete
    public int getScore(){return score;}
    public void setScore(int newScore){score = newScore;}  //TODO remove after test

    @Override
    public String toString(){
        return "\n\tUsername: " + username + "\n\tGameId: " + gameID + "\n\tScore: " + score;
    }

    //endregion

}
