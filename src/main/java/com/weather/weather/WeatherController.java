package com.weather.weather;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    public Label weatherDescriptionLabel;
    @FXML
    public Label tempLabel;
    @FXML
    public Label speedLabel;
    @FXML
    public TextField cityInput;
    @FXML
    public Button searchButton;
    @FXML
    public ImageView weatherIcon;
    @FXML
    public ImageView tempIcon;
    @FXML
    public ImageView windIcon;
    @FXML
    public ListView<String> citiesList;

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
            this.city = citySearcher.search(cityInput.getText().trim());
            this.displayCity();
            if (this.city != null) {
                this.citySaver.save(this.city);
                displaySavedCities();
            }
        } catch (Exception e) {
            failedConnectionMessage();
        }
    }

    /**
     * Called when a city name is clicked on the ListView.
     * Saves city to the cities.db database.
     */
    public void search(String cityName) {
        Platform.runLater(() -> {
            try {
                city = citySaver.getCity(cityName);
                displayCity();
            } catch (Exception e) {
                failedConnectionMessage();
            }
        });
    }

    /**
     * Displays the city's weather to the user or a warning if the city is not found.
     */
    private void displayCity() {
        if (this.city != null) {
            try {
                JSONObject weather = this.cityWeather.search(this.city);
                String description = weather.getString("condition").toLowerCase();
                this.weatherIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource(description + ".png")).toExternalForm()));
                this.tempIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource("temperature.png")).toExternalForm()));
                this.windIcon.setImage(new Image(Objects.requireNonNull(getClass().getResource( "wind2.png")).toExternalForm()));
                this.weatherDescriptionLabel.setText(description);
                this.cityLabel.setText(this.city.getName().toUpperCase());
                this.tempLabel.setText(weather.getDouble("temperature") + " °C");
                this.speedLabel.setText(weather.getDouble("wind_speed") + " KM/H");
            } catch (Exception e) {
                e.printStackTrace();
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
     * Displays all the saved cities.
     */
    private void displaySavedCities() {
        this.citiesList.getItems().clear();
        for (City city : this.citySaver.getCities()) this.citiesList.getItems().add(city.getName());
    }

    /**
     * Removes a saved city from the database.
     * @param cityName city to delete from database.
     */
    private void deleteSavedCity(String cityName) {
        Alert cityDeleteWarning = new Alert(Alert.AlertType.WARNING);
        cityDeleteWarning.setTitle("Delete city?");
        cityDeleteWarning.setContentText(
                String.format("Are you sure you want to delete '%s'?", cityName)
        );

        if (cityDeleteWarning.showAndWait().filter(ButtonType.OK::equals).isPresent()) {
            this.citySaver.deleteCity(cityName);
            this.displaySavedCities();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cityLabel.setText("");
        weatherDescriptionLabel.setText("");
        tempLabel.setText("");
        speedLabel.setText("");
        displaySavedCities();
        citiesList.setOnMouseClicked(event -> {
            String selectedItem = citiesList.getSelectionModel().getSelectedItem();
            if (event.getClickCount() >= 2 && selectedItem != null) {
               this.deleteSavedCity(selectedItem);
            } else if (selectedItem != null) {
                search(selectedItem);
            }
        });
    }
}
