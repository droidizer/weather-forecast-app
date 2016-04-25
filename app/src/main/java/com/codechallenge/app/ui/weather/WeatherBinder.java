package com.codechallenge.app.ui.weather;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.codechallenge.app.R;
import com.codechallenge.app.models.Forecast;
import com.codechallenge.app.utils.AppUtils;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.ui.recyclerView.DataBindAdapter;
import com.common.android.utils.ui.recyclerView.DataBinder;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

import static com.codechallenge.app.utils.AppUtils.formatDate;
import static com.codechallenge.app.utils.AppUtils.kelvinToCelcius;

public class WeatherBinder extends DataBinder<Forecast, WeatherBinder.ViewHolder> {
    private final static String format = "MMM dd";
    private final static String PATH_TO_WEATHER_FONT = "weather.ttf";

    public WeatherBinder(@NotNull DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public int getLayout() {
        return R.layout.weather_forecast_item;
    }

    @Override
    public void bindViewHolder(@NotNull ViewHolder forecastVh, int position) {

        final Forecast forecast = get(position);

        if (forecast == null)
            return;

        forecastVh.date.setText(formatDate(new SimpleDateFormat(format), forecast.dt));

        final double temp = (forecast.temperature != null) ?
                forecast.temperature.temp : forecast.main.temp;

        forecastVh.dayTemp.setText(String.valueOf(kelvinToCelcius(temp)));
        forecastVh.weatherIcon.setTypeface(Typeface.createFromAsset(ContextHelper.getContext().getAssets(), PATH_TO_WEATHER_FONT));

        if (forecast.weather == null)
            return;

        if (forecast.weather.size() == 1) {
            AppUtils.setWeatherTypeFace(forecastVh.weatherIcon, forecast.weather.get(0).icon);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.dayTemp)
        TextView dayTemp;
        @Bind(R.id.weather_icon)
        TextView weatherIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
