/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;

import android.app.Fragment;
import android.app.FragmentManager;

import android.support.v4.widget.DrawerLayout;


import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.fragment.ActivateCodeFragment;
import vg.victoryglobal.victoryglobal.fragment.CurrentSalesFragment;
import vg.victoryglobal.victoryglobal.fragment.GenealogyFragment;
import vg.victoryglobal.victoryglobal.fragment.HomeFragment;
import vg.victoryglobal.victoryglobal.fragment.LoginFragment;
import vg.victoryglobal.victoryglobal.fragment.PayoutReportsFragment;
import vg.victoryglobal.victoryglobal.fragment.PurchasesFragment;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountFragment;
import vg.victoryglobal.victoryglobal.fragment.UpgradeAccountFragment;
import vg.victoryglobal.victoryglobal.listener.LoginListener;
import vg.victoryglobal.victoryglobal.listener.LogoutListener;
import vg.victoryglobal.victoryglobal.model.AccountLogin;
import vg.victoryglobal.victoryglobal.model.AccountLoginRequest;

public class MainFragmentActivity extends AppCompatActivity implements LoginListener,LogoutListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private android.support.v7.app.ActionBar mActionBar;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    NavigationView mNavigationView;
    BottomNavigationView mBottomNavigationView;

    android.widget.LinearLayout drawer_navigation_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_activity);

        mTitle = mDrawerTitle = getTitle();


        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(bottomOnNavigationItemSelectedListener);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setActionBar(toolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_container);


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

        mNavigationView = (NavigationView) findViewById(R.id.drawer_navigation_view);
        mNavigationView.setNavigationItemSelectedListener(drawerOnNavigationItemSelectedListener);

        drawer_navigation_header = (android.widget.LinearLayout) mNavigationView.getHeaderView(0);

        toggleNavigationHeader(false);
        toggleNavigationLoginMenu(false);

        //toggleBottomNavigation(false);
        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);

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

        if(show){

            AccountLoginRequest accountLoginRequest = AccountLoginRequest.getInstance();

            String full_name = accountLoginRequest.getAccountLogin().getFullname();

            String distributor_id_label = String.format("%09d", accountLoginRequest.getAccountLogin().getMlmMemberId());

            //mNavigationView.inflateHeaderView(R.layout.drawer_navigation_header);
            //drawer_navigation_header.setVisibility(View.VISIBLE);

            //drawer_navigation_header.findViewById(R.id.header_fullname).setVisibility(View.VISIBLE);
            //drawer_navigation_header.findViewById(R.id.header_distributor_id).setVisibility(View.VISIBLE);

            TextView header_full_name = drawer_navigation_header.findViewById(R.id.header_fullname);
            header_full_name.setText(full_name);

            TextView header_distributor_id = drawer_navigation_header.findViewById(R.id.header_distributor_id);
            header_distributor_id.setText(distributor_id_label);

        }else{
            //drawer_navigation_header.setVisibility(View.GONE);
            //drawer_navigation_header.findViewById(R.id.header_fullname).setVisibility(View.GONE);
            //drawer_navigation_header.findViewById(R.id.header_distributor_id).setVisibility(View.GONE);

            TextView header_full_name = drawer_navigation_header.findViewById(R.id.header_fullname);
            header_full_name.setText("Sign in to Victory Global");

            TextView header_distributor_id = drawer_navigation_header.findViewById(R.id.header_distributor_id);
            header_distributor_id.setText("to manage your account");
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
            } else if (id == R.id.drawer_navigation_current_sales) {
                fragment = new CurrentSalesFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
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
                fragment = new LoginFragment();
                fragment_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }else if(id == R.id.drawer_navigation_logout){
                toggleNavigationHeader(false);
                toggleNavigationLoginMenu(false);
                mBottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
            }

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

            FragmentManager front_manager = getFragmentManager();
            android.support.v4.app.FragmentManager front_manager_old = getSupportFragmentManager();


            switch (id) {
                case R.id.navigation_home:
                    //set arguments
                    //bundle.putSparseParcelableArray();
                    fragment = new HomeFragment();
                    front_manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;
                case R.id.navigation_register:
                    fragment_old =  new RegisterAccountFragment();
                    front_manager_old.beginTransaction().replace(R.id.content_frame, fragment_old).commit();
                    break;
                case R.id.navigation_activate_code:
                    fragment_old =  new ActivateCodeFragment();
                    front_manager_old.beginTransaction().replace(R.id.content_frame, fragment_old).commit();
                    break;
                case R.id.navigation_upgrade:
                    fragment_old =  new UpgradeAccountFragment();
                    front_manager_old.beginTransaction().replace(R.id.content_frame, fragment_old).commit();
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
        AccountLoginRequest accountLoginRequest = AccountLoginRequest.getInstance();
        accountLoginRequest.setSuccess(true);

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
        AccountLoginRequest accountLoginRequest = AccountLoginRequest.getInstance();
        accountLoginRequest.setSuccess(false);
        accountLoginRequest.reset();

        toggleNavigationHeader(false);
        toggleNavigationLoginMenu(false);
    }
}
