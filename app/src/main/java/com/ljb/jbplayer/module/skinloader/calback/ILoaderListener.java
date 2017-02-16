package com.ljb.jbplayer.module.skinloader.calback;

/**
 * 加载皮肤时的回调接口
 */
public interface ILoaderListener {
    void onStart();
    void onSuccess();
    void onFailed();
}
