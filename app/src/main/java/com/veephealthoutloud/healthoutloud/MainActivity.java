package com.veephealthoutloud.healthoutloud;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.veephealthoutloud.healthoutloud.Fragments.MyPostsFragment;
import com.veephealthoutloud.healthoutloud.Fragments.NewsfeedFragment;


public class MainActivity extends AppCompatActivity implements NewsfeedFragment.OnNewsfeedFragmentInteractionListener,
MyPostsFragment.OnFragmentInteractionListener{

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    public static int navItemIndex = 0;

    private static final String TAG_NEWSFEED = "newsfeed";
    private static final String TAG_MY_POSTS = "my_posts";
    private String CURRENT_TAG;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Entered OnCreate for MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        SetUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_NEWSFEED;
            loadContentFragment();
        }

    }

    @Override
    public void onNewsfeedFragmentInteraction(Uri uri) {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void SetUpNavigationView(){
        System.out.println("Entered SetUpNavigationView for MainActivity");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("menuItem.getItemId(): " + menuItem.getItemId());
                switch (menuItem.getItemId()){
                    case R.id.nav_newsfeed:
                        CURRENT_TAG = TAG_NEWSFEED;
                        navItemIndex = 0;
                        break;
                    case R.id.nav_my_posts:
                        CURRENT_TAG = TAG_MY_POSTS;
                        navItemIndex = 1;
                        break;
                    case R.id.nav_settings:
                        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);
                        return true;
                    case R.id.nav_logout:
                        // TODO: Have it actually logout
                        Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        return true;
                    default:
                        navItemIndex = 0;
                }
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadContentFragment();

                return true;
            }
        });
    }

    private void loadContentFragment() {
        System.out.println("Entered loadContentFragment()");
        // selecting appropriate nav menu item
        selectNavMenuItem();

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getContentFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
                fragmentTransaction.commit();
            }
        };

        // If mPendingRunnable is not null, then add to log
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawerLayout.closeDrawers();
    }

    private Fragment getContentFragment() {
        System.out.println("Entered getContentFragment");
        System.out.println("navItemIndex");
        switch (navItemIndex) {
            case 0:
                // newsfeed
                return new NewsfeedFragment();
            case 1:
                // my posts
                return new MyPostsFragment();
            default:
                return new NewsfeedFragment();
        }
    }

    private void selectNavMenuItem() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
}
