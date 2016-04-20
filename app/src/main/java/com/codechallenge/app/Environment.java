package com.codechallenge.app;

/**
 * Created by greymatter on 14/03/16.
 */
public enum Environment {

    Development(Constants.WEATHER_BASE_URL);

    public static Environment active = Development;
    public final String baseUrl;

    Environment(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private static class Constants {
        public static final String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5";
    }
}
