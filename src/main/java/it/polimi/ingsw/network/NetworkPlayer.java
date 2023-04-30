package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.VirtualView;

/**
 * Classe creata per collegare ad un username il relativo ClientHandler e VirtualView (gia inizializzato con il ClientHandler)
 */
public class NetworkPlayer {
    private final String username;
    private final ClientHandler clientHandler;
    private VirtualView virtualView;

    public NetworkPlayer(String username, ClientHandler clientHandler) {
        this.username = username;
        this.clientHandler = clientHandler;
    }

    public String getUsername() {
        return username;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public void setVirtualView(VirtualView virtualView){
        this.virtualView = virtualView;
    }
}
