package com.codechallenge.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import com.common.android.utils.ContextHelper;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static com.codechallenge.app.utils.FragmentProvider.showCurrenForecastFragment;
import static com.codechallenge.app.utils.FragmentProvider.showSearchFragment;
import static com.codechallenge.app.utils.FragmentProvider.showWeatherFragment;
import static com.common.android.utils.ContextHelper.getContext;

public class MainActivity extends BaseActivity {

    private String cityName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.codechallenge.app.R.layout.main);

        cityName = retrieveCity();
        if(TextUtils.isEmpty(cityName))
            showSearchFragment();
        else showCurrenForecastFragment(cityName);

        // Keep the screen always on
        if (com.common.android.utils.BuildConfig.DEBUG)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private String retrieveCity(){
        SharedPreferences sharedPref = getContext().getPreferences(ContextHelper.getContext().MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.city), "");
    }

    @Override
    public void onNoConnectionAvailable() {
        Crouton.makeText(getContext(), getContext().getString(R.string.connection_error), Style.ALERT).show();
    }
}