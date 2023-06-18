package com.weather.weather;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @Test
    void city() {
        City city = new City("Durban", 20.1586512, 3.5415);
        assertEquals("Durban", city.getName());
        assertEquals(20.1586512, city.getLatitude());
        assertEquals(3.5415, city.getLongitude());
    }
}