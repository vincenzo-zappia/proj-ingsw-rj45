package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.view.gui.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Controller set common between scenes (static)
 */
public abstract class GenericScene {

    protected static ClientController controller;
    protected static GUI gui;

    @FXML private Label feedback;

    public static void setController(ClientController contr){
        controller = contr;
    }

    public static void setGui(GUI g){ gui = g; }

    public void showMessage(boolean response, String message) {
        feedback.setVisible(true);
        if(response) feedback.setTextFill(Color.GREEN);
        else feedback.setTextFill(Color.RED);
        feedback.setText(message);
    }
}