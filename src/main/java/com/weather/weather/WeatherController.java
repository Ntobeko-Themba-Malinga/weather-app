package com.weather.weather;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controls the actions of the UI.
 */
public class WeatherController implements Initializable {
    @FXML
    public AnchorPane pane;
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
    private final CitySaver citySaver;
    private City city;

    public WeatherController() {
        this.citySearcher = new CitySearcher();
        this.cityWeather = new CityWeather();
        this.citySaver = new CitySaver("cities.db");
    }

    /**
     * Called when the search button is clicked.
     * Saves city to the cities.db database.
     */
    public void search() {
        try {
            this.city = citySearcher.search(cityInput.getText());
            this.displayCity();
            this.citySaver.save(this.city);
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
                this.cityLabel.setText(this.city.getName().toUpperCase());
                this.tempLabel.setText(weather.getDouble("temperature") + " °C");
                this.speedLabel.setText(weather.getDouble("wind_speed") + " KM/H");
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

    /**
     * Called when this controller's fxml file is loaded.
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource("background4.jpg")).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(900);
        imageView.setFitHeight(600);
        pane.getChildren().add(0, imageView);
    }
}
