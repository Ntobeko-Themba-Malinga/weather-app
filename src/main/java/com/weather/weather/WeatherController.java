package com.weather.weather;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;

/**
 * Controls the actions of the UI.
 */
public class WeatherController {
    @FXML
    public Label cityLabel;
    @FXML
    public Label tempLabel;
    @FXML
    public Label speedLabel;
    @FXML
    public TextField cityInput;
    @FXML
    public Button searchButton;

    private final CitySearcher citySearcher;
    private final CityWeather cityWeather;
    private City city;

    public WeatherController() {
        this.citySearcher = new CitySearcher();
        this.cityWeather = new CityWeather();
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
            try {
                JSONObject weather = this.cityWeather.search(this.city);
                this.cityLabel.setText(this.city.getName().toLowerCase());
                this.tempLabel.setText(weather.getDouble("temperature") + " °C");
                this.speedLabel.setText(weather.getDouble("wind_speed") + " km/h");
            } catch (Exception e) {
                failedConnectionMessage();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("City Not Found");
            alert.setContentText("The city '" + cityInput.getText() +"' was not found!");
            alert.show();
        }
    }

    /**
     * Displays the message for a failed connection.
     */
    private void failedConnectionMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Connection failed!");
        alert.setContentText("Please make sure you have a connection to the internet");
        alert.show();
    }
}
