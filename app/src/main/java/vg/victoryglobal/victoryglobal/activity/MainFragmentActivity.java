/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;

import android.app.Fragment;
import android.app.FragmentManager;

import android.support.v4.widget.DrawerLayout;


import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.fragment.ActivateCodeFragment;
import vg.victoryglobal.victoryglobal.fragment.CurrentSalesFragment;
import vg.victoryglobal.victoryglobal.fragment.GenealogyFragment;
import vg.victoryglobal.victoryglobal.fragment.HomeFragment;
import vg.victoryglobal.victoryglobal.fragment.AccountLoginFragment;
import vg.victoryglobal.victoryglobal.fragment.PayoutReportsFragment;
import vg.victoryglobal.victoryglobal.fragment.ProfileFragment;
import vg.victoryglobal.victoryglobal.fragment.PurchasesFragment;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountFragment;
import vg.victoryglobal.victoryglobal.fragment.UpgradeAccountFragment;
import vg.victoryglobal.victoryglobal.listener.LoginListener;
import vg.victoryglobal.victoryglobal.listener.LogoutListener;
import vg.victoryglobal.victoryglobal.model.AccountLogin;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.utils.PersistentCookieStore;

public class MainFragmentActivity extends AppCompatActivity implements LoginListener,LogoutListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private android.support.v7.app.ActionBar mActionBar;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    NavigationView mNavigationView;
    BottomNavigationView mBottomNavigationView;

    android.widget.LinearLayout drawer_navigation_header;

    AuthLoginRequest authLoginRequest;

    CookieStore cookieStore;

    @Override
    public void onSaveInstanceState(Bundle outState) {
    /*Save your data to be restored here
    Example : outState.putLong("time_state", time); , time is a long variable*/
        boolean userAuthenticated = authLoginRequest.isSuccess();
        outState.putBoolean("userAuthenticated", userAuthenticated);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_activity);

        boolean  isDebuggable =  (getApplicationInfo().flags == ApplicationInfo.FLAG_DEBUGGABLE );
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main", getApplicationContext());

        // CookieStore is just an interface, you can implement it and do things like
        // save the cookies to disk or what ever.
        // get auth token from sqlite/SharedPreferences
        if(savedInstanceState!= null){
            if(!savedInstanceState.getBoolean("userAuthenticated"))
            {
                //authLoginRequest.getAuthSession();
                cookieStore = new PersistentCookieStore(getApplicationContext());
                CookieManager manager = new CookieManager( cookieStore, CookiePolicy.ACCEPT_ALL );
                CookieHandler.setDefault( manager );
            }
        }else{
            //authLoginRequest.getAuthSession();
            cookieStore = new PersistentCookieStore(getApplicationContext());
            CookieManager manager = new CookieManager( cookieStore, CookiePolicy.ACCEPT_ALL );
            CookieHandler.setDefault( manager );
        }

        // Optionally, you can just use the default CookieManager
        //CookieManager manager = new CookieManager();
        //CookieHandler.setDefault( manager  );

        mTitle = mDrawerTitle = getTitle();


        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(bottomOnNavigationItemSelectedListener);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setActionBar(toolbar);


        mDrawerLayout = findViewById(R.id.drawer_container);


        //mDrawerToggle = new ActionBarDrawerToggle(
        //        this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //mDrawerLayout.addDrawerListener(mDrawerToggle);
        //mDrawerToggle.syncState();

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description */
                R.string.navigation_drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {

                try {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }catch (Exception e) {
                    Log.e("MainFragmentActivity", e.getMessage());
                }

                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // when using AppCompatActivity
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        // when using Activity instead of AppCompatActivity
        //mActionBar =  this.getActionBar();
        //mActionBar.setDisplayHomeAsUpEnabled(true);
        //mActionBar.setHomeButtonEnabled(true);

        mNavigationView = findViewById(R.id.drawer_navigation_view);
        mNavigationView.setNavigationItemSelectedListener(drawerOnNavigationItemSelectedListener);

        drawer_navigation_header = (android.widget.LinearLayout) mNavigationView.getHeaderView(0);

        ImageView header_image = drawer_navigation_header.findViewById(R.id.header_distributor_image);
        TextView header_full_name = drawer_navigation_header.findViewById(R.id.header_fullname);

        header_image.setOnLongClickListener(new HeaderImageListener());
        header_full_name.setOnClickListener(new DistributorNameListener());

        if(authLoginRequest.isSuccess() && authLoginRequest.hasAuthLogin()) {
            toggleNavigationHeader(true);
            toggleNavigationLoginMenu(true);
        }else{
            toggleNavigationHeader(false);
            toggleNavigationLoginMenu(false);
        }

        //toggleBottomNavigation(false);
        if(savedInstanceState == null) {
            mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }

    private void toggleNavigationLoginMenu(boolean show) {

        Menu menu = mNavigationView.getMenu();
        if(menu == null) return;

        MenuItem menu_item_account = menu.findItem(R.id.drawer_navigation_account);
        if(menu_item_account == null) return;


        if(!authLoginRequest.hasAuthLogin()) {
            show = false;
        }

        MenuItem menu_item_login = menu.findItem(R.id.drawer_navigation_login);

        if(show){
             menu_item_account.getSubMenu().setGroupVisible(R.id.group_menu_login, true);
             menu_item_login.setVisible(false);
        }else{
            menu_item_account.getSubMenu().setGroupVisible(R.id.group_menu_login, false);
            menu_item_login.setVisible(true);
        }
    }

    private void toggleNavigationHeader(boolean show) {

        if(!authLoginRequest.hasAuthLogin()) {
            show = false;
        }

        if(show){
            String full_name = authLoginRequest.getAuthLogin().getUser().frontend_label;

            String distributor_id_label = String.format("%09d", authLoginRequest.getAccountLogin().getMlmMemberId());

            TextView header_full_name = drawer_navigation_header.findViewById(R.id.header_fullname);
            header_full_name.setText(full_name);

            TextView header_distributor_id = drawer_navigation_header.findViewById(R.id.header_distributor_id);
            header_distributor_id.setText(distributor_id_label);

            ImageView header_image = drawer_navigation_header.findViewById(R.id.header_distributor_image);

            header_image.setLongClickable(true);

            header_full_name.setClickable(true);

        }else{
            //drawer_navigation_header.setVisibility(View.GONE);
            //drawer_navigation_header.findViewById(R.id.header_fullname).setVisibility(View.GONE);
            //drawer_navigation_header.findViewById(R.id.header_distributor_id).setVisibility(View.GONE);

            TextView header_full_name = drawer_navigation_header.findViewById(R.id.header_fullname);
            header_full_name.setText("Sign in to Victory Global");

            TextView header_distributor_id = drawer_navigation_header.findViewById(R.id.header_distributor_id);
            header_distributor_id.setText("to manage your account");

            ImageView header_image = drawer_navigation_header.findViewById(R.id.header_distributor_image);

            header_image.setLongClickable(false);

            header_full_name.setClickable(false);
        }
    }


    private final NavigationView.OnNavigationItemSelectedListener drawerOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener(){

        Fragment fragment = null;
        FragmentManager fragment_manager = getFragmentManager();
        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.drawer_navigation_home) {
                fragment = new HomeFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                //set item check in bottom navigation
                mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
/*
            } else if (id == R.id.drawer_navigation_current_sales) {
                fragment = new CurrentSalesFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
*/
            } else if (id == R.id.drawer_navigation_genealogy) {
                fragment = new GenealogyFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);

            } else if (id == R.id.drawer_navigation_payout_reports) {
                fragment = new PayoutReportsFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
            } else if (id == R.id.drawer_navigation_purchases) {
                fragment = new PurchasesFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
            } else if(id == R.id.drawer_navigation_login){
                fragment = new AccountLoginFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }else if(id == R.id.drawer_navigation_logout){
                toggleNavigationHeader(false);
                toggleNavigationLoginMenu(false);
                prepareLogout(authLoginRequest.getAccountLogin());
            }
