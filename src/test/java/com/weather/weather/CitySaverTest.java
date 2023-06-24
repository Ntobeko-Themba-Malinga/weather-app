package com.weather.weather;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CitySaverTest {

    @Test
    void save() {
        City city = new City("Durban", 12, 13);
        CitySaver citySaver = new CitySaver("test_cities.db");

        assertTrue(citySaver.save(city));
    }
}