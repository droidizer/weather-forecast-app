package com.codechallenge.app.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.codechallenge.app.R;
import com.codechallenge.app.models.Places;
import com.codechallenge.app.models.Prediction;
import com.codechallenge.app.ui.BaseFragment;
import com.codechallenge.app.utils.AppUtils;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.extensions.DeviceExtensions;
import com.common.android.utils.interfaces.ICallback;
import com.common.android.utils.logging.Logger;
import de.keyboardsurfer.android.widget.crouton.Style;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.codechallenge.app.network.RequestProvider.searchCity;
import static com.codechallenge.app.ui.helpers.FragmentProvider.showCurrenForecastFragment;
import static de.keyboardsurfer.android.widget.crouton.Crouton.makeText;


public class SearchFragment extends BaseFragment {

    @NonNull
    @Bind(R.id.search)
    AutoCompleteTextView searchCity;

    TextView clickedItem;
    ArrayAdapter<String> adapter;
    List<String> city;
    List<Prediction> prediction;
    String cityName;

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        makeText(ContextHelper.getContext(), R.string.user_message, Style.INFO).show();
        DisplayMetrics metrics = new DisplayMetrics();
        ContextHelper.getContext().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        searchCity.addTextChangedListener(createNewTextWatcher());

        searchCity.setOnItemClickListener(createAdapterItemClickListener());

        searchCity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final int DRAWABLE_RIGHT = 2;
                    if (event.getRawX() >= (searchCity.getRight() - searchCity.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
                    {
                        searchCity.getText().clear();
                        clearAdapter();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @NotNull
    private TextWatcher createNewTextWatcher() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                final CharSequence input = s;
                if(!(input.length() > 3))
                    return;

                searchCity(input.toString(), new ICallback<Places>() {
                        @Override
                        public void onSuccess(Places placesResult) {
                            Logger.d("Places " + placesResult.toString());
                                if (placesResult.predictions.size()==0)
                                    return;

                                city = new ArrayList<>();
                                prediction = new ArrayList<>();

                                for (Prediction p : placesResult.predictions) {
                                    city.add(p.description);
                                    prediction.add(p);
                                }
                                adapter = new ArrayAdapter<>(ContextHelper.getContext(), android.R.layout.simple_list_item_1, city);
                                searchCity.setAdapter(adapter);

                                cityName = null;
                                makeText(ContextHelper.getContext(), "No results. Try again!", Style.INFO);
                        }
                    });
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
            };
    }

    private AdapterView.OnItemClickListener createAdapterItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (adapter != null) {
                    cityName = prediction.get(position).terms.get(0).value;
                    DeviceExtensions.hideKeyboard();
                }
            }
        };
    }

    private void clearAdapter() {
        if(adapter!=null)
        adapter.clear();
    }

    @OnClick(R.id.search_now)
    public void onSearchClick(){
        DeviceExtensions.hideKeyboard();

        if(prediction==null)
            return;

        String citySearch = prediction.get(0).terms.get(0).value;
        if (!TextUtils.isEmpty(citySearch))
            showCurrenForecastFragment(citySearch);

        if (!TextUtils.isEmpty(citySearch))
            AppUtils.saveCity(citySearch);
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

    @Override
    public void onBackPressed() {
            DeviceExtensions.hideKeyboard();
            ContextHelper.getContext().onBackPressed();
    }
}
