package com.ljb.jbplayer.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljb.jbplayer.module.skinloader.attr.DynamicAttr;
import com.ljb.jbplayer.module.skinloader.calback.IDynamicNewView;
import com.ljb.jbplayer.util.ToastUtils;
import com.ljb.jbplayer.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment extends Fragment implements IDynamicNewView {

    private IDynamicNewView iDynamicNewView;
    private View contentView;
    protected ProgressDialog progressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            iDynamicNewView = (IDynamicNewView) context;
        } catch (ClassCastException e) {
            iDynamicNewView = null;
        }
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        if (iDynamicNewView == null) {
            throw new RuntimeException("IDynamicNewView should be implements !");
        } else {
            iDynamicNewView.dynamicAddView(view, pDAttrs);
        }
    }

    public void dynamicAddSkinView(View view, String attrName, int attrValueResId) {
        List<DynamicAttr> pDAttrs = new ArrayList<>();
        pDAttrs.add(new DynamicAttr(attrName, attrValueResId));
        dynamicAddView(view, pDAttrs);
    }

    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        LayoutInflater result = getActivity().getLayoutInflater();
        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(setLayoutByID(), container, false);//setContentView(inflater, container);
        progressDialog = new ProgressDialog(getActivity());
        initView();
        initData();
        return contentView;
    }

    protected abstract int setLayoutByID();

    protected abstract void initView();

    protected abstract void initData();

    protected <T extends View> T initViewById(int id) {
        return (T) contentView.findViewById(id);
    }

    protected void startActivity(Class<?> clazz) {
        startActivityWithExtras(clazz, null);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != extras) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    protected void showToast(String message) {
        ToastUtils.showToast(getActivity(), message);
    }

    protected void cancelToast() {
        ToastUtils.cancelToast();
    }

    protected void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
