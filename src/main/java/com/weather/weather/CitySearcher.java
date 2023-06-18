package com.weather.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Searches for a city.
 */
public class CitySearcher {
    private final HttpClient client;
    private HttpRequest request;

    /**
     * Creates instance of CitySearcher.
     */
    public CitySearcher() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Searches for a city using the OpenWeather API.
     * @param name The name of the city to search for.
     * @return if city exist a city object is created and then returned else null.
     */
    public City search(String name) throws Exception {
        City city = null;
        String apiCall = String.format(
                "http://api.openweathermap.org/geo/1.0/direct?q=%s,ZAF&appid=%s",
                name.replace(" ", "+"),
                "300517fbec11bc122e609a0e7e3d4e7e"
                );
        this.request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiCall))
                .build();
        JSONObject cityResponse = sendRequest();
        if (cityResponse != null) {
            city = new City(name, cityResponse.getDouble("lat"), cityResponse.getDouble("lon"));
        }
        return city;
    }

    /**
     * Sends the request to the OpenWeather API.
     * @return City JSONObject if city found else null.
     */
    private JSONObject sendRequest() throws Exception {
        JSONObject cityJSON = null;
        HttpResponse<String> response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());
        JSONArray cityArray = new JSONArray(response.body());
        if (!cityArray.isEmpty()) {
            cityJSON = cityArray.getJSONObject(0);
        }
        return cityJSON;
    }
}
