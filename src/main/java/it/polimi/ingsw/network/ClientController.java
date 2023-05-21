package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.client2server.*;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.UserInterface;

/**
 * Manages the messages received from the server by calling the methods of the user interface.
 * Creates and sends messages to the server from the user interface.
 */
public class ClientController implements Observer {
    //region ATTRIBUTES
    private final UserInterface view; //either CLI or GUI for the packing of messages User interface -> Server
    private final Client client; //for the unpacking of messages Server -> User interface
    private String username;
    //endregion

    //region CONSTRUCTOR
    public ClientController(UserInterface view, Client client){
        this.view = view;
        this.client = client;
        client.register(this);
        new Thread(client).start();
    }
    //endregion

    //region SERVER2CLIENT
    /**
     * Manages the reception of the messages from Client (therefore Server) and outputs them to CLI/GUI by calling View methods
     * @param message message received from Client
     */
    @Override
    public void update(Message message){
        switch (message.getType()){
            case GENERIC_RESPONSE -> {
                TextResponse response = (TextResponse) message;
                view.sendGenericResponse(response.getResponse(), response.getContent());
            }
            case CHECKED_USERNAME -> {
                SpecificResponse response = (SpecificResponse) message;

                //Linking the verified username to the client
                this.username = response.getContent();

                view.confirmUsername(response.getResponse());
            }
            case ACCESS_RESPONSE -> {
                SpecificResponse response = (SpecificResponse) message;
                view.confirmAccess(response.getResponse());
            }
            case NEW_CONNECTION -> {
                UsernameListMessage connectionMessage = (UsernameListMessage) message;
                view.refreshConnectedPlayers(connectionMessage.getUsernameList());
            }
            case CURRENT_PLAYER -> view.showCurrentPlayer(message.getContent());
            case CHECKED_COORDINATES -> {
                CoordinatesMessage checkedCoordinates = (CoordinatesMessage) message;
                view.sendCheckedCoordinates(checkedCoordinates.getCoordinates());
            }
            case REMOVED_CARDS -> {
                CoordinatesMessage removedCards = (CoordinatesMessage) message;
                view.showRemovedCards(removedCards.getCoordinates());
            }
            case UPDATED_BOOKSHELF -> {
                BookshelfMessage updatedBookshelf = (BookshelfMessage) message;
                view.showUpdatedBookshelf(updatedBookshelf.getBookshelf());
            }
            case REFILLED_BOARD -> {
                BoardMessage boardUpdate = (BoardMessage) message;
                view.showRefilledBoard(boardUpdate.getBoardCells());
            }
            case GOALS_DETAILS -> {
                GoalsMessage goalsMessage = (GoalsMessage) message;
                view.showGoalsDetails(goalsMessage.getCommonGoals(), goalsMessage.getPrivateGoal());
            }
            case SCOREBOARD -> {
                ScoreboardMessage scoreboard = (ScoreboardMessage) message;
                view.showScoreboard(scoreboard.getScoreboard());
            }
            case FORCE_DISCONNECTION -> {
                view.showDisconnection();
            }
        }
    }
    //endregion

    //region CLIENT2SERVER
    /**
     * Creates and sends request to check the availability of a chosen username
     * @param username to check
     */
    public void checkUsername(String username){
        Message message = new CheckUsernameRequest(username);
        client.sendMessage(message);
    }

    /**
     * Creates and sends the request to create a new lobby
     */
    public void createLobby(){
        Message create = new CreateLobbyRequest(username);
        client.sendMessage(create);
    }

    /**
     * Creates and sends the request to join a lobby
     * @param lobbyId identification number of the lobby that the player wants to join
     */
    public void joinLobby(int lobbyId){
        Message join = new JoinLobbyRequest(username, lobbyId);
        client.sendMessage(join);
    }

    /**
     * Creates and sends the Message that once received by the server will start the game
     */
    public void startGame(){
        StartGameRequest start = new StartGameRequest(username);
        client.sendMessage(start);
    }

    /**
     * Creates a Message out of the coordinates of the cards selected by the user and sends them to the server
     * @param coordinates of the cards selected by the user
     */
    public void sendSelection(int[][] coordinates){
        SelectionRequest selectionRequest = new SelectionRequest(username, coordinates);
        client.sendMessage(selectionRequest);
    }

    /**
     * Creates a Message out of the ordered cards and the column for their insertion chosen by the user and sends them to the server
     * @param column where the selected cards will be inserted
     */
    public void sendInsertion(int column){
        Message insert = new InsertionRequest(username, column);
        client.sendMessage(insert);
    }
    //endregion

}
