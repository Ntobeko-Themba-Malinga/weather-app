package com.weather.weather;

import java.sql.*;

/**
 * Saves a city to the database.
 */
public class CitySaver {
    private final Connection connection;
    private final Statement statement;

    /**
     * Creates an instance of the city saver.
     * @param database name of the database to use.
     */
    public CitySaver(String database) {
        String databaseUrl = "jdbc:sqlite:/" + database;
        try {
            this.connection = DriverManager.getConnection(databaseUrl);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves a cit to the database.
     * @param city city to save to the database.
     * @return true if city saved else false.
     */
    public boolean save(City city) {

        return false;
    }
}
