package com.codechallenge.app.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.codechallenge.app.R;
import com.codechallenge.app.ui.BaseFragment;
import com.codechallenge.app.ui.search.SearchFragment;
import com.codechallenge.app.ui.weather.CurrentWeatherFragment;
import com.codechallenge.app.ui.weather.WeatherForecastFragment;
import static com.common.android.utils.ContextHelper.getContext;
import static com.common.android.utils.extensions.FragmentExtensions.newInstance;

public class FragmentProvider {

    private static final String CITY = "CITY_NAME";

    public static void showSearchFragment() {
        final FragmentManager fm = getContext().getSupportFragmentManager();
        final Bundle bundle = new Bundle();
        final BaseFragment fragment = newInstance(SearchFragment.class, bundle);
        final FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, fragment.tag());
        ft.addToBackStack(fragment.tag());
        ft.commit();
    }

    public static void showWeatherFragment(@NonNull final String city) {
        final FragmentManager fm = getContext().getSupportFragmentManager();
        final Bundle bundle = new Bundle();
        bundle.putString(CITY,city);
        final BaseFragment fragment = newInstance(WeatherForecastFragment.class, bundle);
        final FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, fragment.tag());
        ft.addToBackStack(fragment.tag());
        ft.commit();
    }

    public static void showCurrenForecastFragment(@NonNull final String city) {
        final FragmentManager fm = getContext().getSupportFragmentManager();
        final Bundle bundle = new Bundle();
        bundle.putString(CITY,city);
        final BaseFragment fragment = newInstance(CurrentWeatherFragment.class, bundle);
        final FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, fragment.tag());
        ft.addToBackStack(fragment.tag());
        ft.commit();
    }
}
