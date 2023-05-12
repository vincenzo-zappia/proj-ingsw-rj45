package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

/**
 * A subject maintains a list of its observers and notifies them of any state changes
 */
public abstract class Subject {
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Adds observer to the list
     * @param o observer to add
     */
    public void register(Observer o) {
        observers.add(o);
    }

    /**
     * Removes observer from the list
     */
    public void unregister(Observer o) {
        observers.remove(o);
    }

    /**
     * Notifies all observers in the list
     * @param message object with the updated information
     */
    public void notifyObserver(Message message) {
        for(Observer o : observers){
            o.update(message);
        }
    }

}
