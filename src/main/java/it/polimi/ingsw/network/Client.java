package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Subject;

import java.io.*;
import java.net.Socket;

/**
 * Class that manages only the network functionality of the Client (send and receive message,
 * server connection, ...)
 */
public class Client extends Subject implements Runnable{

    //region ATTRIBUTES
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    //endregion

    //region CONSTRUCTOR
    public Client(String ip, int port){

        try {
            socket = new Socket(ip, port);
            objOut = new ObjectOutputStream(socket.getOutputStream());
            objIn = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.port = port;
        this.ip = ip;

        //TODO: Se è la CLI/GUI a creare ClientManager come si fa a passare un già esistente ClientManager al Client?
        //this.register(new ClientActionManager());
    }
    //endregion

    /**
     * The method receiveMessage() of Client is called in loop by the CLI for the whole duration of the game
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            //Actual management of the received message relatively to the state of Client
            notifyObserver(receiveMessage());
        }
    }

   public void sendMessage(Message msg){
       try {
           objOut.writeObject(msg);
           objOut.reset();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

    /**
     * Waits the reception of a message and manages it accordingly to the state of Client
     */
    public Message receiveMessage(){
        boolean res = false;
        Message msg = null;
        try {
            while(!res){
                msg = (Message) objIn.readObject();
                res = msg!=null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return msg;
    }

    //endregion

}
