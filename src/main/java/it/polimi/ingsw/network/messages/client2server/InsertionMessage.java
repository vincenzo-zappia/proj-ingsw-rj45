package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

public class InsertionMessage extends Message {
    private ArrayList<Card> selectedCards;
    private int selectedColumn;

    protected InsertionMessage(String sender) {
        super(sender, MessageType.INSERTION_MESSAGE);
    }


    public ArrayList<Card> getSelectedCards() {
        return selectedCards;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }
}