package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.mechanics.GameController;
import it.polimi.ingsw.mechanics.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.server2client.ErrorMessage;
import it.polimi.ingsw.network.messages.server2client.GameStarted;
import it.polimi.ingsw.network.messages.server2client.ResponseMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    //region ATTRIBUTES
    private final int lobbyId;
    private final Server server;
    private ArrayList<String> playerUsernames;
    private HashMap<String, NetworkPlayer> networkMap;
    private boolean inGame;
    private GameController gameController;
    //endregion

    //region CONSTRUCTOR
    public Lobby(Server server, int lobbyId) {
        inGame = false;
        this.lobbyId = lobbyId;
        this.server = server;
        playerUsernames = new ArrayList<>();
        networkMap = new HashMap<>();
    }
    //endregion

    //region METHODS

    /**
     * Method used by clients to enter a lobby
     * @param netPlayer player that request access to lobby
     */
    public void joinLobby(NetworkPlayer netPlayer){
        Message response;
        //se il gioco sta andando non faccio entrare il player
        if(inGame) {
            response = new ErrorMessage("Game already started!");
            netPlayer.getClientHandler().sendMessage(response);
            return;
        }

        //se si è raggiunto il numero massimo di giocatori non faccio entrare il player
        if(playerUsernames.size()>=4){
            response = new ErrorMessage("Lobby is full!");
            netPlayer.getClientHandler().sendMessage(response);
            return;
        }


        String username = netPlayer.getUsername();
        assert (username != null);
        if(playerUsernames.contains(username)){
            response = new ErrorMessage("Username already taken!");
            netPlayer.getClientHandler().sendMessage(response);
            return;
        }


        //Aggiunta effettiva del player
        playerUsernames.add(username);
        VirtualView view = new VirtualView(netPlayer.getClientHandler());
        netPlayer.setVirtualView(view);
        networkMap.put(username, netPlayer);
    }

    //TODO: nuovo metodo da vedere se mantenere
    public void sendLobbyMessage(Message message){
        for(NetworkPlayer player: networkMap.values()){
            player.getClientHandler().sendMessage(message);
        }
    }

    /**
     * Method that start the game and initialize hashmaps and GameController
     */
    public void startGame(){
        assert (playerUsernames.size()>1 && playerUsernames.size()<=4);

        HashMap<String, VirtualView> viewHashMap = new HashMap<>();
        for (NetworkPlayer netPlayer: networkMap.values()) viewHashMap.put(netPlayer.getUsername(), netPlayer.getVirtualView());

        gameController = new GameController(new Game(playerUsernames), viewHashMap);
        inGame = true;
        sendLobbyMessage(new GameStarted());
    }

    /**
     * Forwards an external message to the GameController
     * @param msg message to forwards
     */
    public void sendToController(Message msg){
        gameController.messageHandler(msg);
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public ArrayList<String> getPlayerUsernames(){
        return playerUsernames;
    }
    //endregion
}
