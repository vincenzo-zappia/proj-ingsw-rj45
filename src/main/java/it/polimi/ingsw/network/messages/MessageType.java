package it.polimi.ingsw.network.messages;

public enum MessageType {

    //region CLIENT2SERVER
    JOIN_LOBBY_REQUEST,
    CREATE_LOBBY_REQUEST,
    START_GAME_REQUEST,
    SELECTION_REQUEST,
    INSERTION_REQUEST,
    //endregion

    //region SERVER2CLIENT
    LOBBY_ACCESS_RESPONSE,
    LOBBY_CREATION_RESPONSE,
    START_GAME_RESPONSE,
    SELECTION_RESPONSE,
    INSERTION_RESPONSE,

    CARD_REMOVE_UPDATE,
    BOARD_REFILL_UPDATE,
    GOALS_DETAILS,
    CURRENT_PLAYER_UPDATE,
    NEW_CONNECTION_UPDATE,

    NOT_YOUR_TURN,
    ERROR_MESSAGE,
    SCOREBOARD,
    END_GAME;
    //endregion


}
