package com.ljb.jbplayer.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ljb.jbplayer.R;
import com.ljb.jbplayer.base.BaseActivity;
import com.ljb.jbplayer.module.networkmusic.WebFragment;
import com.ljb.jbplayer.module.theme.ThemeFragment;
import com.ljb.jbplayer.util.ViewUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private WebFragment webFragment;
    private boolean isWebFragment = true;
    private boolean isHome = true;
    private long currentTick = 0;

    @Override
    protected int setLayoutByID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        toolbar = initViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = initViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = initViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = initViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getChildAt(0).setVerticalScrollBarEnabled(false);
        navigationView.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(navigationView.getHeaderView(0), "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(navigationView, "navigationViewMenu", R.color.colorPrimary);
        dynamicAddSkinEnableView(fab, "backgroundTint", R.color.colorPrimary);

    }

    @Override
    protected void initData() {
        loadFragment(WebFragment.class, R.id.content_main);
        webFragment = ((WebFragment) ViewUtils.createFragment(WebFragment.class));
        webFragment.setUrl("http://music.baidu.com/home?fr=mo");
        isHome = true;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        if (isWebFragment && webFragment.canGoBack()) {
            webFragment.goBack();
            return;
        }

        if (!isHome) {
            loadFragment(WebFragment.class, R.id.content_main);
            isHome = true;
            return;
        }

        cancleActivity();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.local_music) {
            // Handle the camera action
        } else if (id == R.id.download_manager) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_theme) {
            loadFragment(ThemeFragment.class, R.id.content_main);
            toolbar.setTitle(getString(R.string.select_theme));
        }

        isHome = false;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void cancleActivity() {
        if ((System.currentTimeMillis() - currentTick) > 2000) {
            showToast("再按一次退出程序");
            currentTick = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


}
