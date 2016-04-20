package com.codechallenge.app.ui.search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import butterknife.Bind;
import com.codechallenge.app.R;
import com.codechallenge.app.ui.BaseFragment;
import com.common.android.utils.ContextHelper;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import org.jetbrains.annotations.NotNull;

import static com.codechallenge.app.utils.FragmentProvider.showWeatherFragment;

/**
 * Created by greymatter on 18/04/16.
 */
public class SearchFragment extends BaseFragment {

    @NonNull
    @Bind(R.id.search)
    EditText search;

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        Crouton.makeText(ContextHelper.getContext(), R.string.user_message, Style.INFO).show();

        search.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    final String citySearch = search.getText().toString();
                    if(!TextUtils.isEmpty(citySearch))
                    showWeatherFragment(citySearch);
                    handled = true;
                    if(!TextUtils.isEmpty(citySearch))
                    saveCity(citySearch);
                }
                return handled;
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.search;
    }

    @NotNull
    @Override
    public String tag() {
        return String.valueOf(R.string.app_name);
    }

    private void saveCity(@NonNull final String cityName) {
        SharedPreferences sharedPref = getActivity().getPreferences(ContextHelper.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.city), cityName);
        editor.commit();
    }

}
