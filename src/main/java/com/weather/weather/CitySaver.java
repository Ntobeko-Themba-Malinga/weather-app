package com.weather.weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves a city to the database.
 */
public class CitySaver {
    private final Connection connection;

    /**
     * Creates an instance of the city saver.
     *
     * @param database name of the database to use.
     */
    public CitySaver(String database) {
        String rootDir = System.getProperty("user.dir");
        String databaseUrl = "jdbc:sqlite:" + rootDir + "/" + database;
        try {
            this.connection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves a cit to the database.
     *
     * @param city city to save to the database.
     * @return true if city saved else false.
     */
    public boolean save(City city) {
        String query = String.format(
                "INSERT INTO cities values('%s', %f, %f)",
                city.getName(),
                city.getLatitude(),
                city.getLongitude()
        );
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets all the cities saved in the database.
     *
     * @return List of saved cities
     */
    public List<City> getCities() {
        List<City> cities = new ArrayList<>();
        String query = "SELECT * FROM cities";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String name = results.getString("name");
                double latitude = results.getDouble("latitude");
                double longitude = results.getDouble("longitude");
                City city = new City(name, latitude, longitude);
                cities.add(city);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
