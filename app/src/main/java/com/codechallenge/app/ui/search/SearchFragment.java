package com.codechallenge.app.ui.search;

import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.VideoView;
import butterknife.Bind;
import butterknife.OnClick;
import com.codechallenge.app.R;
import com.codechallenge.app.ui.BaseFragment;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.extensions.ResourceExtensions;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import org.jetbrains.annotations.NotNull;

import static com.codechallenge.app.utils.FragmentProvider.showCurrenForecastFragment;

public class SearchFragment extends BaseFragment {

    @NonNull
    @Bind(R.id.search)
    EditText search;

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        Crouton.makeText(ContextHelper.getContext(), R.string.user_message, Style.INFO).show();
        DisplayMetrics metrics = new DisplayMetrics();
        ContextHelper.getContext().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    @OnClick(R.id.search_now)
    public void onSearchClick() {
        final String citySearch = search.getText().toString();
        if (!TextUtils.isEmpty(citySearch))
            showCurrenForecastFragment(citySearch);
        if (!TextUtils.isEmpty(citySearch))
            saveCity(citySearch);
    }

    @Override
    public int getLayout() {
        return R.layout.search_fragment;
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
