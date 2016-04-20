package com.codechallenge.app.ui.weather;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.codechallenge.app.R;
import com.codechallenge.app.models.Forecast;
import com.common.android.utils.ui.recyclerView.DataBindAdapter;
import com.common.android.utils.ui.recyclerView.DataBinder;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

import static com.codechallenge.app.utils.AppUtils.formatDate;
import static com.codechallenge.app.utils.AppUtils.kelvinToCelcius;

/**
 * Created by greymatter on 14/04/16.
 */
public class WeatherBinder extends DataBinder<Forecast, WeatherBinder.ViewHolder> {
    private final static String format = "MMM dd,  hh:mm";

    public WeatherBinder(@NotNull DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public int getLayout() {
        return R.layout.weather_item;
    }

    @Override
    public void bindViewHolder(@NotNull ViewHolder forecastVh, int position) {

        final Forecast forecast = get(position);

        if (forecast == null)
            return;

        forecastVh.date.setText(formatDate(new SimpleDateFormat(format), forecast.dt));

        final double pressure = (forecast.main != null) ?
                forecast.main.pressure : forecast.pressure;
        final double humdity = (forecast.main != null) ?
                forecast.main.humidity : forecast.humidity;

        forecastVh.pressure.setText("Pressure: " + String.valueOf(pressure) + " hPa");
        forecastVh.humidity.setText("Humidity: " + String.valueOf(humdity) + " %");

        final double temp = (forecast.temperature != null) ?
                forecast.temperature.temp : forecast.main.temp;
        final double maxTemp = (forecast.temperature != null) ?
                forecast.temperature.tempMax : forecast.main.tempMax;
        final double minTemp = (forecast.temperature != null) ?
                forecast.temperature.tempMin : forecast.main.tempMin;

        forecastVh.dayTemp.setText(String.valueOf(kelvinToCelcius(temp)));
        forecastVh.minTemp.setText("Min temperature: " + (kelvinToCelcius(minTemp)) + " degrees C");
        forecastVh.maxTemp.setText("Max temperature: " + (kelvinToCelcius(maxTemp)) + " degrees C");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.pressure)
        TextView pressure;
        @Bind(R.id.humidity)
        TextView humidity;
        @Bind(R.id.minTemp)
        TextView minTemp;
        @Bind(R.id.maxTemp)
        TextView maxTemp;
        @Bind(R.id.dayTemp)
        TextView dayTemp;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
