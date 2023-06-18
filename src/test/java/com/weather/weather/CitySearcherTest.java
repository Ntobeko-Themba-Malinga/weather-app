package com.weather.weather;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CitySearcherTest {

    @Test
    void searchValidCity() throws Exception {
        CitySearcher citySearcher = new CitySearcher();
        City responseCity = citySearcher.search("Durban");
        City expectedCity = new City("Durban", -29.861825, 31.009909);

        assertEquals(expectedCity.getName(), responseCity.getName());
        assertEquals(expectedCity.getLatitude(), responseCity.getLatitude());
        assertEquals(expectedCity.getLongitude(), responseCity.getLongitude());
    }

    @Test
    void searchInvalidCity() throws Exception {
        CitySearcher citySearcher = new CitySearcher();
        assertNull(citySearcher.search("a city that doesn't exist"));
    }
}