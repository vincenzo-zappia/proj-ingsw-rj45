
package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.messages.InsertionMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SelectionMessage;

import java.util.ArrayList;

public class GameController {

    //region ATTRIBUTES
    private final Game game;
    private final ArrayList<String> playerUsernames;
    private String currentPlayer;
    private turnPhase currentPhase;
    private boolean endGame;

    private ClientHandler clientHandler;

    //endregion

    //region CONSTRUCTOR
    public GameController(ArrayList<String> playerUsernames, Game game, ClientHandler clientHandler){
        this.playerUsernames = playerUsernames;
        currentPlayer = playerUsernames.get(0); //TODO: the couch
        this.game = game;
        endGame = false;

    }
    //endregion

    enum turnPhase {SELECTION, INSERTION};

    //region TURN
    //TODO: Esportarlo in una classe esterna? Si, ma TurnController non si occuperà più di gestire legalità
    //TODO: delle mosse. Unico utilizzo eventuale gestione nello scandire fasi di gioco
    //method that sets the current player and starts the endgame
    public void nextTurn(){
        int currentPlayerIndex = playerUsernames.indexOf(currentPlayer);

        //circular iteration of turns
        if(currentPlayerIndex + 1 < playerUsernames.size()) currentPlayerIndex += 1;
        else if(!endGame) currentPlayerIndex = 0;
            //the player to the right of the sofa is the last player (the player who goes after the first one is the one to his left because the game turns go clockwise)
        else {
            //TODO: spostare implementazione logica di gioco: in base alla fase corrente setatta da TurnController
            //TODO: Lobby chiamerà gli eventi di gioco
            findWinner();
            return;
        }
        currentPlayer = playerUsernames.get(currentPlayerIndex);
    }

    //method that checks the validity of the player's move and calls the game methods
    //TODO: review required
    //TODO: dividere ancor di più le fasi quando sarà da implementarsi la GUI (routine di inizio e fine turno)
    public void startTurn(){
        //int playerIndex = playerUsernames.indexOf(currentPlayer);

        //TODO: Check legalità verrà fatto in Lobby(?) e non nello gestore turni
        //if the bookshelf is full then the endgame begins
        if(game.isPlayerBookshelfFull(currentPlayer)) endGame = true;

        //If the game isn't ended then the current player changes and the action of the turn is called again recursively
        nextTurn();
        startTurn();
    }
    //endregion

    //region METHODS

    //TODO: Inutile
    private void waitPhase(turnPhase phase){
        while(!currentPhase.equals(phase)){
            waitPhase(phase);
        }
    }

//TODO: Metodo switch case chiamato da ClientHandler a sua volta chiamato da Lobby
    public void receiveMessage(Message message){
        switch (message.getType()){

            //TODO: Logica di gioco conseguentemente gestita esternamente alla gestione turni
            case SELECTION_MESSAGE -> {
                cardSelection((SelectionMessage) message);

            }
            case INSERTION_MESSAGE -> {
                cardInsertion((InsertionMessage) message);
            }
        };
    }

    //TODO: Metodo che chiama il primo startTurn() (forse gestito da Lobby)
    public void startGame(){
        //TODO: Inizializzazione

        startTurn();
    }


    //method that extracts the coordinates from the XML command, checks the validity of the selection and turns the
    //coordinates into their corresponding Cards
    public void cardSelection(SelectionMessage message){
        if(game.isSelectable(message.getCoordinates())) {
            game.removeCardFromBoard(message.getCoordinates()); //Removal of the selected cards form the game board
        }

        //TODO: notifica gli altri player (chiamata metodi VirtualView) (Observer) oppure in Game (meglio in Game)

        //TODO: invio eccezione
    }

    //method that extracts the chosen column from the XML and inserts the cards previously selected into the player's bookshelf
    public void cardInsertion(InsertionMessage message){
        //Insertion of the cards removed from the board into the player's bookshelf
        try {
            game.addCardToBookshelf(currentPlayer, message.getSelectedColumn(), message.getSelectedCards());
        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }
    }

    //method that creates the final scoreboard
    public void findWinner(){
        game.scoreCommonGoal();
        game.scorePrivateGoal();

        //TODO: creation of the scoreboard based on the calculated scores for each one of the players
        //TODO: calling of Game method that creates ordered ArrayList of Players
    }

    public ClientHandler getClientHandler(){return clientHandler;}
    public String getCurrentPlayer(){return currentPlayer;}

    //endregion
}
