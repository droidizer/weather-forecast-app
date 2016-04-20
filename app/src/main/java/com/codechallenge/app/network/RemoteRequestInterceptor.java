package com.codechallenge.app.network;

import com.orhanobut.wasp.utils.AuthToken;
import com.orhanobut.wasp.utils.RequestInterceptor;
import com.orhanobut.wasp.utils.WaspRetryPolicy;

import java.util.Map;

/**
 * Created by greymatter on 14/03/16.
 */
public class RemoteRequestInterceptor implements RequestInterceptor {
    @Override
    public void onHeadersAdded(Map<String, String> map) {

    }

    @Override
    public void onQueryParamsAdded(Map<String, Object> map) {

    }

    @Override
    public WaspRetryPolicy getRetryPolicy() {
        return new WaspRetryPolicy(7200, 2, 1.5f);
    }

    @Override
    public AuthToken getAuthToken() {
        return null;
    }
}
