package com.ljb.jbplayer.module.theme;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.ljb.jbplayer.R;
import com.ljb.jbplayer.base.BaseFragment;
import com.ljb.jbplayer.module.skinloader.SkinManager;
import com.ljb.jbplayer.module.skinloader.calback.ILoaderListener;
import com.ljb.jbplayer.util.FileUtils;
import com.ljb.jbplayer.util.PreferencesHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemeFragment extends BaseFragment implements View.OnClickListener{

    private TextView themeName;
    private static Map<String, String> skins = new HashMap<>();
    private static String PREFERENCES_KEY = "skin_name";
    private static String SKIN_DIR;
    private boolean isSkin = false;
    private String skin_name;


    @Override
    protected int setLayoutByID() {
        return R.layout.fragment_theme;
    }

    @Override
    protected void initView() {
        themeName = initViewById(R.id.tv_theme);
        initViewById(R.id.rb_blue).setOnClickListener(this);
        initViewById(R.id.rb_green).setOnClickListener(this);
        initViewById(R.id.rb_orange).setOnClickListener(this);
        initViewById(R.id.rb_black).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initSkin();
        skin_name = PreferencesHelper.getString(getActivity(), PREFERENCES_KEY, "default");
        updateThemeName(skin_name);
    }



    private void initSkin() {
        SKIN_DIR = FileUtils.getSkinDirPath(getActivity());
        skins.put("default", "default");
        skins.put("orange", "skin_orange.apk");
        skins.put("black", "skin_black.apk");
        skins.put("green", "skin_green.apk");
    }

    private void updateThemeName(String skin_name) {
        themeName.setText("当前主题："+skin_name);
    }

    private void changeSkin(final String skinName) {
        String skinFullName = SKIN_DIR + File.separator + skins.get(skinName);
        FileUtils.moveRawToDir(getActivity(), skins.get(skinName), skinFullName);
        File skin = new File(skinFullName);
        if (!skin.exists()) {
            showToast("请检查" + skinFullName + "是否存在");
        } else {
            SkinManager.getInstance().load(skin.getAbsolutePath(),
                    new ILoaderListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess() {
                            PreferencesHelper.putString(getActivity(),PREFERENCES_KEY,skinName);
                            updateThemeName(skinName);
                            showToast("切换成功");
                        }

                        @Override
                        public void onFailed() {
                            showToast("切换失败");
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_blue:
                SkinManager.getInstance().restoreDefaultTheme();
                PreferencesHelper.putString(getActivity(),PREFERENCES_KEY,"default");
                updateThemeName("default");
                break;
            case R.id.rb_orange:
                changeSkin("orange");
                break;
            case R.id.rb_black:
                changeSkin("black");
                break;
            case R.id.rb_green:
                changeSkin("green");
                break;
            default:
                break;
        }
    }
}
