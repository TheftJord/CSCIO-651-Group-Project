package com.groupproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    private int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), screenWidth-100, screenHeight-100);
        stage.setScene(scene);
        stage.setTitle("CSCI 651 - B+-Tree Storage");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}