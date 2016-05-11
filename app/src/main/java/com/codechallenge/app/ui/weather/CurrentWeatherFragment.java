package com.codechallenge.app.ui.weather;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import com.codechallenge.app.R;
import com.codechallenge.app.models.Forecast;
import com.codechallenge.app.ui.BaseFragment;
import com.codechallenge.app.utils.AppUtils;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.extensions.DeviceExtensions;
import com.common.android.utils.interfaces.ICallback;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.codechallenge.app.network.RequestProvider.weatherToday;
import static com.codechallenge.app.utils.AppUtils.*;
import static com.codechallenge.app.ui.helpers.FragmentProvider.showSearchFragment;
import static com.codechallenge.app.ui.helpers.FragmentProvider.showWeatherFragment;

/**
 * Created by greymatter on 21/04/16.
 */
public class CurrentWeatherFragment extends BaseFragment {
@NonNull
    @Bind(R.id.date)
    TextView date;
    @NonNull
    @Bind(R.id.dayTemp)
    TextView dayTemp;
    @NonNull
    @Bind(R.id.weather_icon)
    TextView weatherIcon;
    @NonNull
    @Bind(R.id.pressure)
    TextView dayPressure;
    @NonNull
    @Bind(R.id.humidity)
    TextView dayHumidity;
    @NonNull
    @Bind(R.id.minTemp)
    TextView minTemp;
    @NonNull
    @Bind(R.id.maxTemp)
    TextView maxTemp;
    @NonNull
    @Bind(R.id.cityText)
    TextView cityName;
    @NonNull
    @Bind(R.id.weather_report)
    TextView forecastDesc;

    private static final String CITY_NAME = "CITY_NAME";
    private final static String format = "MMM dd";
    private final static String PATH_TO_WEATHER_FONT = "weather.ttf";
    private static String city = "";

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        setHasOptionsMenu(true);
        if (bundle==null)
            showSearchFragment();
        else {
            city = bundle.getString(CITY_NAME);
            if(!TextUtils.isEmpty(city))
                currentForecast(city);
            cityName.setText(city);
        }
    }

    private void currentForecast(String city) {
        weatherToday(city, new ICallback<Forecast>() {
            @Override
            public void onSuccess(Forecast forecast) {
                if (forecast == null)
                    return;

                final double temp = (forecast.temperature != null) ?
                        forecast.temperature.temp : forecast.main.temp;
                dayTemp.setText(String.valueOf(kelvinToCelcius(temp)));
                date.setText(formatDate(new SimpleDateFormat(format, Locale.GERMANY), forecast.dt));
                if (forecast.weather == null)
                    return;

                setWeatherTypeFace(weatherIcon, forecast.weather.get(0).icon);
                displayForecast(forecast);
            }});
    }

    private void displayForecast(Forecast forecast) {

        final double pressure = (forecast.main != null) ?
                forecast.main.pressure : forecast.pressure;
        final double humidity = (forecast.main != null) ?
                forecast.main.humidity : forecast.humidity;

        dayPressure.setText("Pressure: " + String.valueOf(pressure) + " hPa");
        dayHumidity.setText("Humidity: " + String.valueOf(humidity) + " %");

        final double temp = (forecast.temperature != null) ?
                forecast.temperature.temp : forecast.main.temp;
        final double max = (forecast.temperature != null) ?
                forecast.temperature.tempMax : forecast.main.tempMax;
        final double min = (forecast.temperature != null) ?
                forecast.temperature.tempMin : forecast.main.tempMin;

        dayTemp.setText(String.valueOf(kelvinToCelcius(temp)));
        minTemp.setText("Min temperature: " + (kelvinToCelcius(min)) + " deg C");
        maxTemp.setText("Max temperature: " + (kelvinToCelcius(max)) + " deg C");
        weatherIcon.setTypeface(Typeface.createFromAsset(ContextHelper.getContext().getAssets(), PATH_TO_WEATHER_FONT));

        if (forecast.weather == null)
            return;

        if (forecast.weather.size() == 1) {
            forecastDesc.setText(forecast.weather.get(0).title);
            AppUtils.setWeatherTypeFace(weatherIcon, forecast.weather.get(0).icon);
        }
}
    @Override
    public int getLayout() {
        return R.layout.fragment_current_weather;
    }

    @NotNull
    @Override
    public String tag() {
        return String.valueOf(R.string.current_weather);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                Crouton.makeText(ContextHelper.getContext(), R.string.user_message, Style.INFO).show();
                showSearchFragment();
                return true;

            case R.id.current:
                currentForecast(city);
                return true;

            case R.id.five_days:
                showWeatherFragment(city);
                return true;

            case R.id.seven_days:
                showWeatherFragment(city);

                return true;

            case R.id.sixteen_days:
                showWeatherFragment(city);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onBackPressed() {
        DeviceExtensions.hideKeyboard();
        ContextHelper.getContext().onBackPressed();
    }
}
