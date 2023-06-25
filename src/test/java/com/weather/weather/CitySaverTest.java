package com.weather.weather;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class CitySaverTest {
    private static  Connection connection;
    private static Statement statement;
    private static final String DATABASE = "test_cities.db";

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
}