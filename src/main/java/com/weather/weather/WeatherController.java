package com.weather.weather;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controls the actions of the UI.
 */
public class WeatherController {
    @FXML
    public TextField cityInput;
    @FXML
    public Button searchButton;

    private final CitySearcher citySearcher;
    private City city;

    public WeatherController() {
        this.citySearcher = new CitySearcher();
    }

    /**
     * Called when the search button is clicked.
     */
    public void search() {
        try {
            this.city = citySearcher.search(cityInput.getText());
            this.displayCity();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Connection failed!");
            alert.setContentText("Please make sure you have a connection to the internet");
            alert.show();
        }
    }

    /**
     * Displays the city's weather to the user or a warning if the city is not found.
     */
    private void displayCity() {
        if (this.city != null) {
            System.out.println("City found");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("City Not Found");
            alert.setContentText("The city '" + cityInput.getText() +"' was not found!");
            alert.show();
        }
    }
}
