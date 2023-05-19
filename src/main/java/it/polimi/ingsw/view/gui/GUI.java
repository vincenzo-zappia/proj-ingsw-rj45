package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {
    //TODO: Spostare creazione client nel costruttore di GUI e CLI
    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("Username.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 300,300);
            stage.setTitle("MyShelfie");
            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
