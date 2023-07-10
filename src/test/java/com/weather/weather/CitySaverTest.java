package com.weather.weather;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CitySaverTest {
    private static  Connection connection;
    private static Statement statement;
    private static final String DATABASE = "test_cities.db";

    /**
     * Creates and saves cities used for testing.
     * @param citySaver object used to save cities to the test database.
     */
    private void createAndSaveCities(CitySaver citySaver) {
        City city = new City("Durban", 12, 13);
        City city2 = new City("Durban2", 14, 15);
        City city3 = new City("Durban3", 16, 17);
        citySaver.save(city);
        citySaver.save(city2);
        citySaver.save(city3);
    }

    @BeforeAll
    static void setUpClass() throws SQLException {
        String rootDir = System.getProperty("user.dir");
        String databaseUrl = "jdbc:sqlite:" + rootDir + "/" + DATABASE;
        connection = DriverManager.getConnection(databaseUrl);
    }

    @BeforeEach
    void setUp() throws SQLException {
        statement = connection.createStatement();
    }

    @AfterEach
    void tearDown() throws SQLException {
        statement.executeUpdate("DELETE FROM cities");
        statement.close();
    }

    @Test
    void save() throws SQLException {
        City city = new City("Durban", 12, 13);
        CitySaver citySaver = new CitySaver(DATABASE);
        boolean response = citySaver.save(city);
        ResultSet results = statement.executeQuery("SELECT * FROM cities");

        assertTrue(response, "Failed to save city!");
        assertEquals("Durban", results.getString("name"));
        assertEquals("12", results.getString("latitude"));
        assertEquals("13", results.getString("longitude"));
    }

    @Test
    void getCities() {
        CitySaver citySaver = new CitySaver(DATABASE);
        createAndSaveCities(citySaver);

        List<City> savedCities = citySaver.getCities();
        assertEquals(3, savedCities.size());
        assertEquals("Durban", savedCities.get(2).getName());
        assertEquals(12, savedCities.get(2).getLatitude());
        assertEquals(13, savedCities.get(2).getLongitude());
        assertEquals("Durban2", savedCities.get(1).getName());
        assertEquals(14, savedCities.get(1).getLatitude());
        assertEquals(15, savedCities.get(1).getLongitude());
        assertEquals("Durban3", savedCities.get(0).getName());
        assertEquals(16, savedCities.get(0).getLatitude());
        assertEquals(17, savedCities.get(0).getLongitude());
    }

    @Test
    void getCity() {
        CitySaver citySaver = new CitySaver(DATABASE);
        createAndSaveCities(citySaver);

        City actualCity = citySaver.getCity("Durban");

        assertEquals("Durban", actualCity.getName());
        assertEquals(12, actualCity.getLatitude());
        assertEquals(13, actualCity.getLongitude());
    }

    @Test
    void getCityNoneExistent() {
        CitySaver citySaver = new CitySaver(DATABASE);
        City city = citySaver.getCity("A city that does not exist");
        assertNull(city);
    }

    @Test
    void deleteCity() {
        CitySaver citySaver = new CitySaver(DATABASE);
        createAndSaveCities(citySaver);

        assertTrue(citySaver.deleteCity("Durban"));
        assertEquals(2, citySaver.getCities().size());
    }

    @Test
    void deleteCityNoneExistent() {
        CitySaver citySaver = new CitySaver(DATABASE);
        createAndSaveCities(citySaver);

        assertFalse(citySaver.deleteCity("A City That Does Not Exist"));
        assertEquals(3, citySaver.getCities().size());
    }
}