package com.kimson.kcframeofandroid.ui.base;

import android.app.ProgressDialog;

import com.kimson.kcframeofandroid.api.ApiClient;
import com.kimson.kcframeofandroid.api.ApiService;


/**
 * Created by zhujianheng on 6/1/16.
 */
public class BaseFragment extends com.kimson.library.ui.fragment.BaseFragment{
    private final String TAG = this.getClass().getSimpleName();

    protected final ApiService API = ApiClient.getApiService();

    protected ProgressDialog mProgressDialog;

    protected void showProgressDialog(int resId) {
        showProgressDialog(getString(resId));
    }

    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


}
