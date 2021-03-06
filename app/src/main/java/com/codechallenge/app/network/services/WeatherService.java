package com.codechallenge.app.network.services;

import com.codechallenge.app.models.Forecast;
import com.codechallenge.app.models.WeatherModel;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.WaspRequest;
import com.orhanobut.wasp.http.FormUrlEncoded;
import com.orhanobut.wasp.http.GET;
import com.orhanobut.wasp.http.Path;

public interface WeatherService {

    @GET("/weather?&q={city}&lang=en&APPID=259cad6720d80786a38f99146a9f3521")
    WaspRequest weather(@Path("APPID") String key, @Path("city") String cityName, Callback<Forecast> callback);

    @GET("/forecast/daily?&q={city}&lang=en&cnt={count}&APPID=259cad6720d80786a38f99146a9f3521")
    WaspRequest weatherForecast(@Path("APPID") String key, @Path("city") String cityName, @Path("count") int count, Callback<WeatherModel> callback);

}

