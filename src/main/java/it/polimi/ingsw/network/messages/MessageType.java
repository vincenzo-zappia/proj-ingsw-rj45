package it.polimi.ingsw.network.messages;

public enum MessageType {

    //region CLIENT2SERVER
    USERNAME_REQUEST,
    CREATE_LOBBY_REQUEST,
    JOIN_LOBBY_REQUEST,
    START_GAME_REQUEST,
    SELECTION_REQUEST,
    INSERTION_REQUEST,
    //endregion

    //region SERVER2CLIENT
    GENERIC_RESPONSE,
    ERROR_MESSAGE,
    CHECKED_USERNAME,
    ACCESS_RESPONSE,
    NEW_CONNECTION,
    CURRENT_PLAYER,
    CHECKED_COORDINATES,
    REMOVED_CARDS,
    UPDATED_BOOKSHELF,
    REFILLED_BOARD,
    GOALS_DETAILS,
    SCOREBOARD,
    FORCE_DISCONNECTION,
    //endregion

}
