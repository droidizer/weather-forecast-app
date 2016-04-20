package com.codechallenge.app.ui.weather;

import android.content.SharedPreferences;
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
import com.common.android.utils.ContextHelper;
import com.common.android.utils.interfaces.ICallback;
import com.common.android.utils.ui.recyclerView.DataBindAdapter;
import com.codechallenge.app.R;
import com.codechallenge.app.models.Forecast;
import com.codechallenge.app.models.WeatherModel;
import com.codechallenge.app.ui.BaseFragment;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import org.jetbrains.annotations.NotNull;

import static com.codechallenge.app.FragmentProvider.showSearchFragment;
import static com.codechallenge.app.network.RequestProvider.weatherForecast;
import static com.codechallenge.app.network.RequestProvider.weatherToday;

/**
 * Created by greymatter on 14/04/16.
 */
public class WeatherFragment extends BaseFragment{

    private static final String CITY_NAME = "CITY_NAME";
    @NonNull
    @Bind(R.id.recyclerView)
    RecyclerView forecastList;

    @NonNull
    @Bind(R.id.cityText)
    TextView city;

    @NonNull
    @Bind(R.id.forecastDesc)
    TextView forecastDesc;

    DataBindAdapter<Forecast> weatherAdapter;
    private String cityName;

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        cityName = bundle.getString(CITY_NAME);

        if(TextUtils.isEmpty(retrieveCity()))
            showSearchFragment();

        city.setText(cityName);
        forecastDesc.setText(R.string.current);
        setupRecyclerView();
        currentForecast();
        setHasOptionsMenu(true);
    }


    private void setupRecyclerView() {
        forecastList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        weatherAdapter = new DataBindAdapter<>();
        forecastList.setHasFixedSize(true);
        forecastList.setAdapter(weatherAdapter);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_weather;
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

    private void currentForecast() {
        weatherAdapter.clear();

        weatherToday(cityName, new ICallback<Forecast>() {
            @Override
            public void onSuccess(Forecast weatherForecast) {
                if (weatherForecast == null)
                    return;
                weatherAdapter.add(weatherForecast, WeatherBinder.class);

            }});
    }

    private void prepForecast(@NonNull final int stringResId, @NonNull final int count) {
        weatherAdapter.clear();
        forecastDesc.setText(stringResId);
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
private String retrieveCity(){
    SharedPreferences sharedPref = getActivity().getPreferences(ContextHelper.getContext().MODE_PRIVATE);
    return sharedPref.getString(getString(R.string.city), "");
}
    @NotNull
    @Override
    public String tag() {
        return String.valueOf(R.string.app_name);
    }
}
