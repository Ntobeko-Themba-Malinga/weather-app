package com.weather.weather;

/**
 * Stores all the attributes of a city.
 */
public class City {
    private final String name;
    private final double latitude;
    private final double longitude;

    /**
     * Creates an instance of a city.
     *
     * @param name The name of the city.
     * @param latitude The latitude of the city.
     * @param longitude The longitude of the city.
     */
    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return City's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return City's latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return City's longitude
     */
    public double getLongitude() {
        return longitude;
    }
}
