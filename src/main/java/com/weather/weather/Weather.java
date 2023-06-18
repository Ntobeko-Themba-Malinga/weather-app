package com.weather.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Weather app program entry point.
 */
public class Weather extends Application {

    /**
     * Launches the javaFX window.
     * @param args commandline arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX's configurations before launching.
     * @param stage primary stage.
     * @throws Exception throws exception when the weather.fxml file is not found.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("weather.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
