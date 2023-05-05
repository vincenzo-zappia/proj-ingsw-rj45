package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.View;

import java.util.Scanner;

public class CLI implements Runnable, View {
    private final Scanner scanner;
    private final ClientController controller;

    public CLI(Client client) {
        scanner = new Scanner(System.in);
        controller = new ClientController(this, client);
    }

    @Override
    public void run() {

        //TODO: Stampa a schermo titolo di gioco da metodo di CliUtils

        connection();

    }

    //region PRIVATE METHODS
    /**
     * The client interface asks the player his username
     */
    private String requestUsername() {
        String username;
        do{
            System.out.println("Enter username:");
            username = scanner.nextLine();
            if (username.replace(" ", "").equals("")) System.out.println(CliUtil.makeErrorMessage("Enter valid username:"));
        }while(username.replace(" ", "").equals(""));

        return username;
    }

    /**
     * The client interface asks the player if he wants to create a new lobby and, if he doesn't,
     * the ID of the lobby he wants to join
     */
    public int requestLobby() {
        int selection;
        do {
            System.out.println("[0] Create new lobby");
            System.out.println("[1] Join existing lobby");
            selection = Integer.parseInt(scanner.nextLine());
            if(selection != 0 && selection != 1) System.out.println(CliUtil.makeErrorMessage("Enter valid number."));
        }while (selection != 0 && selection != 1);
        return selection;
    }

    //endregion

    public void connection(){
        int choice = requestLobby();
        String username = requestUsername();

        if(choice == 0){
            controller.createLobby(username);
        }
        else if(choice == 1){
            System.out.println("Enter lobby id:");
            int id = Integer.parseInt(scanner.nextLine());
            controller.joinLobby(username, id);
        }


    }

    public void showErrorMessage(){

    }



    @Override
    public void showRemovedCards(int[][] coordinates) {

    }

    @Override
    public void showRefilledBoard(Board board) {
        //TODO: CliUtil.makeBoard() con che parametro?
    }

    @Override
    public void sendResponse(boolean response, MessageType responseType) {

    }

    @Override
    public void sendNotYourTurn() {

    }


}
