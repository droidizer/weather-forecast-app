package com.codechallenge.app.network;

import android.support.annotation.NonNull;
import com.codechallenge.app.Environment;
import com.codechallenge.app.R;
import com.codechallenge.app.models.Forecast;
import com.codechallenge.app.models.WeatherModel;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.interfaces.ICallback;
import com.orhanobut.wasp.*;
import com.orhanobut.wasp.utils.NetworkMode;
import de.keyboardsurfer.android.widget.crouton.Style;

import java.net.CookiePolicy;

import static com.common.android.utils.ContextHelper.getContext;
import static de.keyboardsurfer.android.widget.crouton.Crouton.makeText;

public class RequestProvider {

    private static final boolean MOCK_NETWORK = false;
    private static final String TAG = RequestProvider.class.getSimpleName();

    private static NetworkService service;

    @NonNull
    private static RemoteRequestInterceptor createRemoteRequestInterceptor() {
        return new RemoteRequestInterceptor();
    }

    private static NetworkService networkService() {
        if (service == null)
            service = createNetworkService(createRemoteRequestInterceptor());
        return service;
    }

    private static NetworkService createNetworkService(RemoteRequestInterceptor interceptor) {
        return new Wasp.Builder(getContext())
                .setEndpoint(Environment.active.baseUrl)
                .setNetworkMode(MOCK_NETWORK
                        ? NetworkMode.MOCK
                        : NetworkMode.LIVE)
                .enableCookies(CookiePolicy.ACCEPT_ALL)
                .setRequestInterceptor(interceptor)
                .build()
                .create(NetworkService.class);
    }

    public static WaspRequest weatherToday(final String cityName, final ICallback<Forecast> callback) {
        return networkService().weather(cityName, new Callback<Forecast>() {

            @Override
            public void onSuccess(Response response, Forecast weatherModel) {
                callback.onSuccess(weatherModel);
            }

            @Override
            public void onError(WaspError waspError) {
                makeText(ContextHelper.getContext(), R.string.error, Style.ALERT).show();

            }
        });
    }
    public static WaspRequest weatherForecast(final String cityName, final int count, final ICallback<WeatherModel> callback) {
        return networkService().weatherForecast(cityName, count, new Callback<WeatherModel>() {
                    @Override
                    public void onSuccess(Response response, WeatherModel weatherModel) {
                        callback.onSuccess(weatherModel);
                    }

                    @Override
                    public void onError(WaspError waspError) {
                        makeText(ContextHelper.getContext(), R.string.error, Style.ALERT).show();
                    }
                });
    }
}