/*
            else if(id == R.id.drawer_navigation_profile){
                fragment = new ProfileFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
            }
*/
            mNavigationView.setCheckedItem(id);

            if(id != R.id.drawer_navigation_logout) {
                setTitle(item.getTitle());
            }
            mDrawerLayout.closeDrawer(Gravity.START);
            return true;
        }
    };

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();

            Fragment fragment = null;
            android.support.v4.app.Fragment fragment_old = null;


            /*
            //-- using fragment
            Class fragmentClass = null;
            fragmentClass = HomeFragment.class;

            try {
            assert fragmentClass != null;
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            */

            Bundle bundle = new Bundle();

            FragmentManager fragment_manager = getFragmentManager();
            android.support.v4.app.FragmentManager fragment_manager_old = getSupportFragmentManager();


            switch (id) {
                case R.id.navigation_home:
                    //set arguments
                    //bundle.putSparseParcelableArray();
                    fragment = new HomeFragment();
                    fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;
                case R.id.navigation_register:
                    fragment_old =  new RegisterAccountFragment();
                    fragment_manager_old.beginTransaction().replace(R.id.content_frame, fragment_old).commit();
                    break;
                case R.id.navigation_activate_code:
                    fragment_old =  new ActivateCodeFragment();
                    fragment_manager_old.beginTransaction().replace(R.id.content_frame, fragment_old).commit();
                    break;
                case R.id.navigation_upgrade:
                    fragment_old =  new UpgradeAccountFragment();
                    fragment_manager_old.beginTransaction().replace(R.id.content_frame, fragment_old).commit();
                    break;
            }

            // Highlight the selected item has been done by NavigationView
            item.setChecked(true);
            // Set action bar title
            setTitle(item.getTitle());
            return true;
        }

    };


    @Override
    public void prepareLogin(AccountLogin account_login) {
        toggleNavigationHeader(true);
        toggleNavigationLoginMenu(true);

        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void prepareRegister() {
        mBottomNavigationView.setSelectedItemId(R.id.navigation_register);
    }

    @Override
    public void prepareLogout(AccountLogin account_login) {
        AuthLoginRequest authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        authLoginRequest.setSuccess(false);
        authLoginRequest.reset();

        toggleNavigationHeader(false);
        toggleNavigationLoginMenu(false);

        Fragment fragment = null;
        FragmentManager fragment_manager = getFragmentManager();
        fragment = new HomeFragment();
        fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
    }

    //other method
    public class HeaderImageListener implements ImageView.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {

            Fragment fragment = null;
            fragment = new ProfileFragment();
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
            }

            FragmentManager fragment_manager = getFragmentManager();
            fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
            setTitle("Profile");
            return true;
        }
    }

    public class DistributorNameListener implements ImageView.OnClickListener {

        @Override
        public void onClick(View view) {

            Fragment fragment = null;
            fragment = new ProfileFragment();
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);

            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
            }

            FragmentManager fragment_manager = getFragmentManager();
            fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
            setTitle("Profile");
        }
    }
}
