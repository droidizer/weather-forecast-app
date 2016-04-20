package com.codechallenge.app.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.extensions.DeviceExtensions;
import com.common.android.utils.interfaces.ILayout;
import com.common.android.utils.interfaces.ILogTag;
import com.codechallenge.app.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by greymatter on 01/03/16.
 */
public abstract class BaseFragment extends Fragment implements ILayout, ILogTag {
    protected View rootView;

    public BaseFragment() {
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        onViewCreated(savedInstanceState);
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayout(), container, false);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    protected abstract void onViewCreated(Bundle savedInstanceState);

}
