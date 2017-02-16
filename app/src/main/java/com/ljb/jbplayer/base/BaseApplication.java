package com.ljb.jbplayer.base;

import android.app.Application;

import com.ljb.jbplayer.module.skinloader.SkinManager;


public class BaseApplication extends Application {

    public void onCreate() {
        super.onCreate();

        initSkinLoader();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
    }
}
