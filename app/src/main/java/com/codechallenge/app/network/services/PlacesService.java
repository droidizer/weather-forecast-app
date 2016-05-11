package com.codechallenge.app.network.services;

import com.codechallenge.app.models.Places;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.WaspRequest;
import com.orhanobut.wasp.http.GET;
import com.orhanobut.wasp.http.Path;

public interface PlacesService {

    @GET("/json?&types=(cities)&input={input}&key=AIzaSyDWyer-rrNHr_B3-JDL-_KUnUGxRSd1BoU")
    WaspRequest cityPredictions(@Path("key") String key, @Path("input") String input, Callback<Places> callback);
}
