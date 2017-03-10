package com.kason.kwatching;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kason.kwatching.entity.GankData;
import com.kason.kwatching.services.GankServiceClient;
import com.kason.kwatching.ui.fragment.PhotoFragment;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //initDrawer(mToolbar);

        mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        mNavigationView.setItemIconTintList(null);
        //mNavigationView.setCheckedItem(R.id.nav_record_video);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
        initFragment();
        /*Consumer<Movie> consumer = new Consumer<Movie>() {
            @Override
            public void accept(Movie movie) throws Exception {
                Log.i(TAG, "accept: "+movie);
            }
        };
        MovieServiceClient.getInstance().getMovieById(consumer,111);*/
        Consumer<GankData> consumer = new Consumer<GankData>() {
            @Override
            public void accept(GankData gankData) throws Exception {
                Log.i(TAG, "accept: "+gankData);
            }
        };

        GankServiceClient.getInstance().getByDay(consumer,2015,8,7);
    }
    private void initFragment() {

        currentFragment = new PhotoFragment();
        switchContent(currentFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 设置点击事件
        switch (item.getItemId()){
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);//打开抽屉
                item.setChecked(true);
                initFragment();
                break;

            default:
                break;
        }
        return true;
    }
    private void switchContent(Fragment currentFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment,currentFragment).commit();
        invalidateOptionsMenu();
    }
    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open,
                    R.string.close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            mDrawerToggle.syncState();
            mDrawerLayout.addDrawerListener(mDrawerToggle);
        }
    }
}
