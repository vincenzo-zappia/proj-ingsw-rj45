package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.cli.View;

//TODO: Aggiornare descrizione nel caso in cui TurnManager diventi TurnController
/**
 * Class that manages the creation of messages server -> client. Used by Lobby, GameController
 */
public class VirtualView implements View, Observer {
    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    @Override
    public void update(Message message){
        clientHandler.sendMessage(message);
    }

    //TODO: Popolare metodi

    @Override
    public void requestUsername() {
    }

    @Override
    public void requestLobby() {

    }

    @Override
    public void requestNumberOfPlayers() {

    }

    @Override
    public void requestCardSelection() {

    }

    @Override
    public void requestCardInsertion() {

    }

    @Override
    public void showRemovedCards(int[][] coordinates){
        clientHandler.sendMessage(new CardRemovalMessage(coordinates));
    }

    @Override
    public void showRefilledBoard(Board board){
        clientHandler.sendMessage(new BoardRefillUpdate(board));
    }

    @Override
    public void sendResponse(boolean response, MessageType responseType){
        clientHandler.sendMessage(new ResponseMessage(responseType, response));
    }

    @Override
    public void sendNotYourTurn(){
        clientHandler.sendMessage(new ResponseMessage(MessageType.NOT_YOUR_TURN, false));
    }
}
