package com.weather.weather;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

/**
 * Searches for a city's weather.
 */
public class CityWeather {
    private final HttpClient client;
    private HttpRequest request;

    /**
     * Creates the instance of a city
     */
    public CityWeather() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Searches for a city's weather on the Open Weather API.
     * @param city The to search its weather for.
     * @return JSON containing all the city's weather conditions such as temperature, wind_speed, and condition.
     */
    public JSONObject search(City city) {
        String apiCall = String.format(
                "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s",
                city.getLatitude(),
                city.getLongitude(),
                "300517fbec11bc122e609a0e7e3d4e7e"
        );
        this.request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiCall))
                .build();
        return sendRequest();
    }

    /**
     * Send the request to the open weather api.
     * @return JSON response from the Open Weather api containing all city's weather conditions.
     */
    private JSONObject sendRequest() {
        JSONObject rawResponse = null;
        try {
            HttpResponse<String> response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());
            rawResponse = new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return parseResponse(Objects.requireNonNull(rawResponse));
    }

    /**
     * Extracts only the needed information from the JSON response
     * @param response The response from the Open Weather API.
     * @return new JSON containing only the needed information such as the condition, temperature, and wind_speed
     */
    private JSONObject parseResponse(JSONObject response) {
        String condition = response.getJSONArray("weather").getJSONObject(0).getString("main");
        double temperature = response.getJSONObject("main").getDouble("temp");
        double windSpeed = response.getJSONObject("wind").getDouble("speed");
        JSONObject weather = new JSONObject();
        weather.put("condition", condition);
        weather.put("temperature", temperature);
        weather.put("wind_speed", windSpeed);
        return weather;
    }
}
