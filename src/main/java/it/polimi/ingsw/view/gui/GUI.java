package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("first.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setTitle("hh");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
