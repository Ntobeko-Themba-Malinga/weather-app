package com.weather.weather;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityWeatherTest {

    @Test
    void search() {
        City city = new City("Durban", -29.861825, 31.009909);
        CityWeather cityWeather = new CityWeather();
        JSONObject weather = cityWeather.search(city);

        assertDoesNotThrow(() -> weather.getString("condition"));
        assertDoesNotThrow(() -> weather.getDouble("temperature"));
        assertDoesNotThrow(() -> weather.getDouble("wind_speed"));
        assertThrows(JSONException.class, () -> weather.getJSONObject("Does not exist"));
    }
}