package com.ljb.jbplayer.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ljb.jbplayer.R;
import com.ljb.jbplayer.module.skinloader.SkinInflaterFactory;
import com.ljb.jbplayer.module.skinloader.SkinManager;
import com.ljb.jbplayer.module.skinloader.attr.DynamicAttr;
import com.ljb.jbplayer.module.skinloader.calback.IDynamicNewView;
import com.ljb.jbplayer.module.skinloader.calback.ISkinUpdate;
import com.ljb.jbplayer.module.skinloader.statusbar.StatusBarBackground;
import com.ljb.jbplayer.util.ToastUtils;
import com.ljb.jbplayer.util.ViewUtils;

import java.util.List;


public abstract class BaseActivity extends AppCompatActivity implements ISkinUpdate, IDynamicNewView {

    private static final String TAG = BaseActivity.class.getPackage().getName();
    // 当前Activity是否需要响应皮肤更改需求
    private boolean isResponseOnSkinChanging = true;
    private SkinInflaterFactory mSkinInflaterFactory;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory();
        LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
        super.onCreate(savedInstanceState);
        changeStatusColor();
        setContentView(setLayoutByID());
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

    @Override
    public void onThemeUpdate() {
        Log.i("BaseActivity", "onThemeUpdate");
        if (!isResponseOnSkinChanging) {
            return;
        }
        mSkinInflaterFactory.applySkin();
        changeStatusColor();
    }

    public void changeStatusColor() {
        //如果当前的Android系统版本大于4.4则更改状态栏颜色
        Log.i("BaseActivity", "changeStatus");
        int color = SkinManager.getInstance().getColorPrimary();
        StatusBarBackground statusBarBackground = new StatusBarBackground(
                this, color != -1 ? color : getResources().getColor(R.color.colorPrimary));
        statusBarBackground.setStatusBarbackColor();
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable) {
        isResponseOnSkinChanging = enable;
    }

    protected abstract int setLayoutByID();

    protected abstract void initView();

    protected abstract void initData();

    protected <T extends View> T initViewById(int id) {
        return (T) super.findViewById(id);
    }


    protected void startActivity(Class<?> clazz) {
        startActivityWithExtras(clazz, null);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        if (null != extras) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    //加载Fragment
    protected void loadFragment(Class<?> clazz, int contentId) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fg = ViewUtils.createFragment(clazz);
        if (fg.isAdded()) {
            fragmentTransaction.hide(fragment).show(fg).commitAllowingStateLoss();
        } else {
            if (null != fragment) {
                fragmentTransaction.hide(fragment).add(contentId, fg).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(contentId, fg).commit();
            }

        }
        fragment = fg;
    }

    protected void showToast(String message) {
        ToastUtils.showToast(this, message);
    }

    protected void cancelToast() {
        ToastUtils.cancelToast();
    }

}
