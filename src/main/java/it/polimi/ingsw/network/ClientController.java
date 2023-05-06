package it.polimi.ingsw.network;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.messages.client2server.CreateLobbyMessage;
import it.polimi.ingsw.network.messages.client2server.InsertionMessage;
import it.polimi.ingsw.network.messages.client2server.JoinLobbyMessage;
import it.polimi.ingsw.network.messages.client2server.StartGame;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.state.ClientSelectionState;
import it.polimi.ingsw.state.TurnState;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

//TODO: Per l'impacchettamento messaggi serve implementare un nuovo tipo di Observer che cambia i parametri di implementazione update
public class ClientController implements Observer {
    //region ATTRIBUTES
    private TurnState turnState; //TODO: Decidere se rimuovere il design pattern
    private final View view; //either CLI or GUI for the packing of messages User interface -> Server
    private Client client; //for the unpacking of messages Server -> User interface
    private String username;
    private int lobbyId;
    //endregion

    //region CONSTRUCTOR
    public ClientController(View view, Client client){
        this.view = view;
        this.client = client;
        client.register(this);
        new Thread(client).start();

        //TODO: Decidere come gestire fase iniziale se da costruttore o dal primo messaggio inviato da server
        turnState = new ClientSelectionState(this);
    }
    //endregion

    //region SERVER2CLIENT
    //TODO: Le chiamate dei metodi astratti della view che prendono parametri diversi da Message avverrà con message.getX()
    /**
     * Manages the reception of the messages from Client (therefore Server) and outputs them to CLI/GUI by calling View methods
     * @param message message received from Client
     */
    @Override
    public void update(Message message){

        //TODO: Per il momento non uso lo state
        switch (message.getType()){
            case BOARD_REFILL -> {
                BoardRefillUpdate boardUpdate = (BoardRefillUpdate) message;
                view.showRefilledBoard(boardUpdate.getBoardCells());
            }
            case SELECTION_RESPONSE -> {}
            case INSERTION_RESPONSE -> {}
            case LOBBY_CREATION_RESPONSE -> {
                LobbyCreationResponse newLobby = (LobbyCreationResponse) message;
                view.connectionSuccess(newLobby.getLobbyId());
            }
            case LOBBY_ACCESS_RESPONSE -> {
                LobbyAccessResponse access = (LobbyAccessResponse) message;
                view.connectionSuccess(lobbyId);
            }
            case ERROR_MESSAGE -> {
                ErrorMessage error = (ErrorMessage) message;
                view.showError(error.getContent());
            }
            case NEW_CONNECTION -> {
                NewConnectionMessage connectionMessage = (NewConnectionMessage) message;
                view.refreshConnectedPlayers(connectionMessage.getUsernameList());
            }
        }
        //TODO: Chiamata di metodi View per sputare su GUI/CLI
        turnState.messageHandler(message);
    }
    //endregion

    //region CLIENT2SERVER
    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public void createLobby(String username){
        Message create = new CreateLobbyMessage(username);
        this.username = username;
        client.sendMessage(create);
    }

    public void joinLobby(String username, int lobbyId){
        Message join = new JoinLobbyMessage(username, lobbyId);
        this.lobbyId = lobbyId;
        this.username = username;
        client.sendMessage(join);
    }

    public void startGame(){
        StartGame start = new StartGame(username);
        client.sendMessage(start);
    }

    //TODO: Metodi impacchettamento messaggi. Outsource con creazione di interfaccia parallela a Observer con diversi tipi di implementazione del metodo update o locale?

    /**
     * method that send the selected cards and
     * bookshelf's column to insert them to the server
     * @param selected cards (arrayList of cards)
     * @param column of the player bookshelf
     */
    public void sendInsertion(ArrayList<Card> selected, int column){
        Message insert = new InsertionMessage(this.username, selected, column);
        client.sendMessage(insert);
    }

    //endregion
}
