package com.ljb.jbplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ljb.jbplayer.R;
import com.ljb.jbplayer.util.PreferencesHelper;


public class SplashActivity extends AppCompatActivity {
    public static final String FIRST_START = "firstStart";
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startNextActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(runnable, 2 * 1000);
    }


    /**
     * 作者：ljb
     * 时间：2016/11/14 19:17
     * 邮箱：563773219@q
     * 描述：跳转到下一个界面
     */
    private void startNextActivity() {
        Intent intent = null;
        if (PreferencesHelper.getBoolean(this, FIRST_START)){
            intent = new Intent(this, MainActivity.class);
        }else {
            intent = new Intent(this,MainActivity.class);
        }

        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        handler = null;
        runnable = null;
    }
}
