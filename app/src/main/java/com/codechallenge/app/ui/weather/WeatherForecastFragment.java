package com.codechallenge.app.ui.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import com.codechallenge.app.R;
import com.codechallenge.app.models.Forecast;
import com.codechallenge.app.models.WeatherModel;
import com.codechallenge.app.ui.BaseFragment;
import com.codechallenge.app.ui.helpers.FragmentProvider;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.extensions.DeviceExtensions;
import com.common.android.utils.interfaces.ICallback;
import com.common.android.utils.ui.recyclerView.DataBindAdapter;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import org.jetbrains.annotations.NotNull;

import static com.codechallenge.app.network.RequestProvider.weatherForecast;
import static com.codechallenge.app.utils.AppUtils.retrieveCity;
import static com.codechallenge.app.ui.helpers.FragmentProvider.showSearchFragment;

public class WeatherForecastFragment extends BaseFragment{

    private static final String CITY_NAME = "CITY_NAME";
    private static final int DEFAULT_COUNT = 5;

    @NonNull
    @Bind(R.id.recyclerView)
    RecyclerView forecastList;
    @NonNull
    @Bind(R.id.cityText)
    TextView city;

    DataBindAdapter<Forecast> weatherAdapter;
    private String cityName;

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        cityName = bundle.getString(CITY_NAME);

        if(TextUtils.isEmpty(retrieveCity()))
            showSearchFragment();

        setHasOptionsMenu(true);
        setRetainInstance(true);

        city.setText(cityName);
        setupRecyclerView();
        prepForecast(R.string.five_days, DEFAULT_COUNT);
    }

    private void setupRecyclerView() {
        forecastList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        weatherAdapter = new DataBindAdapter<>();
        forecastList.setHasFixedSize(true);
        forecastList.setAdapter(weatherAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                Crouton.makeText(ContextHelper.getContext(), R.string.user_message, Style.INFO).show();
                showSearchFragment();
                return true;

            case R.id.current:
                currentForecast();
                return true;

            case R.id.five_days:
                prepForecast(R.string.five_days, 5);
                return true;

            case R.id.seven_days:
                prepForecast(R.string.seven_days, 7);

                return true;

            case R.id.sixteen_days:
                prepForecast(R.string.sixteen_days, 16);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_weather;
    }

    private void currentForecast() {
        FragmentProvider.showCurrenForecastFragment(cityName);
    }

    private void prepForecast(@NonNull final int stringResId, @NonNull final int count) {
        weatherAdapter.clear();
        Crouton.makeText(ContextHelper.getContext(), stringResId, Style.INFO).show();

        weatherForecast(cityName, count, new ICallback<WeatherModel>() {
            @Override
            public void onSuccess(WeatherModel weatherForecast) {
                if (weatherForecast == null)
                    return;

                for (final Forecast item : weatherForecast.getForecast()) {
                    weatherAdapter.add(item, WeatherBinder.class);
                }
            }});
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @NotNull
    @Override
    public String tag() {
        return String.valueOf(R.string.app_name);
    }


    @Override
    public void onBackPressed() {
        ContextHelper.getContext().onBackPressed();
    }
}
