package com.kimson.library.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kimson.library.bind.Bind;

/**
 * Created by zhujianheng on 2/24/16.
 */
public class BaseFragment extends Fragment{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bind.inject(this, view);
    }
}
