package com.home.ecoplus;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import com.home.ecoplus.fragment.AutomationFragment;
import com.home.ecoplus.fragment.HomeFragment;
import com.home.ecoplus.fragment.RoomsFragment;


public class MainActivity extends AppCompatActivity {
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_ROOMS = "rooms";
    private static final String TAG_AUTOMATION = "automation";
    public static String CURRENT_TAG = TAG_HOME;

    private Handler mHandler;
    AHBottomNavigation bottomNavigation;
    LinearLayout bottomNavigationHolder;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mHandler = new Handler();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigationHolder = findViewById(R.id.bottom_navigation_holder);

        bottomNavigation.setDefaultBackgroundColor(Color.TRANSPARENT);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.navigation_menu);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case 0:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case 1:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ROOMS;
                        break;
                    case 2:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_AUTOMATION;
                        break;
                    default:
                        navItemIndex = 0;
                }
                loadHomeFragment();
                return true;
            }});
        }

        @Override
        public void onResume() {
            super.onResume();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = getWindow();
                w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }

    private void loadHomeFragment() {
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        mHandler.post(mPendingRunnable);
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 1:
                // rooms
                return new RoomsFragment();
            case 2:
                // automation
                return new AutomationFragment();
            default:
                // home
                return new HomeFragment();
        }
    }

    public void showBottomAppBar(){
        slideUp(bottomNavigation);
        slideUp(bottomNavigationHolder);
    }

    public void hideBottomAppBar(){
        slideDown(bottomNavigation);
        slideDown(bottomNavigationHolder);
    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight()*3,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()*3); // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    @Override
    public void onBackPressed() {
        showBottomAppBar();
        switch (CURRENT_TAG){
            case TAG_HOME:
                HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_HOME);
                if (!fragment.handleOnBackPress()){
                    super.onBackPressed();
                }
                break;

            case TAG_ROOMS:
                break;

            case TAG_AUTOMATION:
                break;

            default:
                super.onBackPressed();
                break;
        }
    }

}
