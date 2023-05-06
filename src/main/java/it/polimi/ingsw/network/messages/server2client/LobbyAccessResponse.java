package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class LobbyAccessResponse extends Message {

    //region ATTRIBUTES
    private final boolean successful;
    //endregion

    public LobbyAccessResponse(String sender, MessageType type, boolean successful) {
        super(sender, type);
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}