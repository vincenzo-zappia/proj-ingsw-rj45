package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.scenes.GenericScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    //TODO: Spostare creazione client nel costruttore di GUI e CLI

    private Scene scene;
    private FXMLLoader currentLoader;

    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) {
        try{
            //TODO: setup scena iniziale;
            currentLoader = new FXMLLoader(GUI.class.getResource("username.fxml"));
            Scene scene = new Scene(currentLoader.load(), 900,600);
            this.scene = scene;
            startupStage(stage);
            stage.setScene(scene);
            stage.show();
            GUIManager guiManager = new GUIManager(this);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void startupStage(Stage stage){
        stage.setTitle("MyShelfie");
        stage.setFullScreen(true);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
    }

    public void loadScene(String filename){
        currentLoader = new FXMLLoader(GUI.class.getResource(filename));
        try {
            scene.setRoot(currentLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected GenericScene getController(){
        return currentLoader.getController();
    }

}