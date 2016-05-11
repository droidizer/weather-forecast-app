package com.codechallenge.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.common.android.utils.extensions.DeviceExtensions;
import com.common.android.utils.logging.Logger;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static com.zplesac.connectionbuddy.ConnectionBuddy.getInstance;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getInstance().init(new ConnectionBuddyConfiguration.Builder(this).build());

        Logger.v(TAG, "[Connection] hasConnection: " + getInstance().hasNetworkConnection());
        if (!getInstance().hasNetworkConnection()) {
            onNoConnectionAvailable();
            Crouton.makeText(this, R.string.connection_error, Style.ALERT);
        }
    }
    protected abstract void onNoConnectionAvailable();

    @Override
    public void onBackPressed() {
        DeviceExtensions.hideKeyboard();
        onBackPressed();
    }
}
